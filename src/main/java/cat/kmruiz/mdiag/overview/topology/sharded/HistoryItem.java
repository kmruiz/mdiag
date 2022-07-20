package cat.kmruiz.mdiag.overview.topology.sharded;

import cat.kmruiz.mdiag.common.IndexDefinition;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.LocalDateTime;

public record HistoryItem(
    String server,
    String shard,
    String clientAddress,
    LocalDateTime time,
    String operation,
    String namespace,
    Details details
) {
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
            include = JsonTypeInfo.As.PROPERTY,
            property = "type")
    @JsonSubTypes({
            @JsonSubTypes.Type(value = ShardCollectionStartDetails.class),
            @JsonSubTypes.Type(value = ShardCollectionEndDetails.class)
    })
    interface Details {}

    public record ShardCollectionStartDetails(
        IndexDefinition shardKey,
        String namespace,
        String primary
    ) implements Details {}

    public record ShardCollectionEndDetails(
            String version,
            boolean empty,
            int numChunks
    ) implements Details {}
}
