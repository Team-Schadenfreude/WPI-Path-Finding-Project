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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import AStar.Node;
import DataAccess.Building;
import DataAccess.Room;
import DataAccess.RoomReader;

public class MainController implements Initializable{
    @FXML private AnchorPane anchorPane;
    @FXML private Button loadMapBtn;
    @FXML private Button goBtn;
    @FXML private ImageView mapView;
    @FXML private MenuButton startMenu;
    @FXML private MenuButton destMenu;
    @FXML private Canvas imageCanvas;
    List<Building> buildingList;
    private boolean nodeSelect = false;
    Node startNode = null;
    Node goalNode = null;
    private double scaleX = 1;
    private double scaleY = 1;
    public MainController(){
    	
    }
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	System.out.println("BeforePath");
    	startMenu.getItems().clear();
    	destMenu.getItems().clear();
	}
    @FXML 
    protected void handleLoadMap(ActionEvent event) {
    	
    	DirectoryChooser chooser = new DirectoryChooser();
    	chooser.setTitle("JavaFX Projects");
    	File defaultDirectory = new File("c:/");
    	chooser.setInitialDirectory(defaultDirectory);
    	File selectedDirectory = chooser.showDialog(Main.primaryStage);
    	
    	setupDropDowns(selectedDirectory + "\\Rooms.csv");
    	System.out.println(selectedDirectory + "\\Rooms.csv");
    	File file = new File(selectedDirectory + "\\map.png");
    	Image mapImage = new Image(file.toURI().toString());
        mapView.setImage(mapImage);
        //readin data
        double viewHeight = (int) mapView.getBoundsInLocal().getHeight();
    	double viewWidth = (int) mapView.getBoundsInLocal().getWidth();
    	scaleX = viewWidth / mapImage.getWidth();
    	scaleY = viewHeight / mapImage.getHeight();
    	System.out.println("X = " + scaleX);
    	System.out.println("Y = " + scaleY);
        drawNodeBtns(scaleX, scaleY, 30, Main.testMap);
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
    protected void drawNodeBtns(double scaleX, double scaleY, double btnRadius, List<Node> nodeList)
    {
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
			anchorPane.getChildren().add(btn);
    	}
    }
    protected void drawPath(double scaleX, double scaleY, List<Node> path)
    {	
    	imageCanvas.getGraphicsContext2D().clearRect(0, 0, imageCanvas.getWidth(), imageCanvas.getHeight());
    	imageCanvas.setWidth(anchorPane.getWidth());
    	imageCanvas.setHeight(anchorPane.getHeight());
    	for(int i = 0; i < path.size() - 1; i++)
    	{
    		Node n1 = path.get(i);
    		Node n2 = path.get(i+1);
    		imageCanvas.getGraphicsContext2D().strokeLine(n1.xPos * scaleX, n1.yPos * scaleY, n2.xPos * scaleX, n2.yPos * scaleY);
    	}
    }
    private void setupDropDowns(String path)
    {
    	buildingList = RoomReader.getBuildingList(path);
    	startMenu.getItems().clear();
    	destMenu.getItems().clear();
    	for (Building b : buildingList)
    	{
    		Menu rooms = new Menu();
    		Menu rooms2 = new Menu();
    		rooms.setText(b.getName());
    		rooms2.setText(b.getName());
    		for (Room r : b.getRooms())
    		{
    			MenuItem mi1 = new MenuItem(r.getName());
    			MenuItem mi2 = new MenuItem(r.getName());
    			mi1.setOnAction(new EventHandler<ActionEvent>() {
    			    @Override public void handle(ActionEvent e) {
    			    	startNode = Main.findNodeByName(Main.testMap, mi1.getText());
    			    	System.out.println("Start Node Selected");
    			    }
    			});
    			mi2.setOnAction(new EventHandler<ActionEvent>() {
    			    @Override public void handle(ActionEvent e) {
    			        goalNode = Main.findNodeByName(Main.testMap, mi2.getText());
    			        System.out.println("Goal Node Selected");
    			    }
    			});
    			rooms.getItems().add(mi1);
    			rooms2.getItems().add(mi2);
    		}
    		startMenu.getItems().add(rooms);
    		destMenu.getItems().add(rooms2);
    	}
    	
    }

}