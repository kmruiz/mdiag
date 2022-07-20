package cat.kmruiz.mdiag.ui;

import javafx.scene.Node;
import javafx.scene.Parent;

import java.nio.file.Files;
import java.nio.file.Paths;

public final class Css {
    private Css() {}

    public static void apply(Parent javafxNode) {
        final var className = javafxNode.getClass().getSimpleName();
        final var cssFileName = className + ".css";

        try {
            final var resourceName = "/styles/" + cssFileName;

            javafxNode.getStyleClass().add(className);
            javafxNode.getStylesheets().add(javafxNode.getClass().getResource(resourceName).toExternalForm());
        } catch (Throwable ex) {
            throw new Error(ex);
        }
    }
}
