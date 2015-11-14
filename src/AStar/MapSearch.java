/*
 * Alonso Martinez
 */

package AStar;
/*Testing Testin*/

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Set;

public class MapSearch{
	//variable to see if we have found our node
    static boolean found = false;
    int i = 0;
	public static void main(String[] args){

		
		//define some test nodes 
		Node node1 = new Node("First",0,0,0,false,0,0);
		Node node2 = new Node("Second",0,0, 0, false,0,1);
		Node node3 = new Node("Third",0,0, 0, false,0,2);
		Node node4 = new Node("Fourth",0,0, 0, false,1,1);


		//initialize the edges
		//if two nodes connect, you need to add an edge between them.
		//for future iteration, make this auto generated
		node1.neighbors = new Edge[]{
				new Edge(node2,getDistance(node1,node2)),
				new Edge(node4,getDistance(node1,node4))

		};

		node2.neighbors = new Edge[]{
				new Edge(node1,getDistance(node1,node2)),
				new Edge(node3,getDistance(node2,node3))


		};

		node3.neighbors = new Edge[]{
				new Edge(node2,getDistance(node2,node3)),
				new Edge(node4,getDistance(node4,node3))


		};
		
		
		node4.neighbors = new Edge[]{
				new Edge(node3,getDistance(node3,node4)),
				new Edge(node1,getDistance(node1,node4)),


		};
	

		//function finds the path between the nodes
		findPath(node1,node4);

		//create a new list of nodes and set it equal to the result of the 
		List<Node> path = printPath(node4);

		System.out.println(getDistance(node1,node4));
		System.out.println(path);
		


	}

	
	//function to print out the path. Takes a node and will print the path if there is one to this node
	public static List<Node> printPath(Node end){
		
		//create a new array list of nodes
		List<Node> path = new ArrayList<Node>();
		
		
		//get the nodes in sequence from the end to the start
		for(Node node = end; node!=null; node = node.parent){
			path.add(node);
		}

		//reverse the path as it goes from end to finish and we want finish to start
		Collections.reverse(path);

		return path;
	}


	public static void findPath(Node start, Node end){

		
		//create a new hashSet of nodes that we have already checked
		Set<Node> checkedNodes = new HashSet<Node>();

		
		
		//this is our queue for storing the nodes that we are one. This queue will process 
		//the nodes. We are using a queue because of speed reasons
		PriorityQueue<Node> queue = new PriorityQueue<Node>(20, 
				new Comparator<Node>(){
			
			//we need this compare method to compare the costs of the nodes so that we can
			//sort our queue based on the closest node
			public int compare(Node i, Node j){
				if(i.fValue > j.fValue){
					return 1;
				}

				else if (i.fValue < j.fValue){
					return -1;
				}

				else{
					return 0;
				}
			}

		}
				);

		//set our first gValue to 0 since it is the start
		start.gValue = 0;

		//add our start node to the queue
		
		queue.add(start);
		
		
		//while our queue is not empty and we have not found a path, execute
		while((!queue.isEmpty())&&(!found)){

			//define a new node named current that will be the current node we are processing
			//set this equal to the top element of the queue for processing
			Node current = queue.poll();

			//add our node to the checked nodes set so that we dont check it again
			checkedNodes.add(current);

			//if the node name is equal to the node we are searching for, then we are done
			//might change this so that it works off of something other than name
			if(current.nodeName.equals(end.nodeName)){
				found = true;
				System.out.println("Found path");
			}

			//here we are checking every edge of every neighboring node for the node we are searching for
			
			for(Edge edge : current.neighbors){
				Node neighbor = edge.connection;
				double cost = edge.cost;
				double gScoreTemp = current.gValue + cost;
				double fScoreTemp = gScoreTemp + neighbor.hValue;


				//if a child has already been checked and the fScore is greater, than we dont want to process that node
				//since the node is further away from our end node
				if((checkedNodes.contains(neighbor)) && 
						(fScoreTemp >= neighbor.fValue)){
					continue;
				}

				//if the queue does not contain the neighbor node and the new fValue
				//is lower, then we DO want to process the node
				else if((!queue.contains(neighbor)) || 
						(fScoreTemp < neighbor.fValue)){

					//set the parent node to the current node so that we evaluate this new node
					neighbor.parent = current;
					neighbor.gValue = gScoreTemp;
					neighbor.fValue = fScoreTemp;

					
					//if the queue already contains the neighbor node, then remove it
					if(queue.contains(neighbor)){
						queue.remove(neighbor);
					}

					//otherwise add the new neighbor to the queue
					queue.add(neighbor);

				}

			}

		}

	}
	
	//function gets the distance between two nodes. This will be used as the cost
	public static double getDistance(Node one, Node two){

		double dx = one.xPos - two.xPos;

		double dy = one.yPos - two.yPos;

		double distance = Math.sqrt(dx*dx + dy*dy);


		return distance;
	}

}
