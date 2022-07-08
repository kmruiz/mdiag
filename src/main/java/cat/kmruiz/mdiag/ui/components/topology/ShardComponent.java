package cat.kmruiz.mdiag.ui.components.topology;

import cat.kmruiz.mdiag.overview.topology.sharded.Shard;
import javafx.geometry.Orientation;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

public class ShardComponent extends FlowPane {
    private final Shard shard;
    private final FlowPane members;
    private final Text shardId;

    public ShardComponent(Shard shard) {
        this.shard = shard;
        this.members = new FlowPane(Orientation.HORIZONTAL);
        this.shardId = new Text(shard.id());

        this.setOrientation(Orientation.VERTICAL);

        for (final var member : shard.members()) {
            this.members.getChildren().add(new ShardMemberComponent(member));
        }

        this.getChildren().add(shardId);
        this.getChildren().add(members);
    }
}
