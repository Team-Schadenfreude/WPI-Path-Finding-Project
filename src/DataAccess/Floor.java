package DataAccess;

import java.util.LinkedList;
import java.util.List;

import AStar.Node;
import javafx.scene.Group;
import javafx.scene.image.Image;

public class Floor extends Group{

	private Image mapImage;
	private String dirPath = "";
	private List<Node> nodes = new LinkedList<Node>();
	
	public Floor(String path, String name) {
		this.mapImage = new Image(path);
		this.setId(name);
	}
	public void setDirectoryPath(String path)
	{
		this.dirPath = path;
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
		return this.getId() + " : Rooms [ " + nodes.toString() + " ] ";
	}
}
