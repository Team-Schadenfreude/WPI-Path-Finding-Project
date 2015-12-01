package GUI;
 
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import AStar.Settings;
import AStar.Node;
import AStar.AStar;
import java.util.List;


import java.util.ArrayList;
import java.util.Arrays;
import AStar.NodeList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
 
public class Main extends Application {
	private static Settings defaultSettings = new Settings(false, false, false);
	private static NodeList nlist = new NodeList();
	//public static List<Node> testMap = new ArrayList<Node>();
	public static List<Node> mainMap = new ArrayList<Node>();
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
        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
       
        stage.show();
        //Image is 3300 1450
//        Node a = new Node("a",127, 807,0, "test");
//        Node b = new Node("b",1061, 807,0, "test");
//        Node c = new Node("c",1061, 612,0, "test");
//        Node d = new Node("d",3125, 612,0, "test");
//        Node e = new Node("e",1093, 945,0, "test");
//        Node f = new Node("f",1073, 293,0, "test");
//        Node g = new Node("g",3117, 317,0, "test");
//        a.neighbors.add(b);
//        b.neighbors.add(a);
//        b.neighbors.add(c);
//        b.neighbors.add(e);
//        c.neighbors.add(b);
//        c.neighbors.add(d);
//        c.neighbors.add(f);
//        d.neighbors.add(c);
//        d.neighbors.add(g);
//        e.neighbors.add(b);
//        f.neighbors.add(c);
//        g.neighbors.add(d);
//        testMap.add(a);
//        testMap.add(b);
//        testMap.add(c);
//        testMap.add(d);
//        testMap.add(e);
//        testMap.add(f);
//        testMap.add(g);
        
    }
   
	public static List<Node> getNodesFromFile(String filePath)
	{
		List<Node> nodeList = new ArrayList<Node>();
		BufferedReader br = null;
		String line = "";
		String delimiter = ",";
		int nodeNameIndex = 0;
		int nodeXIndex = 1;
		int nodeYIndex = 2;
		int nodeZIndex = 3;
		int nodeMapIndex = 4;
		int nodeDescIndex = 5;
		int nodeTransferIndex = 6;
		try {

			br = new BufferedReader(new FileReader(filePath));
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] nodeData = line.split(delimiter);
				String name = nodeData[nodeNameIndex];
				int x = Integer.parseInt(nodeData[nodeXIndex]);
				int y = Integer.parseInt(nodeData[nodeYIndex]);
				int z = Integer.parseInt(nodeData[nodeZIndex]);
				String mapName = nodeData[nodeMapIndex];
				String description = nodeData[nodeDescIndex];
				boolean transferNode = nodeData[nodeTransferIndex].equals("TRUE") ? true : false;
				System.out.println("Is transfer : " + transferNode);
				Node newNode = new Node(name,0,0,0,false, x, y, z, mapName, transferNode, description);
				nodeList.add(newNode);
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
		return nodeList;
	}


	public static void connectEdgesFromFile(List<Node> nodeList, String filePath)
	{
		BufferedReader br = null;
		String line = "";
		String delimiter = ",";
		int edgeX1Index = 0;
		int edgeY1Index = 1;
		int edgeZ1Index = 2;
		int edgeMap1Index = 3;
		int edgeX2Index = 4;
		int edgeY2Index = 5;
		int edgeZ2Index = 6;
		int edgeMap2Index = 7;

		try {

			br = new BufferedReader(new FileReader(filePath));
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] edgeData = line.split(delimiter);
				int x1 = Integer.parseInt(edgeData[edgeX1Index]);
				int y1 = Integer.parseInt(edgeData[edgeY1Index]);
				int z1 = Integer.parseInt(edgeData[edgeZ1Index]);
				String nodeMap1 = edgeData[edgeMap1Index];
				int x2 = Integer.parseInt(edgeData[edgeX2Index]);
				int y2 = Integer.parseInt(edgeData[edgeY2Index]);
				int z2 = Integer.parseInt(edgeData[edgeZ2Index]);
				String nodeMap2 = edgeData[edgeMap2Index];
				Node n1 = findNodeByXYZinMap(nodeList, x1, y1, z1, nodeMap1);
				Node n2 = findNodeByXYZinMap(nodeList, x2, y2, z2, nodeMap2);
				if (n1.neighbors == null)
				{
					n1.neighbors =  new ArrayList<>(Arrays.asList(n2));
				}
				else
				{
					n1.neighbors.add(n2);
				}
				if (n2.neighbors == null)
				{
					n2.neighbors =  new ArrayList<>(Arrays.asList(n1));
				}
				else
				{
					n2.neighbors.add(n1);
				}
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


	public static List<Integer> getScaleFromFile(String filePath)
	{
		List<Integer> scaleList = new ArrayList<Integer>();
		BufferedReader br = null;
		String line = "";
		String delimiter = ",";
		try {

			br = new BufferedReader(new FileReader(filePath));
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] scaleData = line.split(delimiter);
				String xScale_char = scaleData[0];
				String yScale_char = scaleData[1];
				int xScale = Integer.parseInt(xScale_char);
				int yScale = Integer.parseInt(yScale_char);
				scaleList.add(xScale);
				scaleList.add(yScale);
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
		return scaleList;
	}
	public static Node findNodeByXYZinMap(List<Node> nodeList, int x, int y, int z, String nodeMap)//Want to change this to throwing an exception when the node is not found
	{
		for(Node n : nodeList){
			if(n.xPos == x && n.yPos == y && n.zPos == z && n.map.equals(nodeMap))
			{
				return n;
			}
		}
		return null;
	}
	public static Node findNodeByName(List<Node> nodeList, String name)//Want to change this to throwing an exception when the node is not found
	{
		for(Node n : nodeList){
			if(n.nodeName.equals(name))
			{
				return n;
			}
		}
		return null;
	}
	public static List<Node> readMap(String nodeFilePath, String edgeFilePath)
	{
		List<Node> nodeList = getNodesFromFile(nodeFilePath);
		connectEdgesFromFile(nodeList, edgeFilePath);
		return nodeList;
	}
	//Method to find the path given a start node and an end node.
	public static List<Node> getPathFromNode(Node startNode, Node endNode, List<Node> map)
	{
		AStar astar = new AStar(defaultSettings);
		System.out.println(map);
		return astar.findPath(startNode, endNode, map);
	}
	//Method to find path when given a string 
	public static List<Node> getPathFromString(String startName, String destName, List<Node> map)
	{
		Node startNode = nlist.findNode(startName);
		Node destNode = nlist.findNode(destName);
		return getPathFromNode(startNode, destNode, map);
		//drawPath(path);
	}

	//Method to provide a list of directions from a list of nodes.
	public static List<String> getDirectionsList(List<Node> path)
	{
		List<String> directions = new ArrayList<String>();
		if(path.size() == 0 || path.size() == 1)
		{
			directions.add("Can't Generate Directions as no path was found");
			return directions;
		}
		else if(path.size() == 2)
		{
			directions.add("Proceed straight on path");
			return directions;
		}
		int prevAngle = 0;
		String prevDirection = "";
		for(int i = 0; i < path.size(); i++)
		{
			if (i == path.size() - 1)
			{
				directions.add("Continue straight until you've have reached your destination");
				break;
			}
			Node n1 = path.get(i);
			Node n2 = path.get(i+1);
			int newAngle = getAngle(path.get(i), path.get(i+1));
			int delta_angle = 0;
			if (i != 0)
			{
				delta_angle = newAngle - prevAngle;
				double delta_angle_rad = (Math.PI / 180) * (double) delta_angle;
				delta_angle = (int) ((180 / Math.PI) * Math.atan2(Math.sin(delta_angle_rad), Math.cos(delta_angle_rad)));//Bind angle to range [-180,180]
			}

			// System.out.println(delta_angle);
			String direction = getDirectionFromAngle(delta_angle);
			double distance_x = ((double)(n2.xPos - n1.xPos) * .18) * 12;
			double distance_y = ((double)(n2.yPos - n1.yPos) * .13) * 12;
			double distance = Math.sqrt((distance_x * distance_x) + (distance_y * distance_y));
			String dist = String.format("%.2f", distance);
			direction = direction + " walk " + dist + " ft";
			//			if (direction.equals("Go Straight") && direction.equals(prevDirection))
			//			{
			//				//Don't repeat straight directions
			//			}
			//			else
			//			{
			directions.add(direction);
			//}
			prevAngle = newAngle;
			prevDirection = direction;
		}
		return directions;
	}
	public static String getDirectionFromAngle(int angle)
	{
		if (-30 < angle && angle < 30)//Going Straight
		{
			return "Go Straight";
		}
		else if (30 <= angle && angle < 60)
		{
			return "Slight right turn";
		}
		else if (60 <= angle && angle < 120)
		{
			return "Right turn";
		}
		else if (120 < angle && angle <= 180)
		{
			return "Sharp right turn";
		}
		else if (-60 < angle && angle <= -30)
		{
			return "Slight left turn";
		}
		else if (-120 < angle && angle <= -60)
		{
			return "Left turn";
		}
		else if (-180 < angle && angle <= -120)
		{
			return "Sharp left turn";
		}
		else
		{
			return "Go Straight";
		}
	}
	//Returns the angle between two nodes in degrees
	public static int getAngle(Node n1, Node n2)
	{
		int dx = n2.xPos - n1.xPos;
		int dy = n2.yPos - n1.yPos;
		return (int) ((180 / Math.PI) * Math.atan2(dy,dx));
	}
	
	// Returns the total distance of the path.
	public static double getTotalDistance(List<Node> path) {

		double totalDist = 0;

		if (path.size() == 0 || path.size() == 1) {
			System.out.println("Cannot get total distance because path was not found.");
			return -1;
		}

		for (int i = 0; i < path.size(); i++) {
			if (i == path.size() - 1) {
				break;
			}

			Node n1 = path.get(i);
			Node n2 = path.get(i + 1);

			double distance_x = ((double) (n2.xPos - n1.xPos) * .18) * 12;
			double distance_y = ((double) (n2.yPos - n1.yPos) * .13) * 12;
			totalDist = totalDist + Math.sqrt((distance_x * distance_x) + (distance_y * distance_y));
		}

		return totalDist;
	}


}
