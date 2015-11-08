/*
Alonso Martinez
*/
package AStar;


//an edge is the connection between two nodes
public class Edge{
	public double cost; //an edge has a cost (the distance) between the two nodes
	public Node connection; //the connection node is what the node connects to  


	//constructor for edge 
	public Edge(Node connection, double cost){
		this.connection = connection;
		this.cost = cost;
	}
}
