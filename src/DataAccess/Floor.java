package DataAccess;

import java.util.LinkedList;
import java.util.List;

import javafx.scene.image.Image;

public class Floor {

	String name;
	Image mapImage;
	List<Room> rooms = new LinkedList<Room>();
	public Floor(String path, String name) {
		this.mapImage = new Image(path);
		this.name = name;
	}
	public Floor(String name){
		this.name=name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public void setImage(Image image)
	{
		this.mapImage = image;
	}
	public void setRoomList(List<Room> rooms)
	{
		this.rooms = rooms;
	}
	public String getName()
	{
		return this.name;
	}
	public Image getImage()
	{
		return this.mapImage;
	}
	public List<Room> getRoomList()
	{
		return this.rooms;
	}
	public String toString()
	{
		return this.name + " : Rooms [ " + rooms.toString() + " ] ";
	}
}
