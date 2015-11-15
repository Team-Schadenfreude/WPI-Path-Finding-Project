package AStar;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import AStar.Settings;
import AStar.AStar;
import AStar.main_runner;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class TestJunit {

	@Test
	public void test1() {
		assertEquals(1, 1);
	}

	@Test
	public void test_mcc_1() { // Test for horizontal nodes where path is curved
		Settings defaultSettings = new Settings(false, false, false);
		List<Node> map = new ArrayList<Node>();
		Node n1 = new Node(0, 0, "A");
		Node n2 = new Node(1, 0, "B");
		Node n3 = new Node(2, 0, "C");
		n1.neighbors = new ArrayList<>(Arrays.asList(new Edge(n3, AStar.getDistance(n1, n3))));
		n2.neighbors = new ArrayList<>(Arrays.asList(new Edge(n3, AStar.getDistance(n2, n3))));
		n3.neighbors = new ArrayList<>(Arrays.asList(new Edge(n2, AStar.getDistance(n2, n3)), new Edge(n1, AStar.getDistance(n1, n3))));
		map.add(n1);
		map.add(n2);
		map.add(n3);
		List<Node> best_path = new ArrayList<Node>();
		best_path.add(n1);
		best_path.add(n3);
		best_path.add(n2);
		AStar astar = new AStar(map, defaultSettings);
		List<Node> path = astar.findPath(n1, n2);
		assertEquals(best_path, path);
	}

	@Test
	public void test_mcc_2() { // Test for complex search
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
		a.neighbors = new ArrayList<>(Arrays.asList(new Edge(b, 1), new Edge(c, 3)));
		b.neighbors = new ArrayList<>(Arrays.asList(new Edge(a, 1), new Edge(d, 2), new Edge(e, 4)));
		c.neighbors = new ArrayList<>(Arrays.asList(new Edge(a, 3), new Edge(e, 1)));
		d.neighbors = new ArrayList<>(Arrays.asList(new Edge(b, 2)));
		e.neighbors = new ArrayList<>(Arrays.asList(new Edge(b, 4), new Edge(c, 1), new Edge(f, 5), new Edge(g, 6)));
		f.neighbors = new ArrayList<>(Arrays.asList(new Edge(e, 5)));
		g.neighbors = new ArrayList<>(Arrays.asList(new Edge(g, 6)));
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
		AStar astar = new AStar(map, defaultSettings);
		List<Node> path = astar.findPath(a, g);
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
		a.neighbors = new ArrayList<>(Arrays.asList(new Edge(b, 1)));
		b.neighbors = new ArrayList<>(Arrays.asList(new Edge(a, 1), new Edge(d, 2), new Edge(e, 4)));
		c.neighbors = new ArrayList<>(Arrays.asList()); // C does not connect to any nodes
		d.neighbors = new ArrayList<>(Arrays.asList(new Edge(b, 2)));
		e.neighbors = new ArrayList<>(Arrays.asList(new Edge(b, 4), new Edge(f, 5), new Edge(g, 6)));
		f.neighbors = new ArrayList<>(Arrays.asList(new Edge(e, 5)));
		g.neighbors = new ArrayList<>(Arrays.asList(new Edge(g, 6)));
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
		best_path.add(e);
		best_path.add(g);
		AStar astar = new AStar(map, defaultSettings);
		List<Node> path = astar.findPath(a, g);
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
		AStar astar = new AStar(map, defaultSettings);
		List<Node> path = astar.findPath(a, b);
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
		Node n10 = new Node(0, 3, "Node 10");
		Node n11 = new Node(1, 3, "Node 11");
		Node n12 = new Node(2, 3, "Node 12");
		n1.neighbors = new ArrayList<>(Arrays.asList(new Edge(n2, AStar.getDistance(n1, n2)), new Edge(n4, AStar.getDistance(n1, n4))));
		n2.neighbors = new ArrayList<>(Arrays.asList(new Edge(n1, AStar.getDistance(n1, n2)), new Edge(n5, AStar.getDistance(n2, n5))));
		n3.neighbors = new ArrayList<>(Arrays.asList(new Edge(n6, AStar.getDistance(n3, n6))));
		n4.neighbors = new ArrayList<>(Arrays.asList(new Edge(n1, AStar.getDistance(n1, n4)), new Edge(n7, AStar.getDistance(n4, n7))));
		n5.neighbors = new ArrayList<>(Arrays.asList(new Edge(n2, AStar.getDistance(n2, n5)), new Edge(n8, AStar.getDistance(n5, n8))));
		n6.neighbors = new ArrayList<>(Arrays.asList(new Edge(n3, AStar.getDistance(n3, n6)), new Edge(n9, AStar.getDistance(n6, n9))));
		n7.neighbors = new ArrayList<>(Arrays.asList(new Edge(n4, AStar.getDistance(n4, n7)),
				new Edge(n10, AStar.getDistance(n7, n10))));
		n8.neighbors = new ArrayList<>(Arrays.asList(new Edge(n5, AStar.getDistance(n5, n8)),
				new Edge(n11, AStar.getDistance(n8, n11))));
		n9.neighbors = new ArrayList<>(Arrays.asList(new Edge(n6, AStar.getDistance(n6, n9)),
				new Edge(n12, AStar.getDistance(n9, n12))));
		n10.neighbors = new ArrayList<>(Arrays.asList(new Edge(n7, AStar.getDistance(n7, n10)),
				new Edge(n11, AStar.getDistance(n10, n11))));
		n11.neighbors = new ArrayList<>(Arrays.asList(new Edge(n8, AStar.getDistance(n8, n11)),
				new Edge(n10, AStar.getDistance(n10, n11)), new Edge(n12, AStar.getDistance(n11, n12))));
		n12.neighbors = new ArrayList<>(Arrays.asList(new Edge(n9, AStar.getDistance(n9, n12)),
				new Edge(n11, AStar.getDistance(n11, n12))));
		map.add(n1);
		map.add(n2);
		map.add(n3);
		map.add(n4);
		map.add(n5);
		map.add(n6);
		map.add(n7);
		map.add(n8);
		map.add(n9);
		map.add(n10);
		map.add(n11);
		map.add(n12);
		List<Node> best_path = new ArrayList<Node>();
		best_path.add(n1);
		best_path.add(n2);
		best_path.add(n5);
		best_path.add(n8);
		best_path.add(n11);
		best_path.add(n12);
		best_path.add(n9);
		best_path.add(n6);
		best_path.add(n3);
		AStar astar = new AStar(map, defaultSettings);
		List<Node> path = astar.findPath(n1, n3);
		assertEquals(best_path, path);
	}

	@Test
	public void test_bg_1() {
		Settings defaultSettings = new Settings(false, false, false);
		List<Node> map = new ArrayList<Node>();
		Node d = new Node(0, 0, "D");
		Node e = new Node(1, 0, "E");
		Node f = new Node(2, 0, "F");
		Node g = new Node(2, 1, "G");
		Node h = new Node(3, 1, "H");
		Node i = new Node(1, -1, "I");
		d.neighbors = new ArrayList<>(Arrays.asList(new Edge(f, 2), new Edge(e, 1)));
		e.neighbors = new ArrayList<>(Arrays.asList(new Edge(i, 1),new Edge(d, 1), new Edge(f, 1)));
		f.neighbors = new ArrayList<>(Arrays.asList(new Edge(e, 1), new Edge(h, 2),new Edge(g,1), new Edge(d, 2)));
		h.neighbors = new ArrayList<>(Arrays.asList(new Edge(g, 1), new Edge(f, 2)));
		i.neighbors = new ArrayList<>(Arrays.asList(new Edge(e, 1)));
		g.neighbors = new ArrayList<>(Arrays.asList(new Edge(h,1), new Edge(f,1)));
		map.add(d);
		map.add(e);
		map.add(f);
		map.add(g);
		map.add(h);
		map.add(i);
		List<Node> best_path = new ArrayList<Node>();
		best_path.add(i);
		best_path.add(e);
		best_path.add(f);
		best_path.add(h);
		AStar astar = new AStar(map, defaultSettings);
		List<Node> path = astar.findPath(i, h);
		assertEquals(best_path, path);
	}
	
	@Test
	public void testDirectionRight()
	{
		Node n1 = new Node(0, 0, "Node 1");
		Node n2 = new Node(0, 1, "Node 2");
		Node n3 = new Node(1, 1, "Node 3");
		String testDirection = main_runner.getDirections(n1, n2, n3);
		assertEquals("Right", testDirection);
	}
	
	@Test
	public void testDirectionLeft()
	{
		Node n1 = new Node(3, 3, "Node 1");
		Node n2 = new Node(4, 3, "Node 2");
		Node n3 = new Node(4, 4, "Node 3");
		String testDirection = main_runner.getDirections(n1, n2, n3);
		assertEquals("Left", testDirection);
	}
	
	@Test
	public void getAngleTest1()
	{
		Node n1 = new Node(0, 0, "Node 1");
		Node n2 = new Node(2, 2, "Node 2");
		int testAngle = main_runner.getAngle(n1, n2);
		assertEquals(45, testAngle);
	}
	
	@Test
	public void getAngleTest2()
	{
		Node n1 = new Node(0, 0, "Node 1");
		Node n2 = new Node(12, 22, "Node 2");
		int testAngle = main_runner.getAngle(n1, n2);
		assertEquals(61, testAngle);
	}
	
	@Test
	public void getAngleTest3()
	{
		Node n1 = new Node(24, 5, "Node 1");
		Node n2 = new Node(28, 25, "Node 2");
		int testAngle = main_runner.getAngle(n1, n2);
		assertEquals(78, testAngle);
	}
	
	@Test
	public void getAngleTest4()
	{
		Node n1 = new Node(3, 5, "Node 1");
		Node n2 = new Node(17, 10, "Node 2");
		int testAngle = main_runner.getAngle(n1, n2);
		assertEquals(19, testAngle);
	}
	
	
	
	// @Test(expected = ValueOutOfBoundsException.class)
	// public void test10()throws ValueOutOfBoundsException{ //Test out of
	// bounds execption where input is beyond maximum value
	//
	// }

}