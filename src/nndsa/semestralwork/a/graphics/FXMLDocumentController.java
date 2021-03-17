package nndsa.semestralwork.a.graphics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import nndsa.semestralwork.a.libraries.Dialog;
import nndsa.semestralwork.a.libraries.Dialog.Filter;
import nndsa.semestralwork.a.structures.AStarAlgorithm;
import nndsa.semestralwork.a.structures.AbstrGraphHashTable;
import nndsa.semestralwork.a.structures.AbstrGraphList;
import nndsa.semestralwork.a.structures.IAbstrGraph;
import nndsa.semestralwork.a.structures.Node;
import nndsa.semestralwork.a.structures.Path;
import nndsa.semestralwork.a.structures.Point;
import nndsa.semestralwork.a.structures.Town;

/**
 *
 * @author milan.horak
 */
public class FXMLDocumentController implements Initializable {

    private final static Image MAP = new Image("/nndsa/semestralwork/a/resource/Map.png");
    private final IAbstrGraph<Integer, Town, Path> graph = new AbstrGraphHashTable();

    private GraphicsContext gc;

    private Town focusTown;
    private Town focusTownSecond;
    private Town markedTown;

    private Point coordinate = new Point();

    private Filter[] filters = new Filter[1];

    private List<Node> bestPath = new LinkedList();

    private final int TOWN_SIZE = 16;
    private final int SHIFT = TOWN_SIZE / 2;
    private final int FOCUS_DISTANCE = (int) (TOWN_SIZE * 1.125);

    private Town startTown;
    private Town targetTown;
    
    private Color backgroundColor = Color.GRAY;

    private int index = 0;

    @FXML
    private Canvas canvas;
    @FXML
    private CheckMenuItem CheckTownMode;
    @FXML
    private CheckMenuItem background;
    @FXML
    private CheckMenuItem towns;
    @FXML
    private CheckMenuItem paths;

    private void clearScene() {
        graph.clear();
        bestPath.clear();
        startTown = null;
        targetTown = null;
    }

    private void drawTown(Town town, GraphicsContext gc) {
        int x = town.getX() - SHIFT;
        int y = town.getY() - SHIFT;
        gc.fillOval(x, y, TOWN_SIZE, TOWN_SIZE);
        gc.strokeOval(x, y, TOWN_SIZE, TOWN_SIZE);
    }

