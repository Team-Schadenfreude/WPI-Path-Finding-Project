package GUI;
 
import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

 
public class Main extends Application {

	public static Stage primaryStage;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(Main.class.getResource("Main.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        Scene scene = new Scene(root, root.getWidth(), root.getHeight());
        scene.getStylesheets().add("GUI/application.css");
        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
       
        stage.show();
        
    }
}
