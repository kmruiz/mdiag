package cat.kmruiz.mdiag.ui.flows;

import cat.kmruiz.mdiag.MDiag;
import cat.kmruiz.mdiag.overview.topology.sharded.ShardedClusterTopologyAnalyzer;
import cat.kmruiz.mdiag.ui.JavaFXApplication;
import cat.kmruiz.mdiag.ui.components.topology.ShardedClusterTopologyComponent;
import com.mongodb.client.MongoClients;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class MDialogLiveFlow extends VBox {
    public MDialogLiveFlow(String connectionUrl) {
        final var localhost = MongoClients.create(connectionUrl);
        final var analyzer = new ShardedClusterTopologyAnalyzer(localhost);

        final var topology = analyzer.analyze();
        this.getChildren().add(new ShardedClusterTopologyComponent(topology));

        JavaFXApplication.newStage("mdialog", new Scene(new ScrollPane(this)));
    }
}
