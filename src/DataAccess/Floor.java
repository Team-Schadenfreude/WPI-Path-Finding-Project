package DataAccess;

import java.util.LinkedList;
import java.util.List;

import AStar.Node;
import javafx.scene.image.Image;

public class Floor {

	String name;
	Image mapImage;
	List<Node> nodes = new LinkedList<Node>();
	
	public Floor(String path, String name) {
		this.mapImage = new Image(path);
		this.name = name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public void setImage(Image image)
	{
		this.mapImage = image;
	}
	public void setNodes(List<Node> nodes)
	{
		this.nodes = nodes;
	}
	public String getName()
	{
		return this.name;
	}
	public Image getImage()
	{
		return this.mapImage;
	}
	public List<Node> getNodes()
	{
		return this.nodes;
	}
	public String toString()
	{
		return this.name + " : Rooms [ " + nodes.toString() + " ] ";
	}
}
