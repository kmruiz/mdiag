package cat.kmruiz.mdiag;

import cat.kmruiz.mdiag.overview.topology.sharded.ShardedClusterTopologyAnalyzer;
import cat.kmruiz.mdiag.ui.components.topology.ShardedClusterTopologyComponent;
import com.mongodb.client.MongoClients;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MDiag extends Application {
    @Override
    public void start(Stage stage) {
        final var localhost = MongoClients.create("mongodb://localhost/?retryWrites=true&w=majority");
        final var analyzer = new ShardedClusterTopologyAnalyzer(localhost);

        final var topology = analyzer.analyze();

        final var root = new VBox();
        root.getChildren().add(new ShardedClusterTopologyComponent(topology));

        final var scene = new Scene(new ScrollPane(root), 1920, 1080);
        stage.setScene(scene);
        stage.show();
    }
}
