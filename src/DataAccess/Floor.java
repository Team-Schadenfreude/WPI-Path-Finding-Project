package DataAccess;

import java.util.LinkedList;
import java.util.List;

import AStar.Node;
import javafx.scene.image.Image;

public class Floor {

	private String name;
	private Image mapImage;
	private String dirPath = "";
	private List<Node> nodes = new LinkedList<Node>();
	
	public Floor(String path, String name) {
		this.mapImage = new Image(path);
		this.name = name;
	}
	public Floor(String name){
		this.name=name;
	}
	public void setDirectoryPath(String path)
	{
		this.dirPath = path;
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
	public String getDirectoryPath()
	{
		return dirPath;
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
	public void addNode(Node node)
	{
		this.nodes.add(node);
	}
	public String toString()
	{
		return this.name + " : Rooms [ " + nodes.toString() + " ] ";
	}
}
