package GUI;
import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.DirectoryChooser;
import AStar.AStar;
import AStar.Node;
import AStar.Settings;
import DataAccess.Building;
import DataAccess.DirectionBuilder;
import DataAccess.Floor;
import DataAccess.Map;
import DataAccess.MapBuilder;

public class MainController implements Initializable{
	//FXML Layout Objects
    @FXML private AnchorPane anchorPane;
    @FXML private Button loadMapBtn;
    @FXML private Button goBtn;
    @FXML private ImageView mapView;
    @FXML private MenuButton startMenu;
    @FXML private MenuButton destMenu;
    @FXML private MenuButton aboutMenu;
    @FXML private ScrollPane imageScrollPane;
    @FXML private Label activeFloorLabel;
    @FXML private Button floorUpBtn;
    @FXML private Button floorDownBtn;
    @FXML private VBox controlVBox;
    @FXML private Button swapButton;
    @FXML private VBox floorControlVBox;
    SimpleStringProperty nextDirectionProperty = new SimpleStringProperty();
    SimpleBooleanProperty getDirectionsProperty = new SimpleBooleanProperty(false);
	private static Settings defaultSettings = new Settings(false, false, false);
	public static Map mainMap;
    //private Group mainGroup = new Group();
    private String lastBuilding;
    //The list of all buildings on Campus
    //Boolean marking a node as selected
    private boolean nodeSelect = false;
    private boolean clickOutofMap = false;
    //The start and end nodes for AStar
    private Node startNode = null;
    private Node goalNode = null;
    private DoubleProperty zoomProperty = new SimpleDoubleProperty(1);
    private double thresh = 2;
    private int eventX = 0;
    private int eventY = 0;
    private Rectangle overlayRect = new Rectangle();
    //private Canvas mainMapOverlay = new Canvas();
    //Default constructor for the Main Controller
    public MainController(){
    	
    }
    //Function called on initialization of a Main Controller object
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	System.out.println("BeforePath");
    	startMenu.getItems().clear();
    	destMenu.getItems().clear();
    	aboutMenu.getItems().clear();
    	//floorSelectionMenu.getItems().clear();
    	loadMap();
    	getDirectionsProperty.addListener(new ChangeListener<Boolean>() {
            @Override public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            	runAStar();
            }});
    	nextDirectionProperty.addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            	System.out.println("String Changed to -" + newValue + "-");
            	for (Building b : mainMap.getBuildingsUnmodifiable())
            	{
            		int i = 0;
            		for (Floor f : b.getFloorsUnmodifiable())
            		{
						if (f.getId().equals(newValue))
    					{
							if (!b.getId().equals(lastBuilding))
							{
	    						setUpGroupOnClick(b,0, 0);
	    						BuildingPopUp.setupPopUp(b);
							}
    						String floorName = b.setActiveFloor(i);
    						activeFloorLabel.setText(floorName);
    						updateFloor(f, b.getFloorsUnmodifiable());
    						lastBuilding = b.getId();
    						break;
    					}
						
						i++;
            		}
            	}
            	//setUpGroupOnClick(Group buildGroup, Building b, int x, int y)
            }});
    	controlVBox.getStyleClass().add("vbox");
    	controlVBox.getChildren().add(BuildingPopUp.getPopUp());
    	controlVBox.getChildren().add(SidePanel.getGridPane());
    	activeFloorLabel.getStyleClass().add("active_floor_label");
    	
    	swapButton.setMaxHeight(startMenu.getBoundsInParent().getHeight() * 2);
    	
    	ImageView swapButtonImage = new ImageView("res/icons/swap.png");
    	//swapButtonImage.setFitHeight(startMenu.getBoundsInParent().getHeight() * 2);
    	swapButtonImage.setFitHeight(44);
    	swapButton.setGraphic(swapButtonImage);
    	floorControlVBox.setVisible(false);
    	//swapButton.setMinHeight(20);
    	zoomSetup();
	}
    
  //Function swaps the ending node with the starting node and triggers a new path to be created
    @FXML 
    protected void swapDirections(ActionEvent event) {
       //swaps the start and goal node
    	if (startNode != null && goalNode != null)
    	{
    		Node tempNode = goalNode;
	       goalNode = startNode;
	       startNode= tempNode;
	       
	       //displays new node information in the appropriate text box
	       startMenu.setText(startNode.getMap() +" " + startNode.getName());
	       destMenu.setText(goalNode.getMap()+" " + goalNode.getName());
	       
	       //runs A* with the new path
	       getDirectionsProperty.set(!getDirectionsProperty.get());
    	}
    }
    
    @Deprecated
    private File getDirectoryFromDialog()
    {
    	DirectoryChooser chooser = new DirectoryChooser();
    	chooser.setTitle("JavaFX Projects");
    	File defaultDirectory = new File("c:/");
    	chooser.setInitialDirectory(defaultDirectory);
    	return chooser.showDialog(Main.getStage());
    }
    //Action handler for the load map button 
    private void loadMap() {
    	MapBuilder mapBuilder = new MapBuilder("res/SuperMap", "Campus");
    	mainMap = mapBuilder.buildMap();
    	drawMap(mainMap);
    	//imageZoomPane = new ZoomingPane(mainMap);
    	imageScrollPane.setContent(mainMap);
    	setupDropDowns();
    	activeFloorLabel.setText(mainMap.getId());
    	//imageZoomPane.setZoomFactor(.8);
    	
    }
	//Method to find the path given a start node and an end node.
	private static List<Node> getPathFromNode(Node startNode, Node endNode, Map map)
	{
		AStar astar = new AStar(defaultSettings);;
		return astar.findPath(startNode, endNode, map.toNodeListUnmodifiable());
	}
    public void drawMap(Map map)
    {
    	boolean firstRun = true;
    	for (Building b : map.getBuildingsUnmodifiable())
    	{
    		if (firstRun)
			{
//    			mainMapOverlay.setWidth(mainMap.getBoundsInLocal().getWidth());
//    			mainMapOverlay.setHeight(mainMap.getBoundsInLocal().getHeight());
//    			mainMapOverlay.setMouseTransparent(true);
//    			mainMap.getChildren().add(mainMapOverlay);
    			overlayRect.setMouseTransparent(true);
    			mainMap.getChildren().add(overlayRect);
    			mainMap.setScale(b.getScale().getX(), b.getScale().getY());
    			mainMap.setRotateAngle(b.getAngle());
    			mainMap.setTranslate(b.getTranslate().getX(), b.getTranslate().getY());
    			lastBuilding = b.getId();
				firstRun = false;
			}
			if (!b.getId().equals(map.getId()))
    		{
    			b.setOpacity(.5);//0 _a
    		}
			//Need to figure out how to deal with the canvas redraw for removing the selection circles.
			b.setOnMouseEntered(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent arg0) {
					if (lastBuilding.equals(mainMap.getId()) && !b.getId().equals(mainMap.getId()))
					{
						Bounds bounds = b.getBoundsInParent();
						overlayRect.setX(bounds.getMinX());
						overlayRect.setY(bounds.getMinY());
						overlayRect.setWidth(bounds.getWidth());
						overlayRect.setHeight(bounds.getHeight());
						overlayRect.setRotate(b.getAngle() - 90);
						overlayRect.setStrokeWidth(15);
						overlayRect.setArcHeight(5);
						overlayRect.setArcWidth(5);
						overlayRect.setFill(new Color(0,0,0,0));
						overlayRect.setOpacity(.8);
						if (b.getId().equals("Salisbury") || b.getId().equals("Boynton Hall"))
						{
							overlayRect.setRotate(b.getAngle());
						}
						overlayRect.setStroke(Color.BLUE);
						overlayRect.setVisible(true);
					}
				}
			});
			b.setOnMouseExited(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent arg0) {
					overlayRect.setVisible(false);
					//mainMapOverlay.getGraphicsContext2D().clearRect(0, 0, mainMapOverlay.getWidth(), mainMapOverlay.getHeight());
				}
			});
			b.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					if (event.isStillSincePress()) //If the mouse is not panning
					{
						if (b.getId().equals(lastBuilding)) // This is the same building
						{
							if(clickOutofMap)
							{
								Building b = mainMap.getBaseBuilding();
								BuildingPopUp.setupPopUp(b);
								setUpGroupOnClick(b, event.getX(), event.getY());
								String floorName = b.setActiveFloor(0);
								activeFloorLabel.setText(floorName);
								updateFloor(b.getBottomFloor(), b.getFloorsUnmodifiable());
								lastBuilding = b.getId();
								clickOutofMap = false;
							}
						}
						else //This is a diffrent building
						{
							if (lastBuilding.equals(mainMap.getId())) //if the last building was the campus
							{
								BuildingPopUp.setupPopUp(b);
								setUpGroupOnClick(b,event.getX(), event.getY());
								String floorName = b.setActiveFloor(0);
								activeFloorLabel.setText(floorName);
								updateFloor(b.getBottomFloor(), b.getFloorsUnmodifiable());
								lastBuilding = b.getId();
							}
							else
							{
								Building b = mainMap.getBaseBuilding();
								BuildingPopUp.setupPopUp(b);
								setUpGroupOnClick(b, event.getX(), event.getY());
								String floorName = b.setActiveFloor(0);
								activeFloorLabel.setText(floorName);
								updateFloor(b.getBottomFloor(), b.getFloorsUnmodifiable());
								lastBuilding = b.getId();
							}
						}
					}
					
				}});
			
			for(Floor f : b.getFloorsUnmodifiable())
			{
				f.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					System.out.println("You Clicked is " + clickOutofMap);
					
					if (b.getId().equals(lastBuilding) && event.isStillSincePress())//Double check that this was triggered after a building was selected
					{
						Floor f = b.getFloorsUnmodifiable().get(b.getActiveFloor());
						int argb = f.getImageView().getImage().getPixelReader().getArgb((int)event.getX(), (int)event.getY());
						int alpha = argb & 0xFF000000;
						if (alpha == 0 && !lastBuilding.equals(mainMap.getId())) //If clicking on the transparent part of the image
						{
							clickOutofMap = true;
						}
						else
						{
							nodeSelect(f, f.getNearestRoom((int)event.getX(), (int)event.getY()));
						}
					}
				}});
			}
    	}
    }
    
    void nodeSelect(Floor f, Node n)
    {
    	if (nodeSelect)
    	{
    		startNode = n;
    		startMenu.setText(f.getId() + " " + n.getName());
    	}
    	else
    	{
    		goalNode = n;
    		destMenu.setText(f.getId() + " " + n.getName());
    	}
    	nodeSelect = !nodeSelect;
	    getDirectionsProperty.set(!getDirectionsProperty.get());
    }
    
    void setUpGroupOnClick(Building building, double x, double y)
    {
		//mainMapOverlay.getGraphicsContext2D().clearRect(0, 0, mainMapOverlay.getWidth(), mainMapOverlay.getHeight());
    	overlayRect.setVisible(false);
    	 if(!lastBuilding.equals(mainMap.getId())){
             zoomProperty.set(1);
         } else {
             zoomProperty.set(4);
             System.out.println("Bazinga");
         }
         
         thresh = zoomProperty.get()*2;
		for (Building b : mainMap.getBuildingsUnmodifiable())
		{
			if (!b.getId().equals(mainMap.getId()))
			{
				b.setOpacity(0);//0 _b
			}
			//n.setPickOnBounds(true);
		}
		building.setOpacity(1);
		//buildGroup.setPickOnBounds(false);
		//imageZoomPane.setMinSize(mainMap.getBoundsInParent().getWidth(), mainMap.getBoundsInParent().getHeight());
		setupFloorSelection(building);
		centerNodeInScrollPane(imageScrollPane, building);
		double minX = building.getBoundsInParent().getMinX();
		double maxX = building.getBoundsInParent().getMaxX();
		double minY = building.getBoundsInParent().getMinY();
		double maxY = building.getBoundsInParent().getMaxY();
		double pivotX = (minX + maxX)/2;
		double pivotY = (minY + maxY)/2;
		if (building.getId().equals(mainMap.getId()))
		{
			pivotX = x;
			pivotY = y;

			File file =  new File("res/SuperMap/_Campus/Campus/map.png");
			Building b = mainMap.getBaseBuilding();
			Floor f = b.getBottomFloor();
			f.setBaseImage(new Image(file.toURI().toString()));
			b.setOpacity(1);
			b.getBottomFloor().getCanvas().setVisible(true);

		}
		else
		{
			File file =  new File("res/SuperMap/_Campus/Campus/Campusempty.png");
			Building b = mainMap.getBaseBuilding();
			Floor f = b.getBottomFloor();
			f.setBaseImage(new Image(file.toURI().toString()));
			b.setOpacity(1);
			b.getBottomFloor().getCanvas().setVisible(false);
			
		}
		mainMap.getRotation().setPivotX(pivotX);
		mainMap.getRotation().setPivotY(pivotY);
		mainMap.setRotateAngle(- building.getAngle());

		double zoom = imageScrollPane.getWidth() / building.getBoundsInParent().getWidth();
		if (building.getId().equals(mainMap.getId()))
		{
			mainMap.setZoom(1,pivotX,pivotY);
		}
		else
		{
			mainMap.setZoom(zoom * (.5 + building.getScale().getX()),pivotX,pivotY);
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
    private void setupFloorSelection(Building b)
    {
    	floorUpBtn.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent e) {
		    	String floorName = b.setActiveFloor(b.getActiveFloor() + 1);
				activeFloorLabel.setText(floorName);
		    	updateFloor(b.getFloorsUnmodifiable().get(b.getActiveFloor()), b.getFloorsUnmodifiable());
		    }});
    	floorDownBtn.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent e) {
		    	String floorName = b.setActiveFloor(b.getActiveFloor() - 1);
				activeFloorLabel.setText(floorName);
		    	updateFloor(b.getFloorsUnmodifiable().get(b.getActiveFloor()), b.getFloorsUnmodifiable());
		    }});
    }
    private void updateFloor(Floor floor, List<Floor> floors)
    {
    	setNodesVisible(floors, false);
    	floor.setVisible(true);
    	addImagesToFloor(floor);
    	floorControlVBox.setVisible(!floor.getParent().getId().equals(mainMap.getId()));
    	//floorSelectionMenu.setText(text);
    }
    private void setNodesVisible(List<Floor> floors, boolean isVisible)
    {
    	for (Floor n : floors)
    	{
    		n.setVisible(isVisible);
    	}
    }
    private void addImagesToFloor(Floor f)
	{
		Canvas c = f.getCanvas();
		int width = (int) (c.getWidth() / 18);
		if (f.getId().equals(mainMap.getId()))
		{
			return;
		}
		int offset = width / 2;
		
		for (Node n : f.getNodes())
		{
			int x = n.getX() - offset;
			int y = n.getY() - offset;
			//ROOM, STAIRS, ELEVATOR, BATHROOM_M, BATHROOM_F, ENTRANCE, INTERSECTION, ENDHALL, NONE
			Node.Type type = n.getType();

			if (type == Node.Type.BATHROOM_F)
			{
				Image i = new Image("/res/locations/Women'sBathroom.png");
				c.getGraphicsContext2D().drawImage(i, x , y, width, width);
			}
			else if (type == Node.Type.BATHROOM_M)
			{
				Image i = new Image("/res/locations/Men'sBathroom.png");
				c.getGraphicsContext2D().drawImage(i, x, y, width, width);
			}
			else if (type == Node.Type.ELEVATOR)
			{
				Image i = new Image("/res/locations/Elevator.png");
				c.getGraphicsContext2D().drawImage(i, x, y, width, width);
			}
			else if (type == Node.Type.STAIRS)
			{
				Image i = new Image("/res/locations/Stairs.png");
				c.getGraphicsContext2D().drawImage(i, x, y, width, width);
			}
			else if (type == Node.Type.ENTRANCE)
			{
				Image i = new Image("/res/locations/Door.png");
				c.getGraphicsContext2D().drawImage(i, x, y, width, width);
			}
		}
	}
    //Action handler for the zooming in of the map
    @FXML 
    protected void handleZoomIn(ActionEvent event) {
    	//imageZoomPane.setZoomFactor(.8);
//    	double value = imageZoomPane.getZoomFactor() + 1;
//    	if (value > 8)
//    	{
//    		value = 8;
//    	}
//    	imageZoomPane.setPivot(imageScrollPane.getHvalue() * imageZoomPane.getWidth(), imageScrollPane.getVvalue() * imageZoomPane.getHeight());
//    	imageZoomPane.setZoomFactor(value);
    }
    //Action handler for the zooming out of the map
    @FXML 
    protected void handleZoomOut(ActionEvent event) {
    	//double value = imageZoomPane.getZoomFactor() - 1;
//    	if (value < 1)
//    	{
//    		value = 1;
//    	}
    	//imageZoomPane.setZoomFactor(value);
    }
    
    private void runAStar() {
    	System.out.println("Nodes");
    	System.out.println(startNode);
    	System.out.println(goalNode);
    	if (startNode != null && goalNode != null)
    	{
    		List<Node> path = getPathFromNode(startNode, goalNode, mainMap);
    		drawPath(path);
    		System.out.println("The Path is");
    		System.out.println(path);
            List<String> directions = DirectionBuilder.getDirectionsList(path, 1, 1);
            showDirections(directions);
            if (!path.isEmpty())
            {
            	nextDirectionProperty.set(path.get(0).getMap());
            }
//    		startNode = null;
//    		goalNode = null;
    	}
    }
    private void showDirections(List<String> directions)
    {
    	SidePanel.setUpSidePanel(startNode.getName(),goalNode.getName() , directions, BuildingPopUp.getPopUp(), nextDirectionProperty);
    	controlVBox.setMaxHeight(imageScrollPane.getHeight());
    	controlVBox.setPrefHeight(VBox.USE_COMPUTED_SIZE);
    }
   
    
    //Function to draw the Path from Node to Node
    protected void drawPath(List<Node> path)
    {
    	System.out.println(path);
    	if (path.size() > 0)
    	{
    		Canvas activeCanvas = findMapCanvas(path.get(0).getMap());
    		System.out.println(path.get(0).getMap());
        	clearAllCanvas();
    		
        	boolean first = true;
        	Node prevNode = null;
        	System.out.println("CanvasEditing");
        	for(Node node : path)
        	{
        		if (node.isTransition())
        		{
        			System.out.println("TNode " + node.getName());
        			System.out.println("Map " + node.getMap());
        		}
        		if (first)
        		{
        			first = false;
        			//do nothing
        		}
        		else if (node.isTransition() && prevNode.isTransition() && !node.getMap().equals(prevNode.getMap()))
        		{
        			System.out.println("CHANGING CANVAS");
        			System.out.println("Map name is : " + node.getMap());
        			activeCanvas = findMapCanvas(node.getMap());
        	    	activeCanvas.getGraphicsContext2D().clearRect(0, 0, activeCanvas.getWidth(), activeCanvas.getHeight());
        		}
        		else
        		{
        			GraphicsContext gc = activeCanvas.getGraphicsContext2D();
        			if (node.getMap().equals(mainMap.getId()))
        			{
        				gc.setLineWidth(4);
        			}
        			else
        			{
        				gc.setLineWidth(8);
        			}
        			gc.setStroke(Color.RED);
        			gc.strokeLine(prevNode.getX()+5, prevNode.getY()+5, node.getX()+5, node.getY()+5);        		}
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
    
    private void drawCircleOnNode(String map, int xPos, int yPos, int radius, Paint p)
    {
    	Canvas activeCanvas = findMapCanvas(map);
    	GraphicsContext gc = activeCanvas.getGraphicsContext2D();
    	gc.setFill(p);
    	gc.fillOval(xPos - (radius /2)+5, yPos - (radius / 2)+5, radius, radius);
    }
    
    private void drawCircleOnNode(Node n, int radius, Paint p)
    {
    	drawCircleOnNode(n.getMap(), n.getX(), n.getY(), radius, p);
    }
    
    private void clearAllCanvas()
    {
    	for (Building b : mainMap.getBuildingsUnmodifiable())
    	{
    		for (Floor f : b.getFloorsUnmodifiable())
    		{
				Canvas c = f.getCanvas();
    			GraphicsContext gc = c.getGraphicsContext2D();
    			gc.clearRect(0, 0, c.getWidth(), c.getHeight());
    		}
       	}
    }
    private Canvas findMapCanvas(String map)
    {
    	for (Building b : mainMap.getBuildingsUnmodifiable())
    	{
    		for (Floor f : b.getFloorsUnmodifiable())
    		{
    			if(f.getId().equals(map))
        		{
        			return f.getCanvas();
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
    	for (Building b : mainMap.getBuildingsUnmodifiable())
    	{
    		if (b.getFloorsUnmodifiable() != null) //This needs to be here
    		{
    			Menu building = new Menu();//Need to use two distinct objects otherwise conflicts occur
        		Menu building2 = new Menu();
        		building.setText(b.getId());
        		building2.setText(b.getId());
    			for (Floor f : b.getFloorsUnmodifiable())
        		{
        			Menu floors = new Menu();
        			Menu floors2 = new Menu();
        			floors.setText(f.getId());
        			floors2.setText(f.getId());
        			for (Node n : f.getNodes())
        			{
        				if ((n.getType() == Node.Type.ROOM || n.getType() == Node.Type.ENTRANCE) && !n.getName().equals("node"))
        				{
        					MenuItem mi1 = new MenuItem(n.getName());
            				MenuItem mi2 = new MenuItem(n.getName());
            				mi1.setOnAction(new EventHandler<ActionEvent>() {
                			    @Override public void handle(ActionEvent e) {
                			    	startNode = mainMap.findNodeByXYZinMap(n.getX(), n.getY(), n.getZ(), n.getMap());
                			    	startMenu.setText(mi1.getParentMenu().getText() + " " + mi1.getText());
                			    	getDirectionsProperty.set(!getDirectionsProperty.get());
                			    	System.out.println("Start Node Selected");
                			    }
                			});
                			mi2.setOnAction(new EventHandler<ActionEvent>() {
                			    @Override public void handle(ActionEvent e) {
                			        goalNode = mainMap.findNodeByXYZinMap(n.getX(), n.getY(), n.getZ(), n.getMap());
                			    	destMenu.setText(mi1.getParentMenu().getText() + " " + mi1.getText());
                			    	getDirectionsProperty.set(!getDirectionsProperty.get());
                			        System.out.println("Goal Node Selected");
                			    }
                			});
                			addMenuItemsSorted(floors.getItems(), mi1);
                			addMenuItemsSorted(floors2.getItems(), mi2);
        				}
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
    private void addMenuItemsSorted(List<MenuItem> menuItems, MenuItem m)
    {
    	for (int i = 0; i <= menuItems.size(); i++)
    	{
    		if (i >= menuItems.size())
    		{
    			menuItems.add(m);
    			break;
    		}
    		else if (m.getText().compareToIgnoreCase(menuItems.get(i).getText()) < 0)
    		{
    			menuItems.add(i, m);
    			break;
    		}
    	}
    }
    
    private void zoomSetup()
    {
    	mainMap.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                System.out.println("Zoom is: "+zoomProperty.get());

                System.out.println(event.getDeltaY());
                
                System.out.println(thresh*2);
                
                if (event.getDeltaY() > 0 &&zoomProperty.get()<thresh*2) {
                
                    System.out.println("Zoom is: "+zoomProperty.get());
                    zoomProperty.set(zoomProperty.get()*1.1);
                    System.out.println(event.getX()+" "+event.getY());
                    eventX = (int)event.getX();
                    eventY = (int)event.getY();

                } else if (event.getDeltaY() < 0 && zoomProperty.get()>thresh/2) {
                    System.out.println("Zoom is: "+zoomProperty.get());
                    zoomProperty.set(zoomProperty.get()/1.1);
                    System.out.println(event.getX()+" "+event.getY());
                    eventX = (int)event.getX();
                    eventY = (int)event.getY();
                }
                else {
                    System.out.println("Consuming");
                    event.consume();
                }
                } 
            
        });


        zoomProperty.addListener(new InvalidationListener() {

            @Override
            public void invalidated(Observable arg0) {
                // TODO Auto-generated method stub

                System.out.println(eventX +" "+eventY);
                
                mainMap.setZoom(zoomProperty.get(),eventX,eventY);
                System.out.println("***********************");
                System.out.println(zoomProperty.get());
                System.out.println("***********************");


            }
        });
    }

}