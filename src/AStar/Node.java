/*
Alonso Martinez
*/

package AStar;

import java.util.LinkedList;
import java.util.List;

//class for node. A node is a point on the grid that contains an x and y position
public class Node{
	public enum Type { ROOM, STAIRS, BATHROOM_M, BATHROOM_F, ENTRANCE, NONE }; // an Enumeration to represent the different types of nodes
	
	public String nodeName; //every node has a name
    public Node parent; //node has a parent. the parent is where the current node came from
    public double gValue; //gValue is cost from current block
    public double hValue; //hValue is distance
    public double fValue; //fValue is overall cost
    public int xPos; //every Node has an X pos
    public int yPos; // y position of node
	public int zPos;
    public String description;
	public List<Node> neighbors = new LinkedList<Node>();
	public String map;
	public boolean isTransitionNode = false;
	public Type type;
    //constructor for node
    public Node(String nodeName, double hValue,double gValue,double fValue, int xPos, int yPos, int zPos, String map, boolean isTransitionNode, String description, Type nodeType){
            this.nodeName = nodeName;
    		this.hValue = hValue;
            this.gValue = gValue;
            this.xPos = xPos;
            this.yPos = yPos;
            this.description = description;
            this.map = map;
            this.zPos = zPos;
            this.type = nodeType;
            this.isTransitionNode = isTransitionNode;
    }
    
    public Node(String nodeName, int xPos, int yPos, int zpos, String map, String description, Type type)
    {
    	this.nodeName = nodeName;
    	this.hValue = 0;
        this.gValue = 0;
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = 0;
        this.map = map;
        this.description=  description;
        this.type = type;
    }
    
    public Node(String nodeName, int xPos, int yPos, int zPos, String map)
    {
    	this.nodeName = nodeName;
    	this.hValue = 0;
        this.gValue = 0;
        this.xPos = xPos;
        this.yPos = yPos;
        this.map = map;
        this.description = "";
        this.type = Type.NONE;
    }

    //function to turn stringName into an actual string name.
    //Why do we have to do this? Because Java is stupid. JK, Java Master Race
    public String toString(){
            return " Name = " + nodeName + " Map = " + map + " Trans = " + isTransitionNode;
    }
}
