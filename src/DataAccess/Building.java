package DataAccess;

import java.util.List;
import DataAccess.Room;
public class Building {
	String name;
	private List<Room> rooms;
	
	public Building(String name, List<Room> rooms)
	{
		this.name = name;
		this.rooms = rooms;
	}
	public void setRooms(List<Room> rooms)
	{
		this.rooms = rooms;
	}
	public List<Room> getRooms()
	{
		return this.rooms;
	}
	public String getName()
	{
		return this.name;
	}
	public String toString(){
        return this.name;
}
}
