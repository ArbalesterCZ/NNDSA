package nndsa.semestralwork.a.graphics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nndsa.semestralwork.a.structures.Graph;

/**
 *
 * @author milan.horak
 */
public class NNDSA_SemestralWork_A extends Application {
    
    private final Graph<String, Integer> graph = new Graph<>();
    
    @Override
    public void start(Stage stage) throws Exception {
        graph.addVertex("a");
        graph.addVertex("b");       
        graph.addVertex("c");
        
        graph.addEdge("a", "b", 200);
        
        graph.addVertex("b");
        
        System.out.println(graph.size());
        //System.out.println(graph.findEdge("b"));
        
        System.out.println(graph.findEdge("b", "c"));
        System.out.println(graph.removeEdge("a", "b"));
        //System.out.println("A:" + graph.removeVertex("a"));
        //System.out.println("B:" + graph.removeVertex("b"));
        //System.out.println("C:" + graph.removeVertex("c"));
        //System.out.println(graph.size());
        
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
