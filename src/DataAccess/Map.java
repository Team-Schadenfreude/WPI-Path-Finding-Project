package DataAccess;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import AStar.Node;
import javafx.collections.ObservableList;
import javafx.scene.Group;

public class Map extends MapComponent{

	private List<Node> nodes = new LinkedList<Node>();
	private List<Building> buildings = new LinkedList<Building>();
	public Map(String name){
		this.setId(name);
	}
	public List<Building> getBuildingsUnmodifiable()
	{
		return Collections.unmodifiableList(this.buildings);
	}
	public List<Node> toNodeListUnmodifiable() //Be extremely careful using this function
	{
		return Collections.unmodifiableList(this.nodes);
	}
	public void addBuilding(Building building)
	{
		this.buildings.add(building);
		this.getChildren().add(building);
		addNodesFromBuilding(building);
	}
	public void addBaseBuilding(Building b)
	{
		if (this.getChildren().size() > this.safeChildrenIndex)
		{
			this.buildings.add(0, b);
			this.getChildren().add(this.safeChildrenIndex, b);
		}
		else
		{
			System.out.println("Dangerous Index Overwite Detected");
			this.buildings.add(b);
			this.getChildren().add(b);
		}
		addNodesFromBuilding(b);
	}
	public Building getBaseBuilding()
	{
		return (Building)this.getChildren().get(this.safeChildrenIndex);
	}
	private void addNodesFromBuilding(Building b)
	{
		for (Floor f : b.getFloorsUnmodifiable())
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
			if(n.getName().equals(name))
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
			if(n.getX() == x && n.getY() == y && n.getZ() == z && n.getMap().equals(nodeMap))
			{
				return n;
			}
		}
		return null;
	}
	 public void setZoom(double zoomFactor)
	 {
		 this.setScale(zoomFactor, zoomFactor);
	 }

	 public void setZoom(double zoomFactor, double pivotX, double pivotY)
	 {
		 this.getScale().setPivotX(pivotX);
		 this.getScale().setPivotY(pivotY);
		 this.setZoom(zoomFactor);
	 }
}
