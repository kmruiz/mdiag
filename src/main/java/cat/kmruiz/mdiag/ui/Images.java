package cat.kmruiz.mdiag.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public final class Images {
    private Images() {}

    public static final Image MONGODB = new Image(Images.class.getResourceAsStream("/images/mongo.png"));
    public static final Image PRIMARY = new Image(Images.class.getResourceAsStream("/images/primary.png"));
    public static final Image SECONDARY = new Image(Images.class.getResourceAsStream("/images/secondary.png"));
    public static final Image DOWN = new Image(Images.class.getResourceAsStream("/images/secondary.png"));
    public static final Image ARBITER = new Image(Images.class.getResourceAsStream("/images/arbiter.png"));
    public static final Image ANALYTICS = new Image(Images.class.getResourceAsStream("/images/analytics.png"));

    public static ImageView small(Image image) {
        final var view = new ImageView(image);
        view.setFitWidth(64);
        view.setFitHeight(64);

        return view;
    }

    public static ImageView medium(Image image) {
        final var view = new ImageView(image);
        view.setFitWidth(128);
        view.setFitHeight(128);

        return view;
    }

    public static ImageView large(Image image) {
        final var view = new ImageView(image);
        view.setFitWidth(256);
        view.setFitHeight(256);

        return view;
    }
}
