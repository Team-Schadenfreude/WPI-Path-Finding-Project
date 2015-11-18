package AStar;

import java.util.HashMap;
import org.junit.Test;
import junit.framework.TestCase;


public class TestNodeList extends TestCase{
	
	@Test
	public void testNodeList() {
		NodeList nodes = new NodeList();
		Node n1 = new Node(0, 0, "A");
		Node n2 = new Node(1, 0, "B");
		Node n3 = new Node(2, 0, "C");
		nodes.addNode("A", n1);
		nodes.addNode("B", n2);
		nodes.addNode("C", n3);
		HashMap<String, Node> nhash = new HashMap<String, Node>();
		nhash.put("A", n1);
		nhash.put("B", n2);
		nhash.put("C", n3);
		assertEquals(nodes.getSize(), nhash.size());
		assertEquals(nodes.getListOfNodes(), nhash);	
		nodes.deleteNode("A");
		nhash.remove("A");
		assertEquals(nodes.getListOfNodes(), nhash);
		Node n4 = nodes.findNode("B");
		assertEquals(n4, n2);
		
	}
}
