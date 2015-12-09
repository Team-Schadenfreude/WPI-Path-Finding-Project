/*
Alonso Martinez
*/

package AStar;

import java.util.LinkedList;
import java.util.List;

//class for node. A node is a point on the grid that contains an x and y position
public class Node{
	public enum Type { ROOM, STAIRS, ELEVATOR, BATHROOM_M, BATHROOM_F, ENTRANCE, NONE }; // an Enumeration to represent the different types of nodes
	
	private String nodeName; //every node has a name
    private Node parent; //node has a parent. the parent is where the current node came from
    private double gValue; //gValue is cost from current block
    private double hValue; //hValue is distance
    private double fValue; //fValue is overall cost
    private int xPos; //every Node has an X pos
    private int yPos; // y position of node
	private int zPos;
    private String description;
	private List<Node> neighbors = new LinkedList<Node>();
	private String map;
	private boolean isTransitionNode = false;
	private Type type;
	private double pxPerFt = 1;
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
    public double getPxPerFt()
    {
    	return this.pxPerFt;
    }
    public String getName()
    {
    	return this.nodeName;
    }
    public double getHVal()
    {
    	return this.hValue;
    }
    public double getGVal()
    {
    	return this.gValue;
    }
    public double getFVal()
    {
    	return this.fValue;
    }
    public int getX()
    {
    	return this.xPos;
    }
    public int getY()
    {
    	return this.yPos;
    }
    public int getZ()
    {
    	return this.zPos;
    }
    public String getMap()
    {
    	return this.map;
    }
    public String getDescription()
    {
    	return this.description;
    }
    public boolean isTransition()
    {
    	return this.isTransitionNode;
    }
    public List<Node> getNeighbors()
    {
    	return this.neighbors;
    }
    public Type getType()
    {
    	return this.type;
    }
    public Node getParent()
    {
    	return this.parent;
    }
    
    public void setPxPerFt(double val)
    {
    	this.pxPerFt = val;
    }
    public void setName(String name)
    {
    	this.nodeName = name;
    }
    public void setHVal(double hVal)
    {
    	this.hValue = hVal;
    }
    public void setGVal(double gVal)
    {
    	this.gValue = gVal;
    }
    public void setFVal(double fVal)
    {
    	this.fValue = fVal;
    }
    public void setX(int x)
    {
    	this.xPos = x;
    }
    public void setY(int y)
    {
    	this.yPos = y;
    }
    public void setZ(int z)
    {
    	this.zPos = z;
    }
    public void setMap(String map)
    {
    	this.map = map;
    }
    public void setDescription(String descrip)
    {
    	this.description = descrip;
    }
    public void setIsTransition(boolean transition)
    {
    	this.isTransitionNode = transition;
    }
    public void setNeighbors(List<Node> list)
    {
    	this.neighbors = list;
    }
    public void setType(Type t)
    {
    	this.type = t;
    }
    public void setParent(Node n)
    {
    	this.parent = n;
    }
    public double distanceTo(Node node)
    {
//    	if (this.type == Type.ELEVATOR &&  node.type == Type.ELEVATOR)
//    	{
//    		return 40;
//    	}
//    	else if (this.type == Type.STAIRS &&  node.type == Type.STAIRS)
//    	{
//    		return 20;
//    	}
    	if (this.isTransition() && node.isTransition())
    	{
    		return 10000;
    	}
    	double x1 = this.getX() * this.pxPerFt;
    	double y1 = this.getY() * this.pxPerFt;
    	double x2 = node.getX() * node.pxPerFt;
    	double y2 = node.getY() * node.pxPerFt;
    	double dx = x2 - x1;
		double dy = y2 - y1;
		double distance = Math.sqrt(dx*dx + dy*dy);
		return distance;
    }
    //function to turn stringName into an actual string name.
    //Why do we have to do this? Because Java is stupid. JK, Java Master Race
    public String toString(){
            return " Name = " + nodeName + " Map = " + map + " Trans = " + isTransitionNode;
    }
}
