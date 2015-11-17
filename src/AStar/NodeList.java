package AStar;

import java.util.HashMap;

public class NodeList {
	private HashMap<String, Node> nodes;

	public NodeList() {
		nodes = new HashMap<String, Node>();
	}
	
	public HashMap<String, Node> getListOfNodes(){
		return nodes;
	}

	// right now very inefficient. We could use some sorting algorithm...
	public boolean addNode(String s, Node n) {
		return (nodes.put(s,n)==n);		
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