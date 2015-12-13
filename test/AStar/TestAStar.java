package AStar;

import java.util.ArrayList;
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
		assertEquals(astar.getSettings(), null);
	}
	@Test
	public void testConstructorOfAStarWithParameters(){
		AStar astar = new AStar();
		List<Node> map = new ArrayList<Node>();
		Node n1 = new Node(0, 0, "A");
		Node n2 = new Node(1, 0, "B");
		Node n3 = new Node(2, 0, "C");
		map.add(n1);
		map.add(n2);
		map.add(n3);
		Settings defaultSettings = new Settings(false, false, false);
		astar.setSettings(defaultSettings);
		AStar astar_new = new AStar(defaultSettings);
		assertEquals(astar.getSettings(), astar_new.getSettings());
	}
	
}