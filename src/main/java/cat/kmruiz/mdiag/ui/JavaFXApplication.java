package cat.kmruiz.mdiag.ui;

import cat.kmruiz.mdiag.ui.flows.MDialogInitFlow;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class JavaFXApplication extends Application {
    public static final String VERSION;

    static {
        try {
            VERSION = new Scanner(JavaFXApplication.class.getResourceAsStream("/version.txt"), "UTF-8").useDelimiter("\\A").next();
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    public static final ObjectMapper JSON = new ObjectMapper()
            .findAndRegisterModules()
            .enable(SerializationFeature.INDENT_OUTPUT);


    private static Stage CURRENT_STAGE = null;

    public static Stage newStage(String title, Scene scene) {
        CURRENT_STAGE.close();
        CURRENT_STAGE = new Stage();
        CURRENT_STAGE.setTitle(title + " - mdiag " + VERSION);
        CURRENT_STAGE.setScene(scene);
        CURRENT_STAGE.show();

        return CURRENT_STAGE;
    }

    public static Stage currentStage() {
        return CURRENT_STAGE;
    }

    @Override
    public void start(Stage stage) {
        final var scene = new Scene(new MDialogInitFlow(), 500, 500);
        stage.setTitle("mdiag " + VERSION);
        stage.setScene(scene);

        stage.show();
        CURRENT_STAGE = stage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
