package nndsa.semestralwork.a.graphics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import nndsa.semestralwork.a.structures.AbstrGraph;
import nndsa.semestralwork.a.structures.Point;

/**
 *
 * @author milan.horak
 */
public class NNDSA_SemestralWork_A extends Application {

    private final AbstrGraph<String, Point, Integer> graph = new AbstrGraph();

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println(graph.size());

        graph.addVertex("a", new Point(0, 0));
        graph.addVertex("b", new Point(1, 1));
        graph.addVertex("c", new Point(2, 2));

        graph.addEdge("a", "b", 200);
        graph.addEdge("a", "c", 300);

        System.out.println(graph);
        System.out.println(graph.findVertex("a"));
        System.out.println(graph.findVertex("b"));
        System.out.println(graph.findVertex("c"));
        System.out.println(graph.findEdge("a", "b"));
        graph.removeVertex("a");
        System.out.println("REMOVE a - " + graph);
        graph.removeVertex("c");
        System.out.println("REMOVE C - " + graph);
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
