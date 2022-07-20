package cat.kmruiz.mdiag.ui.components.topology;

import cat.kmruiz.mdiag.overview.topology.sharded.ShardedClusterTopology;
import cat.kmruiz.mdiag.ui.Css;
import javafx.geometry.Orientation;
import javafx.scene.layout.Border;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import static java.util.Collections.emptyList;

public class ShardedClusterTopologyComponent extends HBox {
    private final ShardedClusterTopology topology;
    private final VBox shards;
    private final VBox mongos;
    private final VBox configServers;

    public ShardedClusterTopologyComponent(ShardedClusterTopology topology) {
        Css.apply(this);

        this.topology = topology;

        this.shards = new VBox();
        this.shards.getStyleClass().add("ShardedClusterTopologyComponentShards");

        this.mongos = new VBox();
        this.mongos.getStyleClass().add("ShardedClusterTopologyComponentMongos");

        this.configServers = new VBox();
        this.configServers.getStyleClass().add("ShardedClusterTopologyComponentConfigServers");

        for (final var shard : topology.shards()) {
            this.shards.getChildren().add(new ShardComponent(shard));
        }

        for (final var mongos : topology.mongos()) {
            this.mongos.getChildren().add(new ShardMongosComponent(mongos));
        }

        for (final var configServers : emptyList()) {
            this.configServers.getChildren().add(null);
        }

        this.getChildren().add(shards);
        this.getChildren().add(mongos);
        this.getChildren().add(configServers);
    }
}
