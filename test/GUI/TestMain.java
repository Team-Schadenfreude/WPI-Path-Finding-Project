package GUI;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import AStar.AStar;
import AStar.Node;
import AStar.Settings;

public class TestMain {
	
	/*
	 * Tests for running AStar.
	 */
//	@Test
//	public void testGetNodesFromFileWhenFileDoesNotExist(){
//
//		List<Node> l=Main.getNodesFromFile("");
//		assertEquals(true,l.isEmpty());
//		assertEquals("FileNotFound", Main.excmessage);
//	}
/*	@Test
	public void testGetNodesFromFileThatIsNotReadable(){
		Main.excmessage="";
		List<Node> l=Main.getNodesFromFile("./src/res/testfile.csv");
		assertEquals(true,l.isEmpty());
		assertEquals("IOException", Main.excmessage);
	}*/
	@Test
	public void test_mcc_1() { // Test for horizontal nodes where path is curved
		Settings defaultSettings = new Settings(false, false, false);
		List<Node> map = new ArrayList<Node>();
		Node a = new Node(0, 0, "A");
		Node b = new Node(1, 0, "B");
		Node c = new Node(2, 0, "C");
		a.setNeighbors(new ArrayList<>(Arrays.asList(c)));
		b.setNeighbors(new ArrayList<>(Arrays.asList(c)));
		c.setNeighbors(new ArrayList<>(Arrays.asList(b, a)));
		map.add(a);
		map.add(b);
		map.add(c);
		List<Node> best_path = new ArrayList<Node>();
		best_path.add(a);
		best_path.add(c);
		best_path.add(b);
		AStar astar = new AStar(defaultSettings);
		List<Node> path = astar.findPath(a, b, map);
		List<Node> path2 = astar.findPath(b, c, map);
		List<Node> best_path2 = new ArrayList<Node>();
		best_path2.add(b);
		best_path2.add(c);
		assertEquals(best_path, path);
		assertEquals(best_path2,path2);
		
	}

	@Test
	public void test_mcc_2() { // Test for complex search
		Settings defaultSettings = new Settings(false, false, false);
		List<Node> map = new ArrayList<Node>();

		Node a = new Node(0, 0, "A");
		Node b = new Node(-1, 1, "B");
		Node c = new Node(0, 1, "C");
		Node d = new Node(1, 0, "D");
		Node e = new Node(2, 1, "E");
		Node f = new Node(-1, 2, "F");
		Node g = new Node(0, 3, "G");
		// Goal = g; Start = A
		a.setNeighbors(new ArrayList<>(Arrays.asList(b, c)));
		b.setNeighbors(new ArrayList<>(Arrays.asList(a, f, g)));
		c.setNeighbors(new ArrayList<>(Arrays.asList(a, d, e)));
		d.setNeighbors(new ArrayList<>(Arrays.asList(c)));
		e.setNeighbors(new ArrayList<>(Arrays.asList(c, g)));
		f.setNeighbors(new ArrayList<>(Arrays.asList(b)));
		g.setNeighbors(new ArrayList<>(Arrays.asList(b,e)));
		map.add(a);
		map.add(b);
		map.add(c);
		map.add(d);
		map.add(e);
		map.add(f);
		map.add(g);
		List<Node> best_path = new ArrayList<Node>();
		best_path.add(a);
		best_path.add(b);
		best_path.add(g);
		AStar astar = new AStar(defaultSettings);
		List<Node> path = astar.findPath(a, g, map);
		assertEquals(best_path, path);
	}

