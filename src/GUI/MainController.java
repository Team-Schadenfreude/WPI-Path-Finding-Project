package GUI;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.stage.DirectoryChooser;
import AStar.Node;
import DataAccess.Building;
import DataAccess.Floor;
import DataAccess.Room;
//import DataAccess.RoomReader;
import GUI.ZoomingPane;

public class MainController implements Initializable{
	//FXML Layout Objects
    @FXML private AnchorPane anchorPane;
    @FXML private Button loadMapBtn;
    @FXML private Button goBtn;
    @FXML private ImageView mapView;
    @FXML private MenuButton startMenu;
    @FXML private MenuButton destMenu;
    @FXML private ScrollPane imageScrollPane;
    @FXML private MenuButton floorSelectionMenu;
    private Group mainGroup = new Group();
    //Scale s = new Scale(2,2);
    private StackPane imageStackPane = new StackPane();
    private ZoomingPane imageZoomPane;
    //The list of all buildings on Campus
    List<Building> buildingList = new LinkedList<Building>();
    //Boolean marking a node as selected
    private boolean nodeSelect = false;
    //The start and end nodes for AStar
    Node startNode = null;
    Node goalNode = null;
    //Default constructor for the Main Controller
    public MainController(){
    	
    }
    //Function called on initialization of a Main Controller object
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	System.out.println("BeforePath");
    	startMenu.getItems().clear();
    	destMenu.getItems().clear();
    	floorSelectionMenu.getItems().clear();
    	//imageCanvas.getTransforms().add(s);
    	//imageScrollPane.setContent(scrollAnchorPane);
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
    	//setupDropDowns(selectedDirectory + "\\Rooms.csv"); //Read in list of all rooms on campus
    	for (File dir : selectedDirectory.listFiles()) //Draw the super map
    	{
    		if (dir.isDirectory() && dir.getName().charAt(0) == '_') //The file is a directory and a building
    		{
    			Building b = new Building(dir.getName().substring(1));
    			updateBuildingValuesFromFile(b, dir + "\\scale.csv");
    			for (File subDir : dir.listFiles())
    			{
    				if (subDir.isDirectory()) //The file is a directory and a floor plan
    				{
    					File file = new File(subDir + "\\map.png");
    					Floor floor = new Floor(file.toURI().toString(), subDir.getName());
    					List<Node> floorNodes = Main.getNodesFromFile(subDir + "\\mapNodes.csv");
    					Main.mainMap.addAll(floorNodes);
    					for (Node n : floorNodes)
    					{
    						if (!n.nodeName.equals("node"))
    						{
    							Room r = new Room(n.nodeName, n);
    							floor.getRoomList().add(r);
    						}
    					}
    					b.getFloors().add(floor);
    				}
    			}
    			if (b.getName().equals("Campus") && buildingList.size() > 0)
    			{
    				buildingList.add(0,b);
    			}
    			else
    			{
    				buildingList.add(b);
    			}
    			System.out.println("Past add");
    		}
    	}
    	System.out.println("Done");
    	for (File dir : selectedDirectory.listFiles()) //Draw the super map
    	{
    		if (dir.isDirectory() && dir.getName().charAt(0) == '_') //The file is a directory and a building
    		{
    			for (File subDir : dir.listFiles())
    			{
    				if (subDir.isDirectory()) //The file is a directory and a floor plan
    				{
    					Main.connectEdgesFromFile(Main.mainMap, subDir + "\\mapEdges.csv");
    				}
    			}
    		}
    	}
    	System.out.println(buildingList);
    	startNode = Main.mainMap.get(0);
    	goalNode = Main.mainMap.get(4);
    	
