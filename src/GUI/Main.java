package GUI;
 
//import com.svg.fx.SvgImageLoaderFactory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
 
public class Main extends Application {

	private static Stage primaryStage;
    public static void main(String[] args) {
    	//SvgImageLoaderFactory.install();
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(Main.class.getResource("Main.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        Scene scene = new Scene(root, root.getWidth(), root.getHeight());
        scene.getStylesheets().add("GUI/application.css");
        stage.setTitle("Randy Path Planner");
        stage.setScene(scene);
       
        stage.show();
        
    }
    public static Stage getStage()
    {
    	return primaryStage;
    }
}
