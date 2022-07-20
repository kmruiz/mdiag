package cat.kmruiz.mdiag.ui.flows;

import cat.kmruiz.mdiag.MDiag;
import cat.kmruiz.mdiag.overview.ExportedReport;
import cat.kmruiz.mdiag.ui.components.topology.ShardedClusterTopologyComponent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class MDialogImportFlow extends VBox {
    public MDialogImportFlow(File report) {
        try {
            final var reportObject = MDiag.JSON.readValue(Files.readString(report.toPath()), ExportedReport.class);
            this.getChildren().add(new ShardedClusterTopologyComponent(reportObject.shardedClusterTopology()));
            MDiag.newStage(report.getAbsolutePath(), new Scene(new ScrollPane(this)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
