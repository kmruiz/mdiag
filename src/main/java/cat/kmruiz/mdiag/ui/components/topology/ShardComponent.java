package cat.kmruiz.mdiag.ui.components.topology;

import cat.kmruiz.mdiag.overview.topology.sharded.Shard;
import cat.kmruiz.mdiag.ui.Css;
import cat.kmruiz.mdiag.ui.Images;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ShardComponent extends VBox {
    private final Shard shard;
    private final HBox innerLayout;

    private final HBox nodes;
    private final HBox shardTitle;

    public ShardComponent(Shard shard) {
        Css.apply(this);

        this.shard = shard;

        this.innerLayout = new HBox();
        this.shardTitle = new HBox();
        this.shardTitle.getStyleClass().add("ShardComponentTitle");

        this.nodes = new HBox();
        final var down = new VBox();
        down.getStyleClass().add("ShardComponentGraph");
        final var arbiters = new VBox();
        arbiters.getStyleClass().add("ShardComponentGraph");
        final var analytics = new VBox();
        analytics.getStyleClass().add("ShardComponentGraph");
        final var secondaries = new VBox();
        secondaries.getStyleClass().add("ShardComponentGraph");
        final var primary = new VBox();
        primary.getStyleClass().add("ShardComponentGraph");

        for (final var node : shard.members()) {
            switch (node.nodeType()) {
                case DOWN -> down.getChildren().add(Images.small(Images.DOWN));
                case ARBITER -> arbiters.getChildren().add(Images.small(Images.ARBITER));
                case ANALYTICS -> analytics.getChildren().add(Images.small(Images.ANALYTICS));
                case SECONDARY -> secondaries.getChildren().add(Images.small(Images.SECONDARY));
                case PRIMARY -> primary.getChildren().add(Images.small(Images.PRIMARY));
            }
        }

        this.nodes.getChildren().add(down);
        this.nodes.getChildren().add(arbiters);
        this.nodes.getChildren().add(analytics);
        this.nodes.getChildren().add(secondaries);
        this.nodes.getChildren().add(primary);

        this.innerLayout.getChildren().add(new ShardOwningCollectionInfoComponent(shard));
        this.innerLayout.getChildren().add(nodes);
        this.shardTitle.getChildren().add(new Text(shard.id()));
        this.getChildren().add(innerLayout);
        this.getChildren().add(shardTitle);
    }
}
