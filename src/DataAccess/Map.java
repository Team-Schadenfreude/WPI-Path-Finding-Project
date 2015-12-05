package DataAccess;

import java.util.LinkedList;
import java.util.List;

import AStar.Node;

public class Map {

	private List<Building> buildings;
	private List<Node> nodes;
	private String name;
	public Map() {
	}
	public Map(String name, List<Building> buildings){
		this.buildings = buildings;
		this.name = name;
		getNodesFromBuildings();
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
	public List<Node> toNodeList()
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
}