    private void render(GraphicsContext gc) {
        if (background.isSelected()) {
            gc.drawImage(MAP, 0, 0);
        } else {
            gc.setFill(backgroundColor);
            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        }
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLACK);
        if (paths.isSelected()) {
            for (Town town : graph) {
                for (Path path : graph.findIncidentElements(town.hashCode())) {
                    gc.strokeLine(path.start.getX(), path.start.getY(), path.target.getX(), path.target.getY());
                }
            }

            gc.setStroke(Color.RED);
            for (int i = 0; i < bestPath.size() - 1; i++) {
                gc.strokeLine(
                        bestPath.get(i).state.getX(), bestPath.get(i).state.getY(),
                        bestPath.get(i + 1).state.getX(), bestPath.get(i + 1).state.getY());
            }
        }
        if (towns.isSelected()) {
            gc.setStroke(Color.BLACK);
            for (Town town : graph) {
                drawTown(town, gc);
            }
        }
        if (focusTown != null) {
            gc.setFill(Color.RED);
            drawTown(focusTown, gc);
        }
        if (startTown != null) {
            gc.setFill(Color.AQUAMARINE);
            drawTown(startTown, gc);
        }
        if (targetTown != null) {
            gc.setFill(Color.BISQUE);
            drawTown(targetTown, gc);
        }
    }

    private void addPath(Path path) {
        graph.addEdge(path.start.hashCode(), path.target.hashCode(), path);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gc = canvas.getGraphicsContext2D();
        filters[0] = new Filter("Binary File", "*.bin");
        render(gc);
    }

    @FXML
    private void OnDelete(ActionEvent event) {
        if (CheckTownMode.isSelected()) {
            if (markedTown != null) {
                graph.removeVertex(markedTown.hashCode());
                markedTown = null;
                render(gc);
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
        clearScene();
        render(gc);
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
                drawTown(markedTown, gc);
                return;
            }
        }
        markedTown = null;
        render(gc);
    }

    @FXML
    private void OnPressed(MouseEvent event) {
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
            render(gc);
        } else {
            render(gc);
            for (Town town : graph) {
                Point point = new Point((int) event.getX(), (int) event.getY());
                if (point.distance(town.getCoordinate()) <= FOCUS_DISTANCE) {
                    if (focusTown == town) {
                        continue;
                    }
                    coordinate = new Point((int) event.getX(), (int) event.getY());
                    focusTownSecond = town;
                    break;
                }
            }
            if (event.getButton() == MouseButton.PRIMARY) {
                gc.setStroke(Color.BLACK);
                if (focusTownSecond == null) {
                    gc.strokeLine(focusTown.getX(), focusTown.getY(), event.getX(), event.getY());
                } else {
                    gc.strokeLine(focusTown.getX(), focusTown.getY(), focusTownSecond.getX(), focusTownSecond.getY());
                    gc.setFill(Color.RED);
                    drawTown(focusTownSecond, gc);
                }
            } else {
                gc.setStroke(Color.RED);
                if (focusTownSecond == null) {
                    gc.strokeLine(focusTown.getX(), focusTown.getY(), event.getX(), event.getY());
                } else {
                    gc.strokeLine(focusTown.getX(), focusTown.getY(), focusTownSecond.getX(), focusTownSecond.getY());
                    gc.setFill(Color.RED);
                    gc.setStroke(Color.BLACK);
                    drawTown(focusTownSecond, gc);
                }
            }
        }
    }

    @FXML
    private void OnReleased(MouseEvent event) {
        if (CheckTownMode.isSelected()) {
            if (focusTown != null) {
                gc.setFill(Color.GREEN);
                drawTown(focusTown, gc);
                LinkedList<Path> paths = graph.findIncidentElements(focusTown.hashCode());
                for (Path path : paths) {
                    path.length = (int) path.start.getCoordinate().distance(path.target.getCoordinate());
                }
                focusTown = null;
            }
        } else {
            if (event.getButton() == MouseButton.PRIMARY) {
                if (focusTown != null && focusTownSecond != null) {
                    addPath(new Path(focusTown, focusTownSecond));
                }
                focusTown = null;
                focusTownSecond = null;
                render(gc);
            } else {
                if (focusTown != null && focusTownSecond != null) {
                    graph.removeEdge(focusTown.hashCode(), focusTownSecond.hashCode());
                }
                focusTown = null;
                focusTownSecond = null;
                render(gc);
            }
        }
    }

    @FXML
    private void OnClicked(MouseEvent event) {
        if (CheckTownMode.isSelected()) {
            if (event.getButton() == MouseButton.MIDDLE) {
                Town town = new Town("Town_" + (++index), new Point((int) event.getX(), (int) event.getY()));
                graph.addVertex(town.hashCode(), town);
                render(gc);
            }
        }
        if (event.getButton() == MouseButton.PRIMARY && event.isAltDown()) {
            if (markedTown != null) {
                startTown = markedTown;
                render(gc);
            }
        }
        if (event.getButton() == MouseButton.SECONDARY && event.isAltDown()) {
            if (markedTown != null) {
                targetTown = markedTown;
                render(gc);
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
            clearScene();
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
        render(gc);
    }

    @FXML
    private void OnRun(ActionEvent event) {
        if (startTown == null || targetTown == null) {
            Dialog.show(Run.STAGE, Alert.AlertType.INFORMATION, "First select start and target town");
            return;
        }
        AStarAlgorithm AStar = new AStarAlgorithm(graph);
        List<Node> list = AStar.findSolution(startTown, targetTown);
        if (list == null) {
            return;
        }
        bestPath = list;
        for (Node node : list) {
            System.out.println(node);
        }
        render(gc);
    }

    @FXML
    private void OnBackground(ActionEvent event) {
        render(gc);
    }

    @FXML
    private void OnTowns(ActionEvent event) {
        render(gc);
    }

    @FXML
    private void Onpaths(ActionEvent event) {
        render(gc);
    }

    @FXML
    private void OnPrint(ActionEvent event) {
        PrinterJob job = PrinterJob.createPrinterJob();

        if (job != null && job.showPrintDialog(Run.STAGE)) {
            Printer printer = job.getPrinter();
            PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM);

            Canvas printPage = new Canvas(canvas.getWidth(), canvas.getHeight());

            double scaleX = pageLayout.getPrintableWidth() / printPage.getWidth();
            double scaleY = pageLayout.getPrintableHeight() / printPage.getHeight();

            Scale scale = new Scale(scaleX, scaleY);

            backgroundColor = Color.WHITE;
            render(printPage.getGraphicsContext2D());
            backgroundColor = Color.GRAY;
            printPage.getTransforms().add(scale);

            if (job.printPage(pageLayout, printPage)) {
                job.endJob();
            }
        }
    }
}
