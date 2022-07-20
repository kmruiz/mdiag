package cat.kmruiz.mdiag.ui.components.topology;

import cat.kmruiz.mdiag.MDiag;
import cat.kmruiz.mdiag.overview.ExportedReport;
import cat.kmruiz.mdiag.overview.topology.sharded.ShardedClusterTopology;
import cat.kmruiz.mdiag.ui.Css;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Date;

import static java.util.Collections.emptyList;

public class ShardedClusterTopologyComponent extends HBox {
    private final ShardedClusterTopology topology;
    private final VBox shards;
    private final VBox mongos;
    private final VBox configServers;
    private final Button exportButton;

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

        this.exportButton = new Button("Export");

        this.getChildren().add(shards);
        this.getChildren().add(configServers);
        this.getChildren().add(mongos);
        this.getChildren().add(exportButton);

        exportButton.setOnAction(this::doExport);
    }

    private void doExport(ActionEvent event) {
        final var fileChooser = new FileChooser();
        fileChooser.setTitle("Export");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("mdiag.json Report", "*.json"));

        final var file = fileChooser.showSaveDialog(MDiag.currentStage());
        if (file != null) {
            final var version = MDiag.VERSION;
            final var date = new Date();
            final var report = new ExportedReport(version, date, topology);
            try {
                final var reportJson = MDiag.JSON.writeValueAsString(report);
                Files.writeString(file.toPath(), reportJson, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
