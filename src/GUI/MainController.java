package GUI;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import AStar.Node;

public class MainController implements Initializable{
    @FXML private AnchorPane anchorPane;
    @FXML private Button loadMapBtn;
    @FXML private ImageView mapView;
    @FXML private ScrollPane imageScrollPane;
    @FXML private AnchorPane imageAnchorPane;
    File file = new File("src/res/stratton_2.jpg");
    final Image mapImage = new Image(file.toURI().toString());
    public MainController(){
    	
    }
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	}
    @FXML 
    protected void handleLoadMap(ActionEvent event) {
        mapView.setImage(mapImage);
        //readin data
        double viewHeight = (int) mapView.getBoundsInLocal().getHeight();
    	double viewWidth = (int) mapView.getBoundsInLocal().getWidth();
    	double scaleX = viewWidth / mapImage.getWidth();
    	double scaleY = viewHeight / mapImage.getHeight();
    	System.out.println("X = " + scaleX);
    	System.out.println("Y = " + scaleY);
        drawNodes(mapView, scaleX, scaleY, Main.testMap);
    }
    
    protected void drawNodes(ImageView iv, double scaleX, double scaleY, List<Node> nodeList)
    {
    	Color color = Color.web("#3366cc");
    	for(Node node : nodeList)
    	{
			Circle c = new Circle(node.xPos * scaleX, node.yPos * scaleY, 10, color);
			imageAnchorPane.getChildren().add(c);
    	}
    }

}