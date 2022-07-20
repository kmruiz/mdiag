package cat.kmruiz.mdiag.overview;

import cat.kmruiz.mdiag.overview.topology.sharded.ShardedClusterTopology;

import java.util.Date;

public record ExportedReport (
    String version,
    Date exportTime,
    ShardedClusterTopology shardedClusterTopology
) {
}
