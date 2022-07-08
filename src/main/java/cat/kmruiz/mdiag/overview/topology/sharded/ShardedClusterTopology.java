package cat.kmruiz.mdiag.overview.topology.sharded;

import java.util.List;

public record ShardedClusterTopology(
        List<Shard> shards,
        List<ShardMongos> mongos
) {
}
