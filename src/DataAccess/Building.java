package DataAccess;

import java.util.LinkedList;
import java.util.List;
public class Building {
	String name;
	String description = "";
	String hours = "";
	int angle = 0;
	int x = 0;
	int y = 0;
	double scaleX = 0;
	double scaleY = 0;
	private List<Floor> floors = new LinkedList<Floor>();
	public Building(String name)
	{
		this.name = name;
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
	public void setScaleY(double scaleY)
	{
		this.scaleY = scaleY;
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
	public double getScaleY()
	{
		return this.scaleY;
	}
	public void addFloor(Floor floor)
	{
		this.floors.add(floor);
	}
	public String toString(){
        return this.name + " : floors[ " + this.floors.toString() + " ] ";
}
}
