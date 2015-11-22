/*
Alonso Martinez
*/

package AStar;

import java.util.ArrayList;
import java.util.List;

//class for node. A node is a point on the grid that contains an x and y position
public class Node{
	public String nodeName; //every node has a name
    public Node parent; //node has a parent. the parent is where the current node came from
    public List<Node> neighbors = new ArrayList<Node>(); //every node contains neighbors
    public double gValue; //gValue is cost from current block
    public double hValue; //hValue is distance
    public double fValue; //fValue is overall cost
    public int xPos; //every Node has an X pos
    public int yPos; // y position of node
    public Boolean isObstacle; //variable to see if the node is an obstacle
    public String description;
    
    //constructor for node
    public Node(String nodeName, double hValue,double gValue,double fValue, Boolean isObstacle, int xPos, int yPos, String description){
            this.nodeName = nodeName;
    		this.hValue = hValue;
            this.gValue = gValue;
            this.isObstacle = isObstacle;
            this.xPos = xPos;
            this.yPos = yPos;
            this.description = description;
    }
    public Node(int xPos, int yPos, String nodeName)
    {
    	this.nodeName = nodeName;
    	this.hValue = 0;
        this.gValue = 0;
        this.isObstacle = false;
        this.xPos = xPos;
        this.yPos = yPos;
        this.description = "";
    }

    //function to turn stringName into an actual string name.
    //Why do we have to do this? Because Java is stupid. JK, Java Master Race
    public String toString(){
            return nodeName;
    }

}
