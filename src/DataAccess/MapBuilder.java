package DataAccess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import AStar.Node;
import GUI.Main;
import GUI.Room;
import GUI.ZoomingPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MapBuilder {

	private File mapPath;
	public MapBuilder(File mapPath) {
		this.mapPath = mapPath;
	}
	public void setMapPath(File mapPath)
	{
		this.mapPath = mapPath;
	}
	public File getMapPath()
	{
		return this.mapPath;
	}
	public Map buildMap()
	{
		Map map = new Map();
		
		return map;
	}
    @FXML 
    protected void handleLoadMap(ActionEvent event) {
    	
    	File selectedDirectory = getDirectoryFromDialog(); //Get SuperMap Directory
    	//setupDropDowns(selectedDirectory + "\\Rooms.csv"); //Read in list of all rooms on campus
    	for (File dir : selectedDirectory.listFiles()) //Draw the super map
    	{
    		if (dir.isDirectory() && dir.getName().charAt(0) == '_') //The file is a directory and a building
    		{
    			Building b = new Building(dir.getName().substring(1));
    			System.out.println("Reading " + b.getName());
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
    	startNode = Main.mainMap.get(0);
    	goalNode = Main.mainMap.get(4);
    	
    	drawMap(buildingList);
    	imageZoomPane = new ZoomingPane(mainGroup);
    	imageScrollPane.setContent(imageZoomPane);
    	setupDropDowns();
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
}
