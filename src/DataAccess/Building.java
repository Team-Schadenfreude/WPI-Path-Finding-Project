package DataAccess;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import AStar.Node;
import javafx.scene.Group;

public class Building extends MapComponent{
	private String description = "";
	private String hours = "";
	private double pxPerFt = 0;
	private int activeFloor = 0;
	private List<Floor> floors = new LinkedList<Floor>();
	public Building(String name)
	{
		super();
		this.setId(name);
	}
	public String setActiveFloor(int newFloor)
	{
		if (newFloor < this.getFloorsUnmodifiable().size() && newFloor >= 0)
    	{
			this.activeFloor = newFloor;
			return this.getFloorsUnmodifiable().get(this.activeFloor).getId();
    	}
		return this.getFloorsUnmodifiable().get(this.activeFloor).getId();
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public void setHours(String hours)
	{
		this.hours = hours;
	}
	public String getDescription()
	{
		return this.description;
	}
	public String getHours()
	{
		return this.hours;
	}
	public void setPxPerFt(double pxPerFt)
	{
		this.pxPerFt = pxPerFt;
		for (Floor f : this.getFloorsUnmodifiable())
		{
			for (Node n : f.getNodes())
			{
				n.setPxPerFt(this.pxPerFt);
			}
		}
	}
	public int getActiveFloor()
	{
		return this.activeFloor;
	}
	//Returns an unmodifiable list of floors
	public List<Floor> getFloorsUnmodifiable()
	{
		return Collections.unmodifiableList(this.floors);
	}
	public void addFloor(Floor f)
	{
		this.floors.add(f);
		this.getChildren().add(f);
	}
	public void addFloor(int i, Floor f)
	{
		this.floors.add(i, f);
		this.getChildren().add(i, f);
	}
	public double getPxPerFt()
	{
		return this.pxPerFt;
	}
	public String toString(){
        return this.getId();// + " : floors[ " + this.floors.toString() + " ] ";
	}
}
