package DataAccess;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
public class Building {
	String name;
	String description;
	List<String> times = new ArrayList<String>();
	String time;	
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
	public Building (String name, String desc, String times){
		this.name = name;
		this.description = desc;
		this.time=times;
	}
	public boolean setFloors(List<Floor> floors)
	{
		if(floors==null){
			return false;
		}
		this.floors = floors;
		return true;
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
	public String getDesc(){
		return description;
	}
	public String getName()
	{
		return this.name;
	}
	public String getTimes(){
		return time;
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
	public String toString(){
        return this.name + " : floors[ " + this.floors.toString() + " ] ";
}
}
