package cat.kmruiz.mdiag.ui.components.topology;

import cat.kmruiz.mdiag.overview.topology.sharded.ShardMember;
import cat.kmruiz.mdiag.overview.topology.sharded.ShardMongos;
import cat.kmruiz.mdiag.ui.Css;
import cat.kmruiz.mdiag.ui.Images;
import javafx.geometry.Orientation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ShardMongosComponent extends VBox {
    private final ShardMongos mongos;
    private final ImageView mongosImage;
    private final Text memberName;

    public ShardMongosComponent(ShardMongos mongos) {
        Css.apply(this);

        this.mongos = mongos;

        this.mongosImage = Images.small(Images.MONGODB);
        this.memberName = new Text(mongos.id());

        memberName.getStyleClass().add("ShardMongosComponentMemberName");

        this.getChildren().add(mongosImage);
        this.getChildren().add(memberName);
    }
}
