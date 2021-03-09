package nndsa.semestralwork.a.libraries;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javax.imageio.ImageIO;

/**
 *
 * @author kelie
 */
public final class Dialog {

    public enum Type {
        OPEN("Open"), SAVE("Save");

        private final String TEXT;

        private Type(String text) {
            this.TEXT = text;
        }

        @Override
        public String toString() {
            return TEXT;
        }
    }

    private Dialog() {
    }

    public static File chooseFile(Stage stage, Type dialogType, Filter[] filter) {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle(dialogType.toString());
        for (Filter innerFilter : filter) {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(innerFilter.description, Arrays.asList(innerFilter.extensions)));
        }

        File selectedFile = null;
        switch (dialogType) {
            case OPEN:
                selectedFile = fileChooser.showOpenDialog(stage);
                break;
            case SAVE:
                selectedFile = fileChooser.showSaveDialog(stage);
        }
        return selectedFile;
    }

    public static BufferedImage chooseImage(Stage stage) {
        final String[] FILTER = {"*.bmp", "*.png", "*.jpg", "*.jpeg"};
        final Filter[] FILTERS = new Filter[1];
        FILTERS[0] = new Filter("Images", FILTER);
        File file = Dialog.chooseFile(stage, Dialog.Type.OPEN, FILTERS);
        if (file == null) {
            return null;
        }
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(file);
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
        return bufferedImage;
    }

    public static void show(Window window, Alert.AlertType type, String text) {
        Alert alert = new Alert(type);

        alert.setTitle(type.toString());
        alert.setContentText(text);
        alert.setHeaderText(null);
        alert.initOwner(window);
        alert.showAndWait();
    }

    public static void exit(Window window, String author, String appName, ImageView image) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Are you sure to exit the " + appName + "?");
        alert.setContentText(author);
        alert.initOwner(window);

        if (image != null) {
            alert.setGraphic(image);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(image.getImage());
        }
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Platform.exit();
            System.exit(0);
        }
    }

    public static class Filter {

        public String description;
        public String[] extensions;

        public Filter(String description, String[] extensions) {
            this.description = description;
            this.extensions = extensions;
        }

        public Filter(String description, String extension) {
            this.description = description;
            this.extensions = new String[1];
            this.extensions[0] = extension;
        }
    }
}
