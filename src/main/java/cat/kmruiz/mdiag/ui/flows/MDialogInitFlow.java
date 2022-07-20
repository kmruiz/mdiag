package cat.kmruiz.mdiag.ui.flows;

import cat.kmruiz.mdiag.MDiag;
import cat.kmruiz.mdiag.ui.Css;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

public class MDialogInitFlow extends VBox {
    private final TextField connectionStringTextField;

    public MDialogInitFlow() {
        super();
        Css.apply(this);

        final var optionsPane = new VBox();
        optionsPane.getStyleClass().add("MDialogInitFlowOptionsPane");
        final var connectionPane = new HBox();
        connectionPane.getStyleClass().add("MDialogInitFlowConnectionPane");
        final var submitButton = new Button("Connect");
        final var importButton = new Button("Import");

        connectionStringTextField = new TextField();
        connectionPane.getChildren().addAll(
                new Label("Connection URL:"),
                connectionStringTextField,
                submitButton
        );

        optionsPane.getChildren().add(sectionSeparator("Connect to a live cluster:"));
        optionsPane.getChildren().add(connectionPane);
        optionsPane.getChildren().add(sectionSeparator("or import a mdiag.json file: "));
        optionsPane.getChildren().add(importButton);
        getChildren().add(optionsPane);

        submitButton.setOnAction(this::doConnect);
        importButton.setOnAction(this::doImport);
    }

    private Text sectionSeparator(String text) {
        final var separator = new Text(text);
        separator.getStyleClass().add("MDialogInitFlowSectionSeparator");

        return separator;
    }

    private void doConnect(ActionEvent e) {
        new MDialogLiveFlow(connectionStringTextField.getText());
    }

    private void doImport(ActionEvent e) {
        final var fileChooser = new FileChooser();
        fileChooser.setTitle("Export");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("mdiag.json Report", "*.json"));

        final var reportFile = fileChooser.showOpenDialog(MDiag.currentStage());
        if (reportFile != null && reportFile.exists()) {
            new MDialogImportFlow(reportFile);
        }
    }
}
