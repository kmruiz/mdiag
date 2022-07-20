package cat.kmruiz.mdiag.overview.topology.sharded;

import cat.kmruiz.mdiag.common.Collection;

import java.time.LocalDateTime;
import java.util.List;

public record Shard(
        String id,
        LocalDateTime topologyTime,
        int state,
        List<ShardMember> members,
        List<Collection> owningCollections
) {
}
