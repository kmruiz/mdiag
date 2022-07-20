package cat.kmruiz.mdiag.overview.topology.sharded;

import cat.kmruiz.mdiag.common.Collection;
import cat.kmruiz.mdiag.common.NodeType;

import java.util.List;

public record ShardMember(
        String host,
        NodeType nodeType

) {
}
