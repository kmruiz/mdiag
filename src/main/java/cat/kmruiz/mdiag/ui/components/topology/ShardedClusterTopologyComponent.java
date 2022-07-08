package cat.kmruiz.mdiag.ui.components.topology;

import cat.kmruiz.mdiag.overview.topology.sharded.ShardedClusterTopology;
import javafx.geometry.Orientation;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

public class ShardedClusterTopologyComponent extends FlowPane {
    private final ShardedClusterTopology topology;
    private final FlowPane shards;
    private final FlowPane mongos;
    private final FlowPane configServers;

    public ShardedClusterTopologyComponent(ShardedClusterTopology topology) {
        this.topology = topology;
        this.shards = new FlowPane(Orientation.HORIZONTAL);
        this.mongos = new FlowPane(Orientation.HORIZONTAL);
        this.configServers = new FlowPane(Orientation.HORIZONTAL);

        this.setOrientation(Orientation.VERTICAL);

        for (final var shard : topology.shards()) {
            this.shards.getChildren().add(new ShardComponent(shard));
        }

        for (final var mongos : topology.mongos()) {
            this.mongos.getChildren().add(new ShardMongosComponent(mongos));
        }

        this.getChildren().add(shards);
        this.getChildren().add(mongos);
        this.getChildren().add(configServers);
    }
}
