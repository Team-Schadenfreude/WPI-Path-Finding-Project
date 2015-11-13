package AStar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NodeList {
	List<Node> nodes;
	
	public NodeList(){
		nodes=new ArrayList<Node>();
	}
	//right now very inefficient. We could use some sorting algorithm...
	public boolean addNode(Node n){
		if(!nodes.contains(n)&&nodes!=null){
			nodes.add(n);
			return true;
		}
		
		return false;
	}
	
	public boolean deleteNode(String name){
		Iterator it = nodes.iterator();
		while(it.hasNext()){
			Node node=(Node)it.next();
			if(node.nodeName==name){		
				it.remove();
				return true;
			}
		}
		
		return false;
	}
	
	public boolean addNodes (Node... nodes){
		return false;
	}
	
	public boolean deleteNodes (String... names){
		return false;
	}

}
