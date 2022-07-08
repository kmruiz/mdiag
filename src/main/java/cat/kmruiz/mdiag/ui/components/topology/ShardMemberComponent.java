package cat.kmruiz.mdiag.ui.components.topology;

import cat.kmruiz.mdiag.overview.topology.sharded.ShardMember;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class ShardMemberComponent extends StackPane {
    private final ShardMember shardMember;
    private final Text memberName;

    public ShardMemberComponent(ShardMember shardMember) {
        this.shardMember = shardMember;
        this.memberName = new Text(shardMember.host());

        this.setStyle("-fx-border-color: black;");
        this.getChildren().add(memberName);
    }
}
