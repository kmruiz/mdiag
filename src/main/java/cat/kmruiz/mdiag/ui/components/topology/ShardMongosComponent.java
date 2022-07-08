package cat.kmruiz.mdiag.ui.components.topology;

import cat.kmruiz.mdiag.overview.topology.sharded.ShardMember;
import cat.kmruiz.mdiag.overview.topology.sharded.ShardMongos;
import javafx.geometry.Orientation;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

public class ShardMongosComponent extends FlowPane {
    private final ShardMongos mongos;
    private final Text memberName;

    public ShardMongosComponent(ShardMongos mongos) {
        this.mongos = mongos;
        this.memberName = new Text(mongos.id());

        this.setOrientation(Orientation.VERTICAL);

        this.setStyle("-fx-border-color: black;");
        this.getChildren().add(memberName);
    }
}
