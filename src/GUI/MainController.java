package GUI;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
import javafx.scene.transform.Rotate;
import javafx.stage.DirectoryChooser;
import AStar.Node;
import DataAccess.AngledImage;
import DataAccess.Building;
import DataAccess.Room;
import DataAccess.RoomReader;

public class MainController implements Initializable{
	//FXML Layout Objects
    @FXML private AnchorPane anchorPane;
    @FXML private Button loadMapBtn;
    @FXML private Button goBtn;
    @FXML private ImageView mapView;
    @FXML private MenuButton startMenu;
    @FXML private MenuButton destMenu;
    @FXML private Canvas imageCanvas;
    @FXML private AnchorPane scrollAnchorPane;
    //The list of all buildings on Campus
    List<Building> buildingList;
    //List of Map Images with index 0 being the primary map
    List<AngledImage> mapImages = new LinkedList<AngledImage>();
    //Boolean marking a node as selected
    private boolean nodeSelect = false;
    //The start and end nodes for AStar
    Node startNode = null;
    Node goalNode = null;
    //Global Scale Values
    private double scaleX = 1;
    private double scaleY = 1;
    //Default constructor for the Main Controller
    public MainController(){
    	
    }
    //Function called on initialization of a Main Controller object
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	System.out.println("BeforePath");
    	startMenu.getItems().clear();
    	destMenu.getItems().clear();
	}
    
    private File getDirectoryFromDialog()
    {
    	DirectoryChooser chooser = new DirectoryChooser();
    	chooser.setTitle("JavaFX Projects");
    	File defaultDirectory = new File("c:/");
    	chooser.setInitialDirectory(defaultDirectory);
    	return chooser.showDialog(Main.primaryStage);
    }
    //Action handler for the load map button
    @FXML 
    protected void handleLoadMap(ActionEvent event) {
    	
    	File selectedDirectory = getDirectoryFromDialog(); //Get SuperMap Directory
    	
    	setupDropDowns(selectedDirectory + "\\Rooms.csv"); //Read in list of all rooms on campus
    	
    	for (File file : selectedDirectory.listFiles()) //Find each sub map in supermap and read in nodes
    	{
    		if (file.isDirectory()) //The directory is 
    		{
    			Main.mainMap.addAll(Main.getNodesFromFile(file + "\\mapNodes.csv"));
    		}
    	}
    	
    	for (File file : selectedDirectory.listFiles()) //Find each sub map in supermap and read in edges
    	{
    		if (file.isDirectory()) //The file is a directory i.e. map
    		{
    			Main.connectEdgesFromFile(Main.mainMap, file + "\\mapEdges.csv");
    		}
    	}
    	
    	for (File dir : selectedDirectory.listFiles()) //Draw the super map
    	{
    		if (dir.isDirectory()) //The file is a directory i.e. map
    		{
    			File file = new File(dir + "\\map.png");
    			AngledImage mapImage = new AngledImage(file.toURI().toString(), dir.getName());
				updateImageValuesFromFile(mapImage, dir + "\\scale.csv");
    			if (dir.getName() == "Campus") //If this is the primary map
    			{
    				mapImages.add(0, mapImage); //Add the image to index 0 of the image list
    			}
    			else
    			{
    				mapImages.add(mapImage); //Add the image to the end of the list
    			}
    		}
    	}
    	
    	drawMap(mapImages);   	
    	
    	
//    	String mapPath = selectedDirectory + "\\map.png";
//    	
//    	File file = new File(mapPath);
//    	Image mapImage = new Image(file.toURI().toString());
//        mapView.setImage(mapImage);
//        //readin data
//        double viewHeight = (int) mapView.getBoundsInLocal().getHeight();
//    	double viewWidth = (int) mapView.getBoundsInLocal().getWidth();
//    	scaleX = viewWidth / mapImage.getWidth();
//    	scaleY = viewHeight / mapImage.getHeight();
//    	System.out.println("X = " + scaleX);
//    	System.out.println("Y = " + scaleY);
//        
//        Main.mainMap = Main.readMap(nodePath, edgePath);
//        
//        drawNodeBtns(scaleX, scaleY, 30, Main.mainMap);
        //mapView.setRotate(40);
    }
    
    public void drawMap(List<AngledImage> images)
    {
    	boolean firstRun = true;
    	for (AngledImage image : images)
    	{
			int width = (int) (image.getScaleX() * image.getWidth());
			int height = (int) (image.getScaleY() * image.getHeight());
			if (firstRun)
			{
				scrollAnchorPane.setMaxWidth(width);
				scrollAnchorPane.setPrefWidth(width);
				scrollAnchorPane.setMaxHeight(height);
				scrollAnchorPane.setPrefHeight(height);
				imageCanvas.setWidth(width);
				imageCanvas.setHeight(height);
				firstRun = false;
			}
			GraphicsContext gc = imageCanvas.getGraphicsContext2D();
			Rotate r = new Rotate(image.getAngle(), image.getX(), image.getY());
	        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
			gc.drawImage(image, image.getX(), image.getY(), width, height);
			scrollAnchorPane.
			gc.restore();
    	}
    }
    private void updateImageValuesFromFile(AngledImage img, String path)
    {
		BufferedReader br = null;
		String line = "";
		String delimiter = ",";
		int imageXIndex = 0;
		int imageYIndex = 1;
		int imageAngleIndex = 2;
		int imageScaleXIndex = 3;
		int imageScaleYIndex = 4;
		try {

			br = new BufferedReader(new FileReader(path));
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] imageData = line.split(delimiter);
				int x = Integer.parseInt(imageData[imageXIndex]);
				int y = Integer.parseInt(imageData[imageYIndex]);
				int angle = Integer.parseInt(imageData[imageAngleIndex]);
				double scaleX = Double.parseDouble(imageData[imageScaleXIndex]);
				double scaleY = Double.parseDouble(imageData[imageScaleYIndex]);
				img.setX(x);
				img.setY(y);
				img.setAngle(angle);
				img.setScaleX(scaleX);
				img.setScaleY(scaleY);
			}

		} 
		catch (FileNotFoundException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();} 
		finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {e.printStackTrace();}
			}
		}
    }
    
    //Action Handler for the Run AStar (GO) Button
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
    //Function to generate buttons at each accessible node on the map
    protected void drawNodeBtns(double scaleX, double scaleY, double btnRadius, List<Node> nodeList)
    {
    	for(Node node : nodeList)
    	{
			//Circle c = new Circle(node.xPos * scaleX, node.yPos * scaleY, 10, color);
			//Button btn = new Button("",circle);
    		if (node.map == "Campus")
    		{
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
    }
    
    //Function to draw the Path from Node to Node
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
    //Function to setup the draw down menus for node selection
    private void setupDropDowns(String path)
    {
    	buildingList = RoomReader.getBuildingList(path);
    	startMenu.getItems().clear();
    	destMenu.getItems().clear();
    	for (Building b : buildingList)
    	{
    		if (b.getRooms() != null) //This needs to be here
    		{
    			Menu rooms = new Menu();//Need to use two distinct objects otherwise conflicts occur
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
        			    	startMenu.setText(mi1.getParentMenu().getText() + " " + mi1.getText());
        			    	System.out.println("Start Node Selected");
        			    }
        			});
        			mi2.setOnAction(new EventHandler<ActionEvent>() {
        			    @Override public void handle(ActionEvent e) {
        			        goalNode = Main.findNodeByName(Main.testMap, mi2.getText());
        			    	destMenu.setText(mi1.getParentMenu().getText() + " " + mi1.getText());
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

}