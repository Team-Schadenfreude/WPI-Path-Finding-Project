package DataAccess;

import java.util.LinkedList;
import java.util.List;

import AStar.Node;

public class Map {

	private List<Building> buildings = new LinkedList<Building>();
	private List<Node> nodes;
	private String name;
	private String baseMap;
	public Map() {
	}
	public Map(String name, String baseMap){
		this.name = name;
		this.baseMap = baseMap;
	}
	private void getNodesFromBuildings()
	{
		nodes = new LinkedList<Node>();
		for(Building b : this.buildings)
		{
			for (Floor f : b.getFloors())
			{
				for (Node n : f.getNodes())
				{
					nodes.add(n);
				}
			}
		}
	}
	public List<Building> getBuildings()
	{
		return this.buildings;
	}
	public List<Node> toNodeList() //Be extremely careful using this function
	{
		return this.nodes;
	}
	public String getName()
	{
		return this.name;
	}
	public void setBuidlings(List<Building> buildings)
	{
		this.buildings = buildings;
		getNodesFromBuildings();
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public void addBuilding(Building building)
	{
		this.buildings.add(building);
		addNodesFromBuilding(building);
	}
	private void addNodesFromBuilding(Building b)
	{
		for (Floor f : b.getFloors())
		{
			nodes.addAll(f.getNodes());
		}
	}
	public int getBuildingCount()
	{
		return this.buildings.size();
	}
	public int getNodeCount()
	{
		return this.nodes.size();
	}
	public Node findNodeByName( String name)//Want to change this to throwing an exception when the node is not found
	{
		for(Node n : nodes)
		{
			if(n.nodeName.equals(name))
			{
				return n;
			}
		}
		return null;
	}
	 public Node findNodeByXYZinMap(int x, int y, int z, String nodeMap)//Want to change this to throwing an exception when the node is not found
	{
		for(Node n : nodes)
		{
			if(n.xPos == x && n.yPos == y && n.zPos == z && n.map.equals(nodeMap))
			{
				return n;
			}
		}
		return null;
	}
}
