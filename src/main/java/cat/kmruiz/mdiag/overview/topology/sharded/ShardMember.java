package cat.kmruiz.mdiag.overview.topology.sharded;

import cat.kmruiz.mdiag.common.NodeType;

public record ShardMember(
        String host,
        NodeType nodeType

) {
}
