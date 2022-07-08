package cat.kmruiz.mdiag.overview.topology.sharded;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonTimestamp;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class ShardedClusterTopologyAnalyzer {
    private final MongoDatabase config;

    public ShardedClusterTopologyAnalyzer(MongoClient mongo) {
        this.config = mongo.getDatabase("config");
    }

    public ShardedClusterTopology analyze() {
        final var shardsCollection = config.getCollection("shards");
        final var mongosCollection = config.getCollection("mongos");

        final var currentShards = StreamSupport.stream(shardsCollection.find().spliterator(), false)
                .map(shard -> new Shard(
                        shard.getString("_id"),
                        LocalDateTime.ofEpochSecond(shard.get("topologyTime", BsonTimestamp.class).getTime(), 0, ZoneOffset.UTC),
                        shard.getInteger("state"),
                        parseShardMembers(shard.getString("_id"), shard.getString("host"))
                )).toList();

        final var currentMongos = StreamSupport.stream(mongosCollection.find().spliterator(), false)
                .map(mongos -> new ShardMongos(
                        mongos.getString("_id"),
                        mongos.getString("version"),
                        Duration.ofMillis(mongos.getLong("up")),
                        mongos.getBoolean("waiting")
                )).toList();

        return new ShardedClusterTopology(currentShards, currentMongos);
    }

    private List<ShardMember> parseShardMembers(String shardId, String topologyString) {
        final var onlyHosts = topologyString.substring(shardId.length() + 1);
        final var hostList = onlyHosts.split(",");

        return Stream.of(hostList).map(ShardMember::new).toList();
    }
}