    	drawMap(buildingList);
    	imageZoomPane = new ZoomingPane(mainGroup);
    	imageScrollPane.setContent(imageZoomPane);
    	setupDropDowns();
    	//Canvas tmp = (Canvas) ((Group)mainGroup.getChildren().get(1)).getChildren().get(1);
    	//tmp.getGraphicsContext2D().rect(20, 20, 600, 600);
    }
    
    public void drawMap(List<Building> buildings)
    {
    	boolean firstRun = true;
    	for (Building b : buildings)
    	{
    		System.out.println("Building " + b.getName());
    		Group buildGroup = new Group();
    		if (firstRun)
			{
				mainGroup.getTransforms().add(new Rotate(b.getAngle()));
				firstRun = false;
			}
    		for (Floor f : b.getFloors())
    		{
    			Group g = new Group();
    			Canvas c = new Canvas(f.getImage().getWidth(), f.getImage().getHeight());
    			ImageView im = new ImageView(f.getImage());
    			g.getChildren().add(im);
    			g.getChildren().add(c);
    			g.setId(f.getName());
    			buildGroup.getChildren().add(g);
    		}
    		Rotate r = new Rotate(b.getAngle());
			buildGroup.getTransforms().add(r);
			buildGroup.getTransforms().add(new Scale(b.getScaleX(), b.getScaleY()));
			buildGroup.setTranslateX(b.getX());
			buildGroup.setTranslateY(b.getY());
			System.out.println("MinX " + buildGroup.getBoundsInParent().getMinX());
			System.out.println("MinY " + buildGroup.getBoundsInParent().getMinY());
			System.out.println("MaxX " + buildGroup.getBoundsInParent().getMaxX());
			System.out.println("MaxY " + buildGroup.getBoundsInParent().getMaxY());
			buildGroup.setId(b.getName());
			if (!buildGroup.getId().equals("Campus"))
    		{
    			buildGroup.setOpacity(0);
    		}
			buildGroup.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					//mainGroup.setRotate(- image.getAngle());
					for (javafx.scene.Node n : mainGroup.getChildren())
					{
						if (!n.getId().equals("Campus"))
						{
							n.setOpacity(0);
						}
					}
					buildGroup.setOpacity(1);
					imageZoomPane.setMinSize(mainGroup.getBoundsInParent().getWidth(), mainGroup.getBoundsInParent().getHeight());
					setupFloorSelection(buildGroup);
					centerNodeInScrollPane(imageScrollPane, buildGroup);
					double minX =buildGroup.getBoundsInParent().getMinX();
					double maxX = buildGroup.getBoundsInParent().getMaxX();
					double minY = buildGroup.getBoundsInParent().getMinY();
					double maxY = buildGroup.getBoundsInParent().getMaxY();
					double pivotX = (minX + maxX)/2;
					double pivotY = (minY + maxY)/2;
					((Rotate) mainGroup.getTransforms().get(0)).setPivotX(pivotX);
					((Rotate) mainGroup.getTransforms().get(0)).setPivotY(pivotY);
					((Rotate) mainGroup.getTransforms().get(0)).setAngle(- b.getAngle());
					imageZoomPane.setPivot(pivotX, pivotY);
					double zoom = imageScrollPane.getWidth() / buildGroup.getBoundsInParent().getWidth();
					imageZoomPane.setZoomFactor(zoom * .5);
				}});
			
			mainGroup.getChildren().add(buildGroup);
			
    	}
    }
    
    public void centerNodeInScrollPane(ScrollPane scrollPane, javafx.scene.Node node) {
        double h = scrollPane.getContent().getBoundsInLocal().getHeight();
        double y = (node.getBoundsInParent().getMaxY() + 
                    node.getBoundsInParent().getMinY()) / 2.0;
        double v = scrollPane.getViewportBounds().getHeight();
        scrollPane.setVvalue(scrollPane.getVmax() * ((y - 0.5 * v) / (h - v)));
        double w = scrollPane.getContent().getBoundsInLocal().getWidth();
        double x = (node.getBoundsInParent().getMaxX() + 
                    node.getBoundsInParent().getMinX()) / 2.0;
        double hor = scrollPane.getViewportBounds().getWidth();
        scrollPane.setHvalue(scrollPane.getHmax() * ((x - 0.5 * hor) / (w - hor)));
    }
    private void setupFloorSelection(Group g)
    {
    	floorSelectionMenu.getItems().clear();
    	for(javafx.scene.Node f : g.getChildren())
    	{
    		MenuItem mi = new MenuItem(f.getId());
    		mi.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			    	setNodesVisible(g.getChildren(), 0);
			    	f.setOpacity(1);
			    	floorSelectionMenu.setText(mi.getText());
			    }
			});
    		floorSelectionMenu.getItems().add(mi);
    	}
    }
    private void setNodesVisible(List<javafx.scene.Node> nodes, double oppacity)
    {
    	for (javafx.scene.Node n : nodes)
    	{
    		n.setOpacity(oppacity);
    	}
    }
  //Action handler for the zooming in of the map
    @FXML 
    protected void handleZoomIn(ActionEvent event) {
    	double value = imageZoomPane.getZoomFactor() + 1;
    	if (value > 8)
    	{
    		value = 8;
    	}
    	imageZoomPane.setPivot(imageScrollPane.getHvalue() * imageZoomPane.getWidth(), imageScrollPane.getVvalue() * imageZoomPane.getHeight());
    	imageZoomPane.setZoomFactor(value);
    }
    //Action handler for the zooming out of the map
    @FXML 
    protected void handleZoomOut(ActionEvent event) {
    	double value = imageZoomPane.getZoomFactor() - 1;
    	if (value < 1)
    	{
    		value = 1;
    	}
    	imageZoomPane.setZoomFactor(value);
    }
    private void updateBuildingValuesFromFile(Building b, String path)
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
				b.setX(x);
				b.setY(y);
				b.setAngle(angle);
				b.setScaleX(scaleX);
				b.setScaleY(scaleY);
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
    		List<Node> path = Main.getPathFromNode(startNode, goalNode, Main.mainMap);
    		drawPath(path);
    		System.out.println("The Path is");
    		System.out.println(path);
