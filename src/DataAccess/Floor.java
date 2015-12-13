package DataAccess;

import java.util.LinkedList;
import java.util.List;

import AStar.Node;
import javafx.scene.Group;
import javafx.scene.image.Image;

public class Floor extends MapComponent{

	private Image mapImage;
	private String dirPath = "";
	private List<Node> nodes = new LinkedList<Node>();
	private List<Node> rooms = new LinkedList<Node>();
	public Floor(String path, String name) {
		super();
		this.mapImage = new Image(path);
		this.setBaseImage(mapImage);
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
		if (node.getType() == Node.Type.ROOM || node.getType() == Node.Type.BATHROOM_F || node.getType() == Node.Type.BATHROOM_M)
		{
			this.rooms.add(node);
		}
	}
	public String toString()
	{
		return this.getId() + " : Rooms [ " + nodes.toString() + " ] ";
	}
	public Node getNearestRoom(int x, int y)
	{
		double minDistance = 0;
		boolean firstRun = true;
		Node closestNode = null;
		for (Node node : this.rooms)
		{
			double distance = node.distanceTo(new Node("", x, y, 0, ""));
			if(firstRun)
			{
				minDistance = distance;
				firstRun = false;
				closestNode = node;
			}
			else if(distance < minDistance)
			{
				minDistance = distance;
				closestNode = node;
			}
			
		}
		return closestNode;
	}
}
