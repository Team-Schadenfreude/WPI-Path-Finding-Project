package DataAccess;

import AStar.Node;

public class Room {
	private String name;
	private Node roomNode;
	public Room(String name, Node roomNode)
	{
		this.name = name;
		this.roomNode = roomNode;
	}
	public String getName()
	{
		return this.name;
	}
	
	public String toString(){
        return this.name;
	}
	public Node getNode()
	{
		return this.roomNode;
	}
	public void setNode(Node n)
	{
		this.roomNode = n;
	}
}