	@Test
	public void test_mcc_3() { // Test for disconnected node
		Settings defaultSettings = new Settings(false, false, false);
		List<Node> map = new ArrayList<Node>();
		Node a = new Node(0, 0, "A");
		Node b = new Node(1, 0, "B");
		Node c = new Node(2, 0, "C");
		Node d = new Node(3, 0, "D");
		Node e = new Node(4, 0, "E");
		Node f = new Node(5, 0, "F");
		Node g = new Node(6, 0, "G");
		// Goal = g; Start = A
		a.setNeighbors(new ArrayList<>(Arrays.asList(b, c)));
		b.setNeighbors(new ArrayList<>(Arrays.asList(a, f)));
		c.setNeighbors(new ArrayList<>(Arrays.asList(a, d, e)));
		d.setNeighbors(new ArrayList<>(Arrays.asList(c)));
		e.setNeighbors(new ArrayList<>(Arrays.asList(c, g)));
		f.setNeighbors(new ArrayList<>(Arrays.asList(b)));
		g.setNeighbors(new ArrayList<>(Arrays.asList(b,e)));
		map.add(a);
		map.add(b);
		map.add(c);
		map.add(d);
		map.add(e);
		map.add(f);
		map.add(g);
		List<Node> best_path = new ArrayList<Node>();
		best_path.add(a);
		best_path.add(c);
		best_path.add(e);
		best_path.add(g);
		AStar astar = new AStar(defaultSettings);
		List<Node> path = astar.findPath(a, g, map);
		assertEquals(best_path, path);
	}

	@Test
	public void test_mcc_4() { // Test for disconnected node
		Settings defaultSettings = new Settings(false, false, false);
		List<Node> map = new ArrayList<Node>();
		Node a = new Node(0, 0, "A");
		Node b = new Node(1, 0, "B");
		// Goal = B; Start = A
		a.setNeighbors(new ArrayList<>(Arrays.asList()));//No connections
		b.setNeighbors(new ArrayList<>(Arrays.asList()));//No connections
		map.add(a);
		map.add(b);
		List<Node> best_path = new ArrayList<Node>();
		AStar astar = new AStar(defaultSettings);
		List<Node> path = astar.findPath(a, b, map);
		assertEquals(best_path, path);
		//
	}

	@Test
	public void test_tsl_1() {
		Settings defaultSettings = new Settings(false, false, false);
		List<Node> map = new ArrayList<Node>();
		Node n1 = new Node(0, 0, "Node 1");
		Node n2 = new Node(1, 0, "Node 2");
		Node n3 = new Node(2, 0, "Node 3");
		Node n4 = new Node(0, 1, "Node 4");
		Node n5 = new Node(1, 1, "Node 5");
		Node n6 = new Node(2, 1, "Node 6");
		Node n7 = new Node(0, 2, "Node 7");
		Node n8 = new Node(1, 2, "Node 8");
		Node n9 = new Node(2, 2, "Node 9");
		
		n1.setNeighbors(Arrays.asList(n2,n4,n5));
		n2.setNeighbors(Arrays.asList(n1,n5, n4, n6, n3));
		n5.setNeighbors(Arrays.asList(n1,n2,n3,n6, n9, n8, n7, n4));
		n4.setNeighbors(Arrays.asList(n1,n2,n5, n8, n7));
		n7.setNeighbors(Arrays.asList(n5, n8, n4));
		n8.setNeighbors(Arrays.asList(n6, n9,n5, n7, n4));
		n9.setNeighbors(Arrays.asList(n6, n5, n8));
		n6.setNeighbors(Arrays.asList(n2,n3, n9, n8, n5));
		n3.setNeighbors(Arrays.asList(n2,n5,n6));


		map.add(n1);
		map.add(n2);
		map.add(n3);
		map.add(n4);
		map.add(n5);
		map.add(n6);
		map.add(n7);
		map.add(n8);
		map.add(n9);
		
		List<Node> best_path = new ArrayList<Node>();
		best_path.add(n1);
		best_path.add(n5);
		best_path.add(n9);
		
		AStar astar = new AStar(defaultSettings);
		List<Node> path = astar.findPath(n1, n9, map);
		assertEquals(best_path, path);
	}

