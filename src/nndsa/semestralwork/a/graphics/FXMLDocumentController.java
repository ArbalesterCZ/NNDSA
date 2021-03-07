package nndsa.semestralwork.a.graphics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import nndsa.semestralwork.a.resource.Dialog;
import nndsa.semestralwork.a.resource.Dialog.Filter;
import nndsa.semestralwork.a.structures.AbstrGraph;
import nndsa.semestralwork.a.structures.IAbstrGraph;
import nndsa.semestralwork.a.structures.Path;
import nndsa.semestralwork.a.structures.Point;
import nndsa.semestralwork.a.structures.Town;

/**
 *
 * @author milan.horak
 */
public class FXMLDocumentController implements Initializable {

    private GraphicsContext gc;

    private IAbstrGraph<Integer, Town, Path> graph = new AbstrGraph();

    final static ImageView MAP = new ImageView(new Image("/nndsa/semestralwork/a/resource/Map.png"));

    private Town focusTown;
    private Town focusTownSecond;
    private Town markedTown;

    private Point coordinate = new Point();

    private Filter[] filters = new Filter[1];

    private final int TOWN_SIZE = 16;
    private final int SHIFT = TOWN_SIZE / 2;
    private final int FOCUS_DISTANCE = (int) (TOWN_SIZE * 1.25);

    private int index = 0;

    @FXML
    private Canvas canvas;
    @FXML
    private CheckMenuItem CheckTownMode;

    private void drawTown(Town town) {
        Point coor = new Point(town.getX(), town.getY());
        gc.fillOval(coor.x - SHIFT, coor.y - SHIFT, TOWN_SIZE, TOWN_SIZE);
        gc.strokeOval(coor.x - SHIFT, coor.y - SHIFT, TOWN_SIZE, TOWN_SIZE);
    }

    private void render() {
        gc.drawImage(MAP.getImage(), 0, 0);
        gc.setFill(Color.GREEN);
        for (Town town : graph) {
            for (Path path : graph.findIncidentElements(town.hashCode())) {
                gc.strokeLine(path.start.getX(), path.start.getY(), path.target.getX(), path.target.getY());
            }
        }
        for (Town town : graph) {
            drawTown(town);
        }
        if (focusTown != null) {
            gc.setFill(Color.RED);
            drawTown(focusTown);
        }
    }

    private void addPath(Path path) {
        graph.addEdge(path.start.hashCode(), path.target.hashCode(), path);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gc = canvas.getGraphicsContext2D();
        filters[0] = new Filter("Text File", "*.bin");
        render();
    }

    @FXML
    private void OnAdd(ActionEvent event) {
        System.out.println("OnAdd");
    }

    @FXML
    private void OnDelete(ActionEvent event) {
        if (CheckTownMode.isSelected()) {
            if (markedTown != null) {
                graph.removeVertex(markedTown.hashCode());
                markedTown = null;
                render();
            }
        }
    }

    @FXML
    private void OnFind(ActionEvent event) {
        if (CheckTownMode.isSelected()) {
            if (markedTown != null) {
                Dialog.show(Run.STAGE, Alert.AlertType.INFORMATION, markedTown.toString());
            }
        }
    }

    @FXML
    private void OnClear(ActionEvent event) {
        graph.clear();
        render();
    }

    @FXML
    private void OnAbout(ActionEvent event) {
        Dialog.show(Run.STAGE, Alert.AlertType.INFORMATION, graph.toString());
    }

    @FXML
    private void OnExit(ActionEvent event) {
        Dialog.exit(Run.STAGE, Run.AUTHOR, Run.APPLICATION_NAME, Run.APPLICATION_ICON_3);
    }

    @FXML
    private void OnMoved(MouseEvent event) {
        for (Town town : graph) {
            if (event.getX() > town.getX() - FOCUS_DISTANCE
                    && event.getX() < town.getX() + FOCUS_DISTANCE
                    && event.getY() > town.getY() - FOCUS_DISTANCE
                    && event.getY() < town.getY() + FOCUS_DISTANCE) {
                markedTown = town;
                gc.setFill(Color.YELLOW);
                drawTown(markedTown);
                return;
            }
        }
        if (markedTown != null) {
            gc.setFill(Color.GREEN);
            drawTown(markedTown);
            markedTown = null;
        }
    }

