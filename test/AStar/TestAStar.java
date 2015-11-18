package AStar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import junit.framework.TestCase;


public class TestAStar extends TestCase{
	/*
	 * Tests for running AStar.
	 */
	@Test
	public void testConstructorOfAStarWithNoParameters(){
		AStar astar = new AStar();
		assertEquals(astar.getMap(), null);
		assertEquals(astar.getSettings(), null);
	}
	@Test
	public void testConstructorOfAStarWithParameters(){
		AStar astar = new AStar();
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
		Settings defaultSettings = new Settings(false, false, false);
		astar.setMap(map);
		astar.setSettings(defaultSettings);
		AStar astar_new = new AStar(map, defaultSettings);
		assertEquals(astar.getMap(), astar_new.getMap());
		assertEquals(astar.getSettings(), astar_new.getSettings());
	}




	
}