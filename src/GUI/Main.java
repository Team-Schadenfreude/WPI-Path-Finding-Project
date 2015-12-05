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
	public static Map mainMap = new Map();
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
				boolean transferNode = false;
				if (nodeData[nodeTransferIndex].contains("TRUE") || nodeData[nodeTransferIndex].contains("true"))
				{
					transferNode = true;
				}
				
				Node newNode = new Node(name,0,0,0,false, x, y, z, mapName, transferNode, description);
				//System.out.println("Is transfer : " + newNode.isTransitionNode);
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
		System.out.println("Edges From " + filePath);
		try {

			br = new BufferedReader(new FileReader(filePath));
			int i = 0;
			while ((line = br.readLine()) != null && line.length() > 0) {
				// use comma as separator
				System.out.println("i = " + i);
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
				i++;
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
	//Method to find the path given a start node and an end node.
	public static List<Node> getPathFromNode(Node startNode, Node endNode, List<Node> map)
	{
		AStar astar = new AStar(defaultSettings);;
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
	public static List<String> getDirectionsList(List<Node> path, double xScale, double yScale)
	{
		List<String> directionsList = new ArrayList<String>();
		if(path.size() == 0)
		{
			directionsList.add("There is no path to follow");
		}
		else
		{
			double totalDistance = 0;
			double distance = 0;
			int prevAngle = 0;
			int currentAngle = 0;
			int prevDirVal = 0;
			int dirVal = 0;
			int deltaAngle = 0;
			boolean mapChange = false;
			System.out.println(path);
			for(int i = 0; i < path.size() -1 ; i++)
			{
				Node n1 = path.get(i);
				Node n2 = path.get(i+1);
				if (n1.isTransitionNode && n2.isTransitionNode && !n1.map.equals(n2.map))
				{
					//String direction = "Procede into " + n2.map;
					totalDistance += distance;
					String direction = getStringFromDirectionValue(prevDirVal);
					directionsList.add(direction + " " + Integer.toString((int)distance) + " ft");
					directionsList.add("Procede into " + n2.map);
					distance = 0;
					prevDirVal = 0;
					prevAngle = 0;
					currentAngle = 0;
					prevDirVal = 0;
					dirVal = 0;
					deltaAngle = 0;
					mapChange = true;
					
				}
				else
				{
					currentAngle = getAngle(n1, n2);
					deltaAngle = 0;
					deltaAngle = currentAngle - prevAngle;
					double delta_angle_rad = (Math.PI / 180) * (double) deltaAngle;
					deltaAngle = (int) ((180 / Math.PI) * Math.atan2(Math.sin(delta_angle_rad), Math.cos(delta_angle_rad)));//Bind angle to range [-180,180]
					dirVal = getDirectionValueFromAngle(deltaAngle);
					if (i == 0 || mapChange)
					{
						deltaAngle = 0;
						dirVal = 0;
						mapChange = false;
					}
					if (dirVal != 0)
					{
						totalDistance += distance;
						String direction = getStringFromDirectionValue(prevDirVal);
						directionsList.add(direction + " " + Integer.toString((int)distance) + " ft");
						distance = 0;
						prevDirVal = dirVal;
					}
					prevAngle = currentAngle;
					double distance_x = (double)(n2.xPos - n1.xPos) * xScale;
					double distance_y = (double)(n2.yPos - n1.yPos) * yScale;
					distance = Math.sqrt((distance_x * distance_x) + (distance_y * distance_y));
				}
				
				
			}
			totalDistance += distance;
			String direction = getStringFromDirectionValue(prevDirVal);
			directionsList.add(direction + " " + Integer.toString((int)distance) + " ft");
			directionsList.add("Procede to Destination");
			directionsList.add("Total Distance is " + Integer.toString((int)totalDistance) + "ft");
		}
		return directionsList;
	}
	public static int getDirectionValueFromAngle(int angle)
	{
		if (-20 < angle && angle < 20)//Going Straight
		{
			return 0;
		}
		else if (20 <= angle && angle < 60)
		{
			return 1;
		}
		else if (60 <= angle && angle < 120)
		{
			return 2;
		}
		else if (120 < angle && angle <= 180)
		{
			return 3;
		}
		else if (-60 < angle && angle <= -20)
		{
			return 4;
		}
		else if (-120 < angle && angle <= -60)
		{
			return 5;
		}
		else if (-180 < angle && angle <= -120)
		{
			return 6;
		}
		else
		{
			return 0; //GoingStraight
		}
	}
	public static String getStringFromDirectionValue(int val)
	{
		switch(val)
		{
		case 0:
			return "Go Straight";
		case 1:
			return "Slight right turn";
		case 2:
			return "Right turn";
		case 3:
			return "Sharp right turn";
		case 4:
			return "Slight left turn";
		case 5:
			return "Left turn";
		case 6:
			return "Sharp left turn";
		default:
			return ""; //GoingStraight
		}
	}
	//Returns the angle between two nodes in degrees
	public static int getAngle(Node n1, Node n2)
	{
		int dx = n2.xPos - n1.xPos;
		int dy = n2.yPos - n1.yPos;
		return (int) ((180 / Math.PI) * Math.atan2(dy,dx));
	}
}