    @FXML
    private void OnPressed(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            for (Town town : graph) {
                if (event.getX() > town.getX() - FOCUS_DISTANCE
                        && event.getX() < town.getX() + FOCUS_DISTANCE
                        && event.getY() > town.getY() - FOCUS_DISTANCE
                        && event.getY() < town.getY() + FOCUS_DISTANCE) {
                    coordinate = new Point((int) event.getX(), (int) event.getY());
                    focusTown = town;
                    break;
                }
            }
        }
    }

    @FXML
    private void OnDragged(MouseEvent event) {
        if (focusTown == null) {
            return;
        }
        focusTownSecond = null;
        if (CheckTownMode.isSelected()) {
            int addendX = (int) (event.getX() - coordinate.x);
            int addendY = (int) (event.getY() - coordinate.y);

            coordinate = new Point((int) event.getX(), (int) event.getY());

            focusTown.setX(focusTown.getX() + addendX);
            focusTown.setY(focusTown.getY() + addendY);
            render();
        } else {
            render();
            for (Town town : graph) {
                if (event.getX() > town.getX() - FOCUS_DISTANCE
                        && event.getX() < town.getX() + FOCUS_DISTANCE
                        && event.getY() > town.getY() - FOCUS_DISTANCE
                        && event.getY() < town.getY() + FOCUS_DISTANCE) {
                    if (focusTown == town) {
                        continue;
                    }
                    coordinate = new Point((int) event.getX(), (int) event.getY());
                    focusTownSecond = town;
                    break;
                }
            }
            if (focusTownSecond == null) {
                gc.strokeLine(focusTown.getX(), focusTown.getY(), event.getX(), event.getY());
            } else {
                gc.strokeLine(focusTown.getX(), focusTown.getY(), focusTownSecond.getX(), focusTownSecond.getY());
                gc.setFill(Color.RED);
                drawTown(focusTownSecond);
            }
        }
    }

    @FXML
    private void OnReleased(MouseEvent event) {
        if (CheckTownMode.isSelected()) {
            if (focusTown != null) {
                gc.setFill(Color.GREEN);
                drawTown(focusTown);
                focusTown = null;
            }
        } else {
            if (focusTown != null && focusTownSecond != null) {
                addPath(new Path((int) focusTown.getCoordinate().distance(focusTownSecond.getCoordinate()), focusTown, focusTownSecond));
            }
            focusTown = null;
            focusTownSecond = null;
            render();
        }
    }

    @FXML
    private void OnClicked(MouseEvent event) {
        if (CheckTownMode.isSelected()) {
            if (event.getButton() == MouseButton.SECONDARY) {
                Town town = new Town("Town Name" + index++, new Point((int) event.getX(), (int) event.getY()));
                graph.addVertex(town.hashCode(), town);
                render();
            }
        }
    }

    @FXML
    private void OnSave(ActionEvent event) throws IOException {
        try {
            File file = Dialog.chooseFile(Run.STAGE, Dialog.Type.SAVE, filters);
            if (file == null) {
                return;
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeInt(index);
            objectOutputStream.writeInt(graph.verticlesCount());
            for (Town town : graph) {
                objectOutputStream.writeObject(town);
            }
            for (Town town : graph) {
                LinkedList<Path> pathList = graph.findIncidentElements(town.hashCode());
                objectOutputStream.writeInt(pathList.size());
                for (Path path : pathList) {
                    objectOutputStream.writeObject(path);
                }
            }
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void OnLoad(ActionEvent event) {
        try {
            File file = Dialog.chooseFile(Run.STAGE, Dialog.Type.OPEN, filters);
            if (file == null) {
                return;
            }
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            graph = new AbstrGraph<Integer, Town, Path>();
            index = objectInputStream.readInt();
            int verticlesSize = objectInputStream.readInt();
            for (int i = 0; i < verticlesSize; i++) {
                Town town = (Town) objectInputStream.readObject();
                graph.addVertex(town.hashCode(), town);
            }
            for (Town town : graph) {
                int pathCount = objectInputStream.readInt();
                for (int i = 0; i < pathCount; i++) {
                    addPath((Path) objectInputStream.readObject());
                }
            }
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e);
        }
        render();
    }
}
