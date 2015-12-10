package DataAccess;

import java.util.LinkedList;
import java.util.List;

import AStar.Node;
import javafx.scene.Group;
public class Building extends Group{
	private String description = "";
	private String hours = "";
	private int angle = 0;
	private double pxPerFt = 0;
	private List<Floor> floors = new LinkedList<Floor>();
	public Building(String name)
	{
		this.setId(name);
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public void setHours(String hours)
	{
		this.hours = hours;
	}
	public void setFloors(List<Floor> floors)
	{
		this.floors = floors;
	}
	public String getDescription()
	{
		return this.description;
	}
	public String getHours()
	{
		return this.hours;
	}
	public List<Floor> getFloors()
	{
		return this.floors;
	}
	public void setAngle(int angle)
	{
		this.angle = angle;
	}
	public void setPxPerFt(double pxPerFt)
	{
		this.pxPerFt = pxPerFt;
		for (Floor f : this.getFloors())
		{
			for (Node n : f.getNodes())
			{
				n.setPxPerFt(this.pxPerFt);
			}
		}
	}
	public int getAngle()
	{
		return this.angle;
	}
	public double getPxPerFt()
	{
		return this.pxPerFt;
	}
	public void addFloor(Floor floor)
	{
		this.floors.add(floor);
	}

	public String toString(){
        return this.getId() + " : floors[ " + this.floors.toString() + " ] ";
}
}