//    		startNode = null;
//    		goalNode = null;
    	}
    }
    //Function to generate buttons at each accessible node on the map
    protected void drawNodeBtns(double scaleX, double scaleY, double btnRadius, List<Node> nodeList)
    {
    	System.out.println("Shouldn't be here");
    	for(Node node : nodeList)
    	{
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
    			    		startNode = Main.findNodeByName(Main.mainMap, btn.getId());
    			    		nodeSelect = true;
    			    	
    			    	}
    			    	else
    			    	{
    			    		goalNode = Main.findNodeByName(Main.mainMap, btn.getId());
    			    		nodeSelect = false;
    			    	}
    			    }
    			});
    			anchorPane.getChildren().add(btn);
    		}
			
    	}
    }
    
    //Function to draw the Path from Node to Node
    protected void drawPath(List<Node> path)
    {
    	System.out.println(path);
    	if (path.size() > 0)
    	{
    		Canvas activeCanvas = findMapCanvas(path.get(0).map);
    		System.out.println(path.get(0).map);
        	clearAllCanvas();
    		drawCircleOnNode(new Node("h",(int)activeCanvas.getWidth(), (int)activeCanvas.getHeight(),0, path.get(0).map), 100, Color.GREEN);

        	boolean first = true;
        	Node prevNode = null;
        	for(Node node : path)
        	{
        		if (first)
        		{
        			first = false;
        			//do nothing
        		}
        		else if (node.isTransitionNode && prevNode.isTransitionNode)
        		{
        			System.out.println("Map name is : " + node.map);
        			activeCanvas = findMapCanvas(node.map);
        	    	activeCanvas.getGraphicsContext2D().clearRect(0, 0, activeCanvas.getWidth(), activeCanvas.getHeight());
        		}
        		else
        		{
        			GraphicsContext gc = activeCanvas.getGraphicsContext2D();
        			gc.setLineWidth(4);
        			gc.setStroke(Color.RED);
        			gc.strokeLine(prevNode.xPos, prevNode.yPos, node.xPos, node.yPos);
        			System.out.println(node);
        		}
        		prevNode = node;
        	}
        	
        	drawCircleOnNode(path.get(0), 20, Color.GREEN);
        	drawCircleOnNode(path.get(path.size() - 1), 20, Color.BLUE);
    	}
    	else
    	{
    		System.out.println("No Path");
    	}
    }
    private void drawCircleOnNode(Node n, int radius, Paint p)
    {
    	Canvas activeCanvas = findMapCanvas(n.map);
    	GraphicsContext gc = activeCanvas.getGraphicsContext2D();
    	gc.setFill(p);
    	gc.fillOval(n.xPos - (radius /2), n.yPos - (radius / 2), radius, radius);
    }
    private void clearAllCanvas()
    {
    	for (javafx.scene.Node g : mainGroup.getChildren())
    	{
    		for (javafx.scene.Node subG : ((Group)g).getChildren())
    		{
				Canvas c = ((Canvas) ((Group) subG).getChildren().get(1));
    			GraphicsContext gc = c.getGraphicsContext2D();
    			gc.clearRect(0, 0, c.getWidth(), c.getHeight());
    		}
       	}
    }
    private Canvas findMapCanvas(String map)
    {
    	for (javafx.scene.Node g : mainGroup.getChildren())
    	{
    		for (javafx.scene.Node subG : ((Group)g).getChildren())
    		{
    			if(subG.getId().equals(map))
        		{
        			return (Canvas) ((Group) subG).getChildren().get(1);
        		}
    		}
       	}
		return null;
    }
    //Function to setup the draw down menus for node selection
    private void setupDropDowns()
    {
    	startMenu.getItems().clear();
    	destMenu.getItems().clear();
    	for (Building b : buildingList)
    	{
    		if (b.getFloors() != null) //This needs to be here
    		{
    			Menu building = new Menu();//Need to use two distinct objects otherwise conflicts occur
        		Menu building2 = new Menu();
        		building.setText(b.getName());
        		building2.setText(b.getName());
    			for (Floor f : b.getFloors())
        		{
        			Menu floors = new Menu();
        			Menu floors2 = new Menu();
        			floors.setText(f.getName());
        			floors2.setText(f.getName());
        			for (Room r : f.getRoomList())
        			{
        				MenuItem mi1 = new MenuItem(r.getName());
        				MenuItem mi2 = new MenuItem(r.getName());
        				mi1.setOnAction(new EventHandler<ActionEvent>() {
            			    @Override public void handle(ActionEvent e) {
            			    	startNode = Main.findNodeByName(Main.mainMap, mi1.getText());
            			    	startMenu.setText(mi1.getParentMenu().getText() + " " + mi1.getText());
            			    	System.out.println("Start Node Selected");
            			    }
            			});
            			mi2.setOnAction(new EventHandler<ActionEvent>() {
            			    @Override public void handle(ActionEvent e) {
            			        goalNode = Main.findNodeByName(Main.mainMap, mi2.getText());
            			    	destMenu.setText(mi1.getParentMenu().getText() + " " + mi1.getText());
            			        System.out.println("Goal Node Selected");
            			    }
            			});
            			floors.getItems().add(mi1);
            			floors2.getItems().add(mi2);
        			}
        			building.getItems().add(floors);
        			building2.getItems().add(floors2);
        		}
    			System.out.println("Building--");
    			System.out.println(building);
        		startMenu.getItems().add(building);
        		destMenu.getItems().add(building2);
    		}
    		
    	}
    	
    }

}