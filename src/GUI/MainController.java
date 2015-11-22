package GUI;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import AStar.Node;

public class MainController implements Initializable{
    @FXML private AnchorPane anchorPane;
    @FXML private Button loadMapBtn;
    @FXML private ImageView mapView;
    @FXML private AnchorPane imageAnchorPane;
    @FXML private Canvas imageCanvas;
    File file = new File("src/res/stratton_2.jpg");
    final Image mapImage = new Image(file.toURI().toString());
    private boolean nodeSelect = false;
    Node startNode = null;
    Node goalNode = null;
    private double scaleX = 1;
    private double scaleY = 1;
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
    	scaleX = viewWidth / mapImage.getWidth();
    	scaleY = viewHeight / mapImage.getHeight();
    	//anchorPaneScroll.setPrefWidth(viewWidth);
    	//anchorPaneScroll.setPrefHeight(viewHeight);
    	System.out.println("X = " + scaleX);
    	System.out.println("Y = " + scaleY);
        drawNodes(scaleX, scaleY, 30, Main.testMap);
    }
    @FXML 
    protected void handleRunAStar(ActionEvent event) {
    	if (startNode != null && goalNode != null)
    	{
    		List<Node> path = Main.getPathFromNode(startNode, goalNode, Main.testMap);
    		drawPath(scaleX, scaleY, path);
    		startNode = null;
    		goalNode = null;
    		System.out.println("The Path is");
    		System.out.println(path);
    	}
    }
    protected void drawNodes(double scaleX, double scaleY, double btnRadius, List<Node> nodeList)
    {
    	Color color = Color.web("#3366cc");
    	for(Node node : nodeList)
    	{
			//Circle c = new Circle(node.xPos * scaleX, node.yPos * scaleY, 10, color);
			//Button btn = new Button("",circle);
			Button btn = new Button("");
			btn.setId(node.nodeName);
			btn.setLayoutX(node.xPos * scaleX - 10);
			btn.setLayoutY(node.yPos * scaleY - 10);
			double r = btnRadius * scaleX;
			btn.setShape(new Circle(r));
			btn.setMinSize(2*r, 2*r);
			btn.setMaxSize(2*r, 2*r);
			btn.setOnAction(new EventHandler<ActionEvent>() {
				 
			    @Override
			    public void handle(ActionEvent e) {
			    	System.out.println("You Clicked Node " + btn.getId());
			    	if (nodeSelect == false)
			    	{
			    		startNode = Main.findNodeByName(Main.testMap, btn.getId());
			    		nodeSelect = true;
			    	
			    	}
			    	else
			    	{
			    		goalNode = Main.findNodeByName(Main.testMap, btn.getId());
			    		nodeSelect = false;
			    	}
			    }
			});
			imageAnchorPane.getChildren().add(btn);
    	}
    }
    protected void drawPath(double scaleX, double scaleY, List<Node> path)
    {	
    	imageCanvas.getGraphicsContext2D().clearRect(0, 0, imageCanvas.getWidth(), imageCanvas.getHeight());
    	imageCanvas.setWidth(imageAnchorPane.getWidth());
    	imageCanvas.setHeight(imageAnchorPane.getHeight());
    	for(int i = 0; i < path.size() - 1; i++)
    	{
    		Node n1 = path.get(i);
    		Node n2 = path.get(i+1);
    		imageCanvas.getGraphicsContext2D().strokeLine(n1.xPos * scaleX - 10, n1.yPos * scaleY - 10, n2.xPos * scaleX - 10, n2.yPos * scaleY - 10);
    	}
    }

}