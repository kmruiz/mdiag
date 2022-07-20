package cat.kmruiz.mdiag.ui.components.topology;

import cat.kmruiz.mdiag.overview.topology.sharded.ShardMember;
import eu.hansolo.fx.charts.forcedirectedgraph.GraphNode;

public class ShardMemberComponent extends GraphNode {
    private final ShardMember shardMember;

    public ShardMemberComponent(ShardMember shardMember) {
        super(shardMember.host());
        this.shardMember = shardMember;
    }
}
