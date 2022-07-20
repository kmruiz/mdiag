package cat.kmruiz.mdiag;

import cat.kmruiz.mdiag.overview.topology.sharded.ShardedClusterTopologyAnalyzer;
import cat.kmruiz.mdiag.ui.components.topology.ShardedClusterTopologyComponent;
import com.mongodb.client.MongoClients;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class MDiag extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        final var localhost = MongoClients.create("mongodb://localhost/?retryWrites=true&w=majority");
        final var analyzer = new ShardedClusterTopologyAnalyzer(localhost);

        final var topology = analyzer.analyze();

        final var root = new FlowPane(Orientation.HORIZONTAL);
        root.getChildren().add(new ShardedClusterTopologyComponent(topology));

        final var scene = new Scene(root, 800, 800);
        stage.setScene(scene);
        stage.show();
    }
}
