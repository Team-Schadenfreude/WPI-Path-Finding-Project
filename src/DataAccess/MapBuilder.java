package DataAccess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import AStar.Node;

public class MapBuilder {

	private String mapPath;
	String baseMap;
	public MapBuilder(String mapPath, String baseMap) {
		this.mapPath = mapPath;
		this.baseMap = baseMap;
	}
	public void setMapPath(String mapPath)
	{
		this.mapPath = mapPath;
	}
	public String getMapPath()
	{
		return this.mapPath;
	}
	public Map buildMap()
	{
		Map map = new Map(baseMap, baseMap);
		//File selectedDirectory = getDirectoryFromDialog(); //Get SuperMap Directory
    	File selectedDirectory = new File(mapPath);
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
    					floor.setNodes(getNodesFromFile(subDir + "\\mapNodes.csv"));
    					b.addFloor(floor);
    				}
    			}
    			
    			if (b.getName().equals(baseMap) && map.getBuildingCount() > 0)
    			{
    				map.getBuildings().add(0,b);
    			}
    			else
    			{
    				map.addBuilding(b);
    			}
    		}
    	}
    	for (File dir : selectedDirectory.listFiles()) //Draw the super map
    	{
    		if (dir.isDirectory() && dir.getName().charAt(0) == '_') //The file is a directory and a building
    		{
    			for (File subDir : dir.listFiles())
    			{
    				if (subDir.isDirectory()) //The file is a directory and a floor plan
    				{
    					connectEdgesFromFile(map, subDir + "\\mapEdges.csv");
    				}
    			}
    		}
    	}
		return map;
	}
	
	private Node.Type nodeTypeFromString(String type)
	{
		switch(type)
		{
		case "ROOM":
			return Node.Type.ROOM;
		case "STAIRS":
			return Node.Type.STAIRS;
		case "BATHROOM_M":
			return Node.Type.BATHROOM_M;
		case "BATHROOM_F":
			return Node.Type.BATHROOM_F;
		case "ENTRANCE":
			return Node.Type.ENTRANCE;
		default:
			return Node.Type.NONE;
		}
	}
	public List<Node> getNodesFromFile(String filePath)
	{
		List<Node> nodeList = new LinkedList<Node>();
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
		int nodeTypeIndex = 7;
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
				Node.Type nodeType = nodeTypeFromString(nodeData[nodeTypeIndex]);
				boolean transferNode = false;
				System.out.println("Transfer Node is -" + nodeData[nodeTransferIndex] + "-");
				if (nodeData[nodeTransferIndex].contains("TRUE") || nodeData[nodeTransferIndex].contains("true"))
				{
					transferNode = true;
				}
				Node newNode = new Node(name,0,0,0,x, y, z, mapName, transferNode, description, nodeType);
				System.out.println("Node transfer = " + newNode.isTransitionNode);
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


	private void connectEdgesFromFile(Map map, String filePath)
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
				//System.out.println("i = " + i);
				String[] edgeData = line.split(delimiter);
				int x1 = Integer.parseInt(edgeData[edgeX1Index]);
				int y1 = Integer.parseInt(edgeData[edgeY1Index]);
				int z1 = Integer.parseInt(edgeData[edgeZ1Index]);
				String nodeMap1 = edgeData[edgeMap1Index];
				int x2 = Integer.parseInt(edgeData[edgeX2Index]);
				int y2 = Integer.parseInt(edgeData[edgeY2Index]);
				int z2 = Integer.parseInt(edgeData[edgeZ2Index]);
				String nodeMap2 = edgeData[edgeMap2Index];
				Node n1 = map.findNodeByXYZinMap(x1, y1, z1, nodeMap1);
				Node n2 = map.findNodeByXYZinMap(x2, y2, z2, nodeMap2);
				if (n1.neighbors == null)
				{
					n1.neighbors =  new LinkedList<>(Arrays.asList(n2));
				}
				else
				{
					n1.neighbors.add(n2);
				}
				if (n2.neighbors == null)
				{
					n2.neighbors =  new LinkedList<>(Arrays.asList(n1));
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
	
}
