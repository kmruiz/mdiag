package cat.kmruiz.mdiag.overview.topology.sharded;

import cat.kmruiz.mdiag.common.IndexDefinition;

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
