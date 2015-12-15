package AStar;

import java.util.HashMap;
/*
 * this class holds the information of all the nodes
 */
public class NodeList {
	private HashMap<String, Node> nodes;

	public NodeList() {
		nodes = new HashMap<String, Node>();
	}
	
	public HashMap<String, Node> getListOfNodes(){
		return nodes;
	}

	public int getSize(){
		return nodes.size();
	}
	
	public void addNode(String s, Node n) {
		nodes.put(s,n);		
	}

	public boolean deleteNode(String name) {
		return (nodes.remove(name) != null);
	}
	
	public Node findNode(String name){
		return nodes.get(name);
	}
	
	public Node findNode (int x, int y){	
		return null;
	}

	public boolean addNodes(Node... nodes) {
		return false;
	}

	public boolean deleteNodes(String... names) {
		return false;
	}

}