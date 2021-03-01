package nndsa.semestralwork.a.graphics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import nndsa.semestralwork.a.structures.AbstrGraph;
import nndsa.semestralwork.a.structures.Path;
import nndsa.semestralwork.a.structures.Point;
import nndsa.semestralwork.a.structures.Town;

/**
 *
 * @author milan.horak
 */
public class NNDSA_SemestralWork_A extends Application {

    private final AbstrGraph<String, Town, Path> graph = new AbstrGraph();

    @Override
    public void start(Stage stage) throws Exception {
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

        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
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