	@Test
	public void test_bg_1() {
		Settings defaultSettings = new Settings(false, false, false);
		List<Node> map = new ArrayList<Node>();
		Node n1 = new Node(0, 0, "Node 1");
		Node n2 = new Node(1, 0, "Node 2");
		Node n3 = new Node(2, 0, "Node 3");
		Node n4 = new Node(0, 1, "Node 4");
		Node n5 = new Node(1, 1, "Node 5");
		Node n6 = new Node(2, 1, "Node 6");
		Node n7 = new Node(0, 2, "Node 7");
		Node n8 = new Node(1, 2, "Node 8");
		Node n9 = new Node(2, 2, "Node 9");
		
		n1.setNeighbors(Arrays.asList(n2,n4,n5));
		n2.setNeighbors(Arrays.asList(n1,n5, n4, n6, n3));
		n5.setNeighbors(Arrays.asList(n1,n2,n3,n6, n9, n8, n7, n4));
		n4.setNeighbors(Arrays.asList(n1,n2,n5, n8, n7));
		n7.setNeighbors(Arrays.asList(n5, n8, n4));
		n8.setNeighbors(Arrays.asList(n6, n9,n5, n7, n4));
		n9.setNeighbors(Arrays.asList(n6, n5, n8));
		n6.setNeighbors(Arrays.asList(n2,n3, n9, n8, n5));
		n3.setNeighbors(Arrays.asList(n2,n5,n6));


		map.add(n1);
		map.add(n2);
		map.add(n3);
		map.add(n4);
		map.add(n5);
		map.add(n6);
		map.add(n7);
		map.add(n8);
		map.add(n9);
		List<Node> best_path = new ArrayList<Node>();
		best_path.add(n3);
		best_path.add(n5);
		best_path.add(n7);
		AStar astar = new AStar(defaultSettings);
		List<Node> path = astar.findPath(n3, n7, map);
		assertEquals(best_path, path);
	}
	
	/*
	 * Test map importing and running AStar on the map.
	 */
//	@Test
//	public void mapTest1()
//	{
//		List<Node> nodes=Main.getNodesFromFile("src/res/Alonso_Node_Map.csv");
//		String edgePath = "src/res/Alonso_Edge_Map.csv";
//		Main.connectEdgesFromFile(nodes, edgePath);
//		List<Node> path = Main.getPathFromNode(nodes.get(3), nodes.get(1), nodes);
//		// System.out.println(path);
//		List<Node> bestPath = new ArrayList<Node>();
//		bestPath.add(nodes.get(3));
//		bestPath.add(nodes.get(2));
//		bestPath.add(nodes.get(1));
//		assertEquals(bestPath, path);
//	}
	
//	@Test
//	public void mapTest2()
//	{
//		String nodePath = "src/res/Node_Map_MARS.csv";
//		String edgePath = "src/res/Edge_Map_MARS.csv";
//		List<Node> map = Main.getNodesFromFile(nodePath);
//		List<Node> path = Main.getPathFromNode(map.get(6), map.get(1), map);
//		// System.out.println(path);
//		List<Node> bestPath = new ArrayList<Node>();
//		bestPath.add(map.get(6));
//		bestPath.add(map.get(5));
//		bestPath.add(map.get(0));
//		bestPath.add(map.get(1));
//		assertEquals(bestPath, path);
//	}
//	
//	@Test
//	public void mapTest3()
//	{
//		String nodePath = "src/res/Node_Map_TSL.csv";
//		String edgePath = "src/res/Edge_Map_TSL.csv";
//		List<Node> map = Main.getNodesFromFile(nodePath);
//		List<Node> path = Main.getPathFromNode(map.get(0), map.get(4), map);
//		// System.out.println(path);
//		List<Node> bestPath = new ArrayList<Node>();
//		bestPath.add(map.get(0));
//		bestPath.add(map.get(1));
//		bestPath.add(map.get(4));
//		assertEquals(bestPath, path);
//	}
//	
	
	
	/*
	 * Sample test for exceptions.
	 */
	
	// @Test(expected = ValueOutOfBoundsException.class)
	// public void test10()throws ValueOutOfBoundsException{ //Test out of
	// bounds execption where input is beyond maximum value
	//
	// }
//	
//	@Test
//	public void testForGetScaleFromFile() {
//		List<Integer> n1 = Main.getScaleFromFile("./src/res/mapScale.csv");
//		List<Integer> n2 = new ArrayList<Integer>();
//		n2.add(18);
//		n2.add(13);
//		assertEquals(n1, n2);
//	}


}