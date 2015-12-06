package GUI;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.stage.DirectoryChooser;
import AStar.AStar;
import AStar.Node;
import AStar.Settings;
import DataAccess.Building;
import DataAccess.DirectionBuilder;
import DataAccess.Floor;
import DataAccess.Map;
import DataAccess.MapBuilder;
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
    @FXML private SplitPane primarySplitPane;
    SimpleBooleanProperty getDirectionsProperty = new SimpleBooleanProperty(false);
	private static Settings defaultSettings = new Settings(false, false, false);
	public static Map mainMap = new Map();
    private Group mainGroup = new Group();
    private String lastBuilding;
    //Scale s = new Scale(2,2);
    private ZoomingPane imageZoomPane;
    //The list of all buildings on Campus
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
    	primarySplitPane.setDividerPositions(1.0);
    	loadMap();
    	getDirectionsProperty.addListener(new ChangeListener<Boolean>() {
            @Override public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            	runAStar();
            }});
	}
    @Deprecated
    private File getDirectoryFromDialog()
    {
    	DirectoryChooser chooser = new DirectoryChooser();
    	chooser.setTitle("JavaFX Projects");
    	File defaultDirectory = new File("c:/");
    	chooser.setInitialDirectory(defaultDirectory);
    	return chooser.showDialog(Main.primaryStage);
    }
    //Action handler for the load map button 
    private void loadMap() {
    	MapBuilder mapBuilder = new MapBuilder("res/SuperMap", "Campus");
    	mainMap = mapBuilder.buildMap();
    	drawMap(mainMap.getBuildings());
    	imageZoomPane = new ZoomingPane(mainGroup);
    	imageScrollPane.setContent(imageZoomPane);
    	setupDropDowns();
    }
	//Method to find the path given a start node and an end node.
	public static List<Node> getPathFromNode(Node startNode, Node endNode, Map map)
	{
		AStar astar = new AStar(defaultSettings);;
		return astar.findPath(startNode, endNode, map.toNodeList());
	}
    public void drawMap(List<Building> buildings)
    {
    	boolean firstRun = true;
    	for (Building b : buildings)
    	{
    		Group buildGroup = new Group();
    		if (firstRun)
			{
				mainGroup.getTransforms().add(new Rotate(b.getAngle()));
				lastBuilding = b.getName();
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
    			System.out.println("-" + g.getId() + "-");
    			buildGroup.getChildren().add(g);
    		}
    		Rotate r = new Rotate(b.getAngle());
			buildGroup.getTransforms().add(r);
			buildGroup.getTransforms().add(new Scale(b.getScaleX(), b.getScaleY()));
			buildGroup.setTranslateX(b.getX());
			buildGroup.setTranslateY(b.getY());
			buildGroup.setId(b.getName());
			if (!buildGroup.getId().equals(mainMap.getBaseMapName()))
    		{
    			buildGroup.setOpacity(0);//0 _a
    		}
			buildGroup.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					//mainGroup.setRotate(- image.getAngle());
					if (event.isStillSincePress() && !buildGroup.getId().equals(lastBuilding))
					{
						for (javafx.scene.Node n : mainGroup.getChildren())
						{
							if (!n.getId().equals(mainMap.getBaseMapName()))
							{
								n.setOpacity(0);//0 _b
							}
						}
						buildGroup.setOpacity(1);
						imageZoomPane.setMinSize(mainGroup.getBoundsInParent().getWidth(), mainGroup.getBoundsInParent().getHeight());
						setupFloorSelection(buildGroup);
						centerNodeInScrollPane(imageScrollPane, buildGroup);
						double minX = buildGroup.getBoundsInParent().getMinX();
						double maxX = buildGroup.getBoundsInParent().getMaxX();
						double minY = buildGroup.getBoundsInParent().getMinY();
						double maxY = buildGroup.getBoundsInParent().getMaxY();
						double pivotX = (minX + maxX)/2;
						double pivotY = (minY + maxY)/2;
						if (buildGroup.getId().equals(mainMap.getBaseMapName()))
						{
							System.out.println("Pivot");
							pivotX = event.getX();
							System.out.println(pivotX);
							pivotY = event.getY();
							System.out.println(pivotY);
						}
						((Rotate) mainGroup.getTransforms().get(0)).setPivotX(pivotX);
						((Rotate) mainGroup.getTransforms().get(0)).setPivotY(pivotY);
						((Rotate) mainGroup.getTransforms().get(0)).setAngle(- b.getAngle());
						Point2D pt = ((Rotate) mainGroup.getTransforms().get(0)).transform(pivotX, pivotY);
						if (buildGroup.getId().equals(mainMap.getBaseMapName()))
						{
							imageZoomPane.setPivot(pt.getX(), pt.getY());
						}
						else
						{
							imageZoomPane.setPivot(pivotX, pivotY);
						}
						double zoom = imageScrollPane.getWidth() / buildGroup.getBoundsInParent().getWidth();
						if (buildGroup.getId().equals(mainMap.getBaseMapName()))
						{
							imageZoomPane.setZoomFactor(1);
						}
						else
						{
							imageZoomPane.setZoomFactor(zoom * .5);
						}
						
						lastBuilding = buildGroup.getId();
					}
					
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
    	imageZoomPane.setZoomFactor(1);
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
    	double value = imageZoomPane.getZoomFactor() - 1;
    	if (value < 1)
    	{
    		value = 1;
    	}
    	imageZoomPane.setZoomFactor(value);
    }
    
    private void runAStar() {
    	if (startNode != null && goalNode != null)
    	{
    		List<Node> path = getPathFromNode(startNode, goalNode, mainMap);
    		drawPath(path);
    		System.out.println("The Path is");
    		System.out.println(path);
            List<String> directions = DirectionBuilder.getDirectionsList(path, 1, 1);
            showDirections(directions);
//    		startNode = null;
//    		goalNode = null;
    	}
    }
    private void showDirections(List<String> directions)
    {
    	GridPane grid = SidePanel.setUpSidePanel("FL", "AK", directions);
    	Button closeBtn = new Button("Close");
    	closeBtn.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	((GridPane)primarySplitPane.getItems().get(1)).getChildren().clear();
		    	//primarySplitPane.getItems().add(1, new GridPane());
		    	primarySplitPane.setDividerPositions(1.0);}});
    	grid.add(closeBtn, 0, 13);
        primarySplitPane.getItems().set(1, grid);
        primarySplitPane.setDividerPositions(0.66);
    }
    //Function to generate buttons at each accessible node on the map
    protected void drawNodeBtns(double scaleX, double scaleY, double btnRadius, List<Node> nodeList)
    {
    	for(Node node : nodeList)
    	{
    		if (node.map == mainMap.getBaseMapName())
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
    			    		startNode = mainMap.findNodeByName(btn.getId());
    			    		nodeSelect = true;
    			    	
    			    	}
    			    	else
    			    	{
    			    		goalNode = mainMap.findNodeByName(btn.getId());
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
    		drawCircleOnNode(path.get(0).map,(int)activeCanvas.getWidth(), (int)activeCanvas.getHeight(), 100, Color.GREEN);
    		
        	boolean first = true;
        	Node prevNode = null;
        	System.out.println("CanvasEditing");
        	for(Node node : path)
        	{
        		if (node.isTransitionNode)
        		{
        			System.out.println("TNode " + node.nodeName);
        			System.out.println("Map " + node.map);
        		}
        		if (first)
        		{
        			first = false;
        			//do nothing
        		}
        		else if (node.isTransitionNode && prevNode.isTransitionNode && !node.map.equals(prevNode.map))
        		{
        			System.out.println("CHANGING CANVAS");
        			System.out.println("Map name is : " + node.map);
        			activeCanvas = findMapCanvas(node.map);
        	    	activeCanvas.getGraphicsContext2D().clearRect(0, 0, activeCanvas.getWidth(), activeCanvas.getHeight());
        		}
        		else
        		{
        			GraphicsContext gc = activeCanvas.getGraphicsContext2D();
        			if (node.map.equals(mainMap.getBaseMapName()))
        			{
        				gc.setLineWidth(4);
        			}
        			else
        			{
        				gc.setLineWidth(8);
        			}
        			gc.setStroke(Color.RED);
        			gc.strokeLine(prevNode.xPos, prevNode.yPos, node.xPos, node.yPos);
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
    
    private void drawCircleOnNode(String map, int xPos, int yPos, int radius, Paint p)
    {
    	Canvas activeCanvas = findMapCanvas(map);
    	GraphicsContext gc = activeCanvas.getGraphicsContext2D();
    	gc.setFill(p);
    	gc.fillOval(xPos - (radius /2), yPos - (radius / 2), radius, radius);
    }
    
    private void drawCircleOnNode(Node n, int radius, Paint p)
    {
    	drawCircleOnNode(n.map, n.xPos, n.yPos, radius, p);
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
    	for (Building b : mainMap.getBuildings())
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
        			for (Node n : f.getNodes())
        			{
        				if (n.type == Node.Type.ROOM || n.type == Node.Type.ENTRANCE)
        				{
        					MenuItem mi1 = new MenuItem(n.nodeName);
            				MenuItem mi2 = new MenuItem(n.nodeName);
            				mi1.setOnAction(new EventHandler<ActionEvent>() {
                			    @Override public void handle(ActionEvent e) {
                			    	startNode = mainMap.findNodeByName(mi1.getText());
                			    	startMenu.setText(mi1.getParentMenu().getText() + " " + mi1.getText());
                			    	getDirectionsProperty.set(!getDirectionsProperty.get());
                			    	System.out.println("Start Node Selected");
                			    }
                			});
                			mi2.setOnAction(new EventHandler<ActionEvent>() {
                			    @Override public void handle(ActionEvent e) {
                			        goalNode = mainMap.findNodeByName(mi2.getText());
                			    	destMenu.setText(mi1.getParentMenu().getText() + " " + mi1.getText());
                			    	getDirectionsProperty.set(!getDirectionsProperty.get());
                			        System.out.println("Goal Node Selected");
                			    }
                			});
                			floors.getItems().add(mi1);
                			floors2.getItems().add(mi2);
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

}