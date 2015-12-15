package DataAccess;

import java.util.LinkedList;
import java.util.List;

import AStar.Node;
public class Building {
	private String name;
	private String description = "";
	private String hours = "";
	private int angle = 0;
	private int x = 0;
	private int y = 0;
	private double scaleX = 0;
	private double pxPerFt = 0;
	private List<Floor> floors = new LinkedList<Floor>();
	public Building(String name)
	{
		this.name = name;
	}
	public Building(String name, String desc){
		this.name = name;
		this.description= desc;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setHours(String hours)
	{
		this.hours = hours;
	}
	public boolean setFloors(List<Floor> floors)
	{
		if(floors==null){
			return false;
		}
		this.floors = floors;
		return true;
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
	public void setX(int x)
	{
		this.x = x;
	}
	public void setY(int y)
	{
		this.y = y;
	}
	public void setScaleX(double scaleX)
	{
		this.scaleX = scaleX;
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
	public void setName(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return this.name;
	}
	public int getAngle()
	{
		return this.angle;
	}
	public int getX()
	{
		return this.x;
	}
	public int getY()
	{
		return this.y;
	}
	public double getScaleX()
	{
		return this.scaleX;
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
        return this.name + " : floors[ " + this.floors.toString() + " ] ";
}
}
