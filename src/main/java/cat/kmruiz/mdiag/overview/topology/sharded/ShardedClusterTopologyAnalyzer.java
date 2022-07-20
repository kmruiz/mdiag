package cat.kmruiz.mdiag.overview.topology.sharded;

import cat.kmruiz.mdiag.common.DataSize;
import cat.kmruiz.mdiag.common.Collection;
import cat.kmruiz.mdiag.common.IndexDefinition;
import cat.kmruiz.mdiag.common.NodeType;
import com.mongodb.client.MongoClient;
import org.bson.BsonTimestamp;
import org.bson.Document;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class ShardedClusterTopologyAnalyzer {
    private static final Logger log = Logger.getLogger("sharded-cluster-analyzer");

    private final MongoClient client;

    public ShardedClusterTopologyAnalyzer(MongoClient mongo) {
        this.client = mongo;
    }

    public ShardedClusterTopology analyze() {
        final var config = client.getDatabase("config");

        final var shardsCollection = config.getCollection("shards");
        final var mongosCollection = config.getCollection("mongos");
        final var settingsCollection = config.getCollection("settings");
        final var changelogCollection = config.getCollection("changelog");
        final var databasesCollection = config.getCollection("databases");

        final var collectionInfo = StreamSupport.stream(databasesCollection.find().spliterator(), false)
                .flatMap(dbInfo -> {
                    final var dbName = dbInfo.getString("_id");
                    final var primaryShard = dbInfo.getString("primary");
                    final var db = client.getDatabase(dbName);

                    return StreamSupport.stream(db.listCollectionNames().spliterator(), false)
                            .map(collName ->
                                    db.getCollection(collName).aggregate(List.of(
                                            new Document("$collStats", new Document(
                                                    "storageStats", new Document("scale", 1)
                                            ))
                                    )).batchSize(1).into(new ArrayList<>()).get(0)
                            ).map(collDoc -> new Collection(
                                    primaryShard,
                                    dbName,
                                    collDoc.getString("ns").substring(collDoc.getString("ns").indexOf('.') + 1),
                                    new DataSize(jsonPath(collDoc, 0, "storageStats", "storageSize")),
                                    new DataSize(jsonPath(collDoc, 0, "storageStats", "size")),
                                    new DataSize(jsonPath(collDoc, 0, "storageStats", "totalIndexSize")),
                                    jsonPath(collDoc, 0, "storageStats", "count"),
                                    sampleDocOfCollection(dbName, collDoc.getString("ns").substring(collDoc.getString("ns").indexOf('.') + 1))

                            ));
                }).collect(Collectors.groupingBy(Collection::shard));

        final var currentShards = StreamSupport.stream(shardsCollection.find().spliterator(), false)
                .map(shard -> new Shard(
                        shard.getString("_id"),
                        LocalDateTime.ofEpochSecond(shard.get("topologyTime", BsonTimestamp.class).getTime(), 0, ZoneOffset.UTC),
                        shard.getInteger("state"),
                        parseShardMembers(shard.getString("_id"), shard.getString("host")),
                        collectionInfo.getOrDefault(shard.getString("_id"), Collections.emptyList())
                )).toList();

        final var currentMongos = StreamSupport.stream(mongosCollection.find().spliterator(), false)
                .map(mongos -> new ShardMongos(
                        mongos.getString("_id"),
                        mongos.getString("version"),
                        Duration.ofMillis(mongos.getLong("up")),
                        mongos.getBoolean("waiting")
                )).toList();

        final var settings = StreamSupport.stream(settingsCollection.find().spliterator(), false)
                .reduce(Settings.defaultSettings(), (Settings accumulator, Document document) ->
                        switch (document.getString("_id")) {
                            case "balancer" ->
                                    new Settings(new Settings.LoadBalancerSetting(!document.getBoolean("stopped"), document.getString("mode")), accumulator.autosplit());
                            case "autosplit" ->
                                    new Settings(accumulator.loadBalancer(), new Settings.BooleanSetting(document.getBoolean("enabled")));
                            default -> unknownSetting(accumulator, document);
                        }, (a, b) -> a);

        final var history = StreamSupport.stream(changelogCollection.find().limit(100).sort(new Document("time", -1)).spliterator(), false)
                .map(changelog -> new HistoryItem(
                        changelog.getString("server"),
                        changelog.getString("shard"),
                        changelog.getString("clientAddr"),
                        LocalDateTime.ofInstant(changelog.get("time", Date.class).toInstant(), ZoneOffset.UTC.normalized()),
                        changelog.getString("what"),
                        changelog.getString("ns"),
                        parseDetails(changelog.get("details", Document.class), changelog.getString("what"))
                ))
                .toList();

        return new ShardedClusterTopology(
                currentShards,
                currentMongos,
                settings,
                history
        );
    }

    private Settings unknownSetting(Settings accumulator, Document document) {
        log.warning("Unknown setting " + document.getString("_id"));
        return accumulator;
    }

    private List<ShardMember> parseShardMembers(String shardId, String topologyString) {
        final var onlyHosts = topologyString.substring(shardId.length() + 1);
        final var hostList = onlyHosts.split(",");

        AtomicBoolean hadPrimary = new AtomicBoolean(false);

        return Stream.of(hostList).map(e -> new ShardMember(e, hadPrimary.getAndSet(true) ? NodeType.SECONDARY : NodeType.PRIMARY)).toList();
    }

    private HistoryItem.Details parseDetails(Document details, String operation) {
        return switch (operation) {
            case "shardCollection.start" -> new HistoryItem.ShardCollectionStartDetails(
                    parseIndexDefinition(details.get("shardKey", Document.class)),
                    details.getString("collection"),
                    details.getString("primary")
            );
            case "shardCollection.end" -> new HistoryItem.ShardCollectionEndDetails(
                    details.getString("version"),
                    details.getBoolean("empty", true),
                    details.getInteger("numChunks")
            );
            default -> null;

        };
    }

    private IndexDefinition parseIndexDefinition(Document document) {
        final var rules = document.entrySet().stream().map(entry -> new IndexDefinition.Rule(entry.getKey(), entry.getValue().toString())).toList();
        return new IndexDefinition(rules);
    }

    private <T> T jsonPath(Document root, T defaultValue, String... path) {
        if (root == null) {
            return defaultValue;
        }

        if (path.length == 1) {
            return root.get(path[0], defaultValue);
        }

        return jsonPath(root.get(path[0], Document.class), defaultValue, Arrays.copyOfRange(path, 1, path.length));
    }

    private String sampleDocOfCollection(String db, String coll) {
        final var sampleDoc = client.getDatabase(db).getCollection(coll).aggregate(
                Arrays.asList(new Document(
                        "$sample", new Document("size", 1)
                ))
        ).into(new ArrayList<>(1)).get(0);

        return sampleDoc.toJson();
    }
}
