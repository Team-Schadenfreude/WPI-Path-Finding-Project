/*
Alonso Martinez
*/

package AStar;

import java.util.ArrayList;
import java.util.List;

import DataAccess.Building;
import javafx.scene.shape.Circle;

//class for node. A node is a point on the grid that contains an x and y position
public class Node{
	public String nodeName; //every node has a name
    public Node parent; //node has a parent. the parent is where the current node came from
    public double gValue; //gValue is cost from current block
    public double hValue; //hValue is distance
    public double fValue; //fValue is overall cost
    public int xPos; //every Node has an X pos
    public int yPos; // y position of node
	public int zPos;
	public Building building;
    public Boolean isObstacle; //variable to see if the node is an obstacle
    public String description;
	public List<Node> neighbors = new ArrayList<Node>();
	public String map;
	public boolean isTransitionNode = false;
	public Circle circle = new Circle(xPos,yPos,100);
    
    //constructor for node
    public Node(String nodeName, double hValue,double gValue,double fValue, Boolean isObstacle, int xPos, int yPos,int zPos, String map,boolean transfer,String description){
            this.nodeName = nodeName;
    		this.hValue = hValue;
            this.gValue = gValue;
            this.isObstacle = isObstacle;
            this.xPos = xPos;
            this.yPos = yPos;
            this.description = description;
            this.map = map;
            this.zPos = zPos;
            this.isTransitionNode = transfer;
    }
    public Node(String nodeName,int xPos, int yPos,int zpos, String map)
    {
    	this.nodeName = nodeName;
    	this.hValue = 0;
        this.gValue = 0;
        this.isObstacle = false;
        this.xPos = xPos;
        this.yPos = yPos;
        this.map = map;
        this.circle=circle;
    }

    //function to turn stringName into an actual string name.
    //Why do we have to do this? Because Java is stupid. JK, Java Master Race
    public String toString(){
            return nodeName;
    }
    
    
    
    
    

}
