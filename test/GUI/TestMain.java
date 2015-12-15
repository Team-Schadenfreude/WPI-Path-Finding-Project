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
		a.neighbors = new ArrayList<>(Arrays.asList(c));
		b.neighbors = new ArrayList<>(Arrays.asList(c));
		c.neighbors = new ArrayList<>(Arrays.asList(b, a));
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
		a.neighbors = new ArrayList<>(Arrays.asList(b, c));
		b.neighbors = new ArrayList<>(Arrays.asList(a, f, g));
		c.neighbors = new ArrayList<>(Arrays.asList(a, d, e));
		d.neighbors = new ArrayList<>(Arrays.asList(c));
		e.neighbors = new ArrayList<>(Arrays.asList(c, g));
		f.neighbors = new ArrayList<>(Arrays.asList(b));
		g.neighbors = new ArrayList<>(Arrays.asList(b,e));
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
		a.neighbors = new ArrayList<>(Arrays.asList(b, c));
		b.neighbors = new ArrayList<>(Arrays.asList(a, f));
		c.neighbors = new ArrayList<>(Arrays.asList(a, d, e));
		d.neighbors = new ArrayList<>(Arrays.asList(c));
		e.neighbors = new ArrayList<>(Arrays.asList(c, g));
		f.neighbors = new ArrayList<>(Arrays.asList(b));
		g.neighbors = new ArrayList<>(Arrays.asList(b,e));
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
		a.neighbors = new ArrayList<>(Arrays.asList());//No connections
		b.neighbors = new ArrayList<>(Arrays.asList());//No connections
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
		
		n1.neighbors =Arrays.asList(n2,n4,n5);
		n2.neighbors =Arrays.asList(n1,n5, n4, n6, n3);
		n5.neighbors =Arrays.asList(n1,n2,n3,n6, n9, n8, n7, n4);
		n4.neighbors =Arrays.asList(n1,n2,n5, n8, n7);
		n7.neighbors =Arrays.asList(n5, n8, n4);
		n8.neighbors =Arrays.asList(n6, n9,n5, n7, n4);
		n9.neighbors =Arrays.asList(n6, n5, n8);
		n6.neighbors =Arrays.asList(n2,n3, n9, n8, n5);
		n3.neighbors =Arrays.asList(n2,n5,n6);


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
		
		n1.neighbors =Arrays.asList(n2,n4,n5);
		n2.neighbors =Arrays.asList(n1,n5, n4, n6, n3);
		n5.neighbors =Arrays.asList(n1,n2,n3,n6, n9, n8, n7, n4);
		n4.neighbors =Arrays.asList(n1,n2,n5, n8, n7);
		n7.neighbors =Arrays.asList(n5, n8, n4);
		n8.neighbors =Arrays.asList(n6, n9,n5, n7, n4);
		n9.neighbors =Arrays.asList(n6, n5, n8);
		n6.neighbors =Arrays.asList(n2,n3, n9, n8, n5);
		n3.neighbors =Arrays.asList(n2,n5,n6);


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
	@Test
	public void mapTest1()
	{
		List<Node> nodes=Main.getNodesFromFile("src/res/Alonso_Node_Map.csv");
		String edgePath = "src/res/Alonso_Edge_Map.csv";
		Main.connectEdgesFromFile(nodes, edgePath);
		List<Node> path = Main.getPathFromNode(nodes.get(3), nodes.get(1), nodes);
		// System.out.println(path);
		List<Node> bestPath = new ArrayList<Node>();
		bestPath.add(nodes.get(3));
		bestPath.add(nodes.get(2));
		bestPath.add(nodes.get(1));
		assertEquals(bestPath, path);
	}
	
	@Test
	public void mapTest2()
	{
		String nodePath = "src/res/Node_Map_MARS.csv";
		String edgePath = "src/res/Edge_Map_MARS.csv";
		List<Node> map = Main.getNodesFromFile(nodePath);
		List<Node> path = Main.getPathFromNode(map.get(6), map.get(1), map);
		// System.out.println(path);
		List<Node> bestPath = new ArrayList<Node>();
		bestPath.add(map.get(6));
		bestPath.add(map.get(5));
		bestPath.add(map.get(0));
		bestPath.add(map.get(1));
		assertEquals(bestPath, path);
	}
	
	@Test
	public void mapTest3()
	{
		String nodePath = "src/res/Node_Map_TSL.csv";
		String edgePath = "src/res/Edge_Map_TSL.csv";
		List<Node> map = Main.getNodesFromFile(nodePath);
		List<Node> path = Main.getPathFromNode(map.get(0), map.get(4), map);
		// System.out.println(path);
		List<Node> bestPath = new ArrayList<Node>();
		bestPath.add(map.get(0));
		bestPath.add(map.get(1));
		bestPath.add(map.get(4));
		assertEquals(bestPath, path);
	}
	
	/*
	 * Tests for the getAngle method used for finding directions.
	 */
	
	@Test
	public void getAngleTest1()
	{
		Node n1 = new Node(0, 0, "Node 1");
		Node n2 = new Node(2, 2, "Node 2");
		int testAngle = Main.getAngle(n1, n2);
		assertEquals(45, testAngle);
	}
	
	@Test
	public void getAngleTest2()
	{
		Node n1 = new Node(0, 0, "Node 1");
		Node n2 = new Node(12, 22, "Node 2");
		int testAngle = Main.getAngle(n1, n2);
		assertEquals(61, testAngle);
	}
	
	@Test
	public void getAngleTest3()
	{
		Node n1 = new Node(24, 5, "Node 1");
		Node n2 = new Node(28, 25, "Node 2");
		int testAngle = Main.getAngle(n1, n2);
		assertEquals(78, testAngle);
	}
	
	@Test
	public void getAngleTest4()
	{
		Node n1 = new Node(3, 5, "Node 1");
		Node n2 = new Node(17, 10, "Node 2");
		int testAngle = Main.getAngle(n1, n2);
		assertEquals(19, testAngle);
	}
	
	/*
	 * Tests for the getDirectionList method.
	 */
	
	@Test
	public void directionTest1()
	{
		List<Node> path = new ArrayList<Node>();
		Node n1 = new Node(5, 2, "Node 1");
		Node n2 = new Node(5, 5, "Node 2");
		Node n3 = new Node(8, 5, "Node 3");
		path.add(n1);
		path.add(n2);
		path.add(n3);
		List<String> testDirections = new ArrayList<String>();
		testDirections = Main.getDirectionsList(path,1,1);
		// System.out.println("Path and Directions:");
		// System.out.println(path);
		// System.out.println(testDirections);
		List<String> solution = new ArrayList<String>();
		solution.add("Go Straight 3 ft");
		solution.add("Left turn 3 ft");
		solution.add("Procede to Destination");
		solution.add("Total Distance is 6ft");
		
		assertEquals(solution, testDirections);
	}
	
	@Test
	public void directionTest2()
	{
		List<Node> path = new ArrayList<Node>();
		Node n1 = new Node(5, 2, "Node 1");
		Node n2 = new Node(5, 5, "Node 2");
		Node n3 = new Node(2, 5, "Node 3");
		path.add(n1);
		path.add(n2);
		path.add(n3);
		List<String> testDirections = new ArrayList<String>();
		testDirections = Main.getDirectionsList(path,1,1);
		// System.out.println("Path and Directions:");
		// System.out.println(path);
		// System.out.println(testDirections);
		List<String> solution = new ArrayList<String>();
		solution.add("Go Straight 3 ft");
		solution.add("Right turn 3 ft");
		solution.add("Procede to Destination");
		solution.add("Total Distance is 6ft");
		
		assertEquals(solution, testDirections);
	}
	
	@Test
	public void directionTest3()
	{
		List<Node> path = new ArrayList<Node>();
		Node n1 = new Node(10, 0, "Node 1");
		Node n2 = new Node(10, 4, "Node 2");
		Node n3 = new Node(7, 1, "Node 3");
		Node n4 = new Node(4, 4, "Node 4");
		Node n5 = new Node(12, 10, "Node 5");
		Node n6 = new Node(12, 6, "Node 6");
		path.add(n1);
		path.add(n2);
		path.add(n3);
		path.add(n4);
		path.add(n5);
		path.add(n6);
		List<String> testDirections = new ArrayList<String>();
		testDirections = Main.getDirectionsList(path,1,1);
		// System.out.println("Path and Directions:");
		// System.out.println(path);
		// System.out.println(testDirections);
		List<String> solution = new ArrayList<String>();
		
		solution.add("Go Straight 4 ft");
		solution.add("Sharp right turn 4 ft");
		solution.add("Left turn 4 ft");
		solution.add("Left turn 10 ft");
		solution.add("Sharp left turn 4 ft");
		solution.add("Procede to Destination");
		solution.add("Total Distance is 26ft");
		assertEquals(solution, testDirections);
	}
	
	@Test
	public void directionTest4()
	{
		List<Node> path = new ArrayList<Node>();
		Node n1 = new Node(0, 0, "Node 1");
		Node n2 = new Node(0, 3, "Node 2");
		Node n3 = new Node(2, 5, "Node 3");
		Node n4 = new Node(2, 3, "Node 4");
		Node n5 = new Node(4, 0, "Node 5");
		Node n6 = new Node(4, 3, "Node 6");
		path.add(n1);
		path.add(n2);
		path.add(n3);
		path.add(n4);
		path.add(n5);
		path.add(n6);
		List<String> testDirections = new ArrayList<String>();
		testDirections = Main.getDirectionsList(path,1,1);
//		System.out.println("Path and Directions:");
//		System.out.println(path);
//		System.out.println(testDirections);
		
		List<String> solution = new ArrayList<String>();
		solution.add("Go Straight 3 ft");
		solution.add("Slight left turn 2 ft");
		solution.add("Sharp left turn 2 ft");
		solution.add("Slight right turn 3 ft");
		solution.add("Sharp right turn 3 ft");
		solution.add("Procede to Destination");
		solution.add("Total Distance is 14ft");
		assertEquals(solution, testDirections);
	}
	
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