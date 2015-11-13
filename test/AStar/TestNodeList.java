package AStar;

import junit.framework.TestCase;



public class TestNodeList extends TestCase{

	public void TestRunner() {
		Node node1 = new Node("First",0,0,0,false,0,0);
		Node node2 = new Node("Second",0,0, 0, false,0,1);
		Node node3 = new Node("Third",0,0, 0, false,0,2);
		Node node4 = new Node("Fourth",0,0, 0, false,1,1);
		
		NodeList nlist = new NodeList();
		assertTrue(nlist.addNode("First", node1));
		assertTrue(nlist.addNode("Second", node2));
		assertTrue(nlist.addNode("Third", node3));
		assertTrue(nlist.addNode("Fourth", node4));
		
		
		assertTrue(nlist.deleteNode("Fourth"));
		
	}
	
}
