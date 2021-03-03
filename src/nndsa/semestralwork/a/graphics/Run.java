package nndsa.semestralwork.a.graphics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import nndsa.semestralwork.a.resource.Dialog;

/**
 *
 * @author milan.horak
 */
public class Run extends Application {

    static Stage STAGE;
    final static String AUTHOR = "Application created by Milan Horák (st43189).";
    final static String APPLICATION_NAME = "NNSDA Semestral Work (A)";

    final static ImageView APPLICATION_ICON_1 = new ImageView(new Image("/nndsa/semestralwork/a/resource/telescope_1.png"));
    final static ImageView APPLICATION_ICON_2 = new ImageView(new Image("/nndsa/semestralwork/a/resource/telescope_2.png"));
    final static ImageView APPLICATION_ICON_3 = new ImageView(new Image("/nndsa/semestralwork/a/resource/telescope_3.png"));
    final static ImageView APPLICATION_ICON_4 = new ImageView(new Image("/nndsa/semestralwork/a/resource/telescope_4.png"));

    @Override
    public void start(Stage stage) throws Exception {
        STAGE = stage;
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/nndsa/semestralwork/a/style/DarkTheme.css");

        stage.setOnCloseRequest(event -> {
            event.consume();
            Dialog.exit(stage, Run.AUTHOR, APPLICATION_NAME, APPLICATION_ICON_3);
        });
        stage.getIcons().add(APPLICATION_ICON_3.getImage());
        stage.setTitle(APPLICATION_NAME);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
