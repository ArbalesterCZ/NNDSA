package nndsa.semestralwork.a.graphics;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import nndsa.semestralwork.a.resource.Dialog;
import nndsa.semestralwork.a.structures.AbstrGraph;
import nndsa.semestralwork.a.structures.Path;
import nndsa.semestralwork.a.structures.Point;
import nndsa.semestralwork.a.structures.Town;

/**
 *
 * @author milan.horak
 */
public class FXMLDocumentController implements Initializable {

    private GraphicsContext gc;

    private final AbstrGraph<String, Town, Path> graph = new AbstrGraph();

    private int TOWN_SIZE = 25;
    @FXML
    private Canvas canvas;

    private void drawTown(Town town) {
        Point coor = town.getCoordinate();
        gc.fillOval(coor.x, coor.y, TOWN_SIZE, TOWN_SIZE);
        gc.strokeOval(coor.x, coor.y, TOWN_SIZE, TOWN_SIZE);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.WHITESMOKE);
        Town townA = new Town("Town_A", new Point(50, 200));
        Town townB = new Town("Town_B", new Point(50, 50));
        Town townC = new Town("Town_C", new Point(300, 50));
        graph.addVertex(townA.hashCode() + "", townA);
        graph.addVertex(townB.hashCode() + "", townB);
        graph.addVertex(townC.hashCode() + "", townC);
        graph.addEdge(townA.hashCode() + "", townB.hashCode() + "", new Path(100, townA, townB));
        graph.addEdge(townA.hashCode() + "", townC.hashCode() + "", new Path(100, townA, townC));

        drawTown(townA);
        LinkedList<Town> list = graph.findSuccessorElements(townA.hashCode() + "");
        for (Town town : list) {
            drawTown(town);
        }
        //test();
    }

    @FXML
    private void OnClose(ActionEvent event) {
        Dialog.exit(Run.STAGE, Run.AUTHOR, Run.APPLICATION_NAME, Run.APPLICATION_ICON_3);
    }

    @FXML
    private void OnAdd(ActionEvent event) {
        System.out.println("OnAdd");
    }

    @FXML
    private void OnDelete(ActionEvent event) {
        System.out.println("OnDelete");
    }

    @FXML
    private void OnFind(ActionEvent event) {
        System.out.println("OnFind");
    }

    private void test() {
        System.out.println(graph.size());

        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, 1);
        Point p3 = new Point(2, 2);
        Town t1 = new Town("Town1", p1);
        Town t2 = new Town("Town2", p2);
        Town t3 = new Town("Town3", p3);
        graph.addVertex(t1.hashCode() + "", t1);
        graph.addVertex(t2.hashCode() + "", t2);
        graph.addVertex(t3.hashCode() + "", t3);

        Path path1 = new Path(200, t1, t2);
        Path path2 = new Path(300, t1, t3);
        graph.addEdge(t1.hashCode() + "", t2.hashCode() + "", path1);
        graph.addEdge(t1.hashCode() + "", t3.hashCode() + "", path2);

        System.out.println(graph);
        System.out.println(graph.findVertex(t1.hashCode() + ""));
        System.out.println(graph.findVertex(t2.hashCode() + ""));
        System.out.println(graph.findVertex(t3.hashCode() + ""));
        System.out.println(graph.findEdge(t1.hashCode() + "", t2.hashCode() + ""));
        graph.removeVertex(t3.hashCode() + "");
        System.out.println("REMOVE t3 - " + graph);
        graph.removeVertex(t1.hashCode() + "");
        System.out.println("REMOVE t1 - " + graph);
        System.out.println(graph);
    }
}
