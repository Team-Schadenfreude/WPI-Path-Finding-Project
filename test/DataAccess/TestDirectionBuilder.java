package DataAccess;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import AStar.Node;
import AStar.Node.Type;
import GUI.Main;

public class TestDirectionBuilder {

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
		n1.setType(Type.ROOM);
		n2.setType(Type.ROOM);
		n3.setType(Type.ROOM);
		n1.setMap("");
		n2.setMap("");
		n3.setMap("");
		path.add(n1);
		path.add(n2);
		path.add(n3);
		
		List<String> testDirections = new ArrayList<String>();
		testDirections = DirectionBuilder.getDirectionsList(path,1,1);
		
		
		// System.out.println("Path and Directions:");
		// System.out.println(path);
		// System.out.println(testDirections);
		List<String> solution = new ArrayList<String>();
		solution.add("Take a straight.");
		solution.add("Go straight out of Node 1 and into Node 2. (3.0 ft)");
		solution.add("Take a left turn.");
		solution.add("Go straight out of Node 2 and into Node 3. (3.0 ft)");
		solution.add("You have reached your destination after 6 ft.");
		
		assertEquals(solution, testDirections);
	}
	
	@Test
	public void directionTest2()
	{
		List<Node> path = new ArrayList<Node>();
		Node n1 = new Node(5, 2, "Node 1");
		Node n2 = new Node(5, 5, "Node 2");
		Node n3 = new Node(2, 5, "Node 3");
		n1.setType(Type.ROOM);
		n2.setType(Type.ROOM);
		n3.setType(Type.ROOM);
		n1.setMap("");
		n2.setMap("");
		n3.setMap("");
		path.add(n1);
		path.add(n2);
		path.add(n3);
		List<String> testDirections = new ArrayList<String>();
		testDirections = DirectionBuilder.getDirectionsList(path,1,1);
		// System.out.println("Path and Directions:");
		// System.out.println(path);
		// System.out.println(testDirections);
		
		List<String> solution = new ArrayList<String>();
		solution.add("Take a straight.");
		solution.add("Go straight out of Node 1 and into Node 2. (3.0 ft)");
		solution.add("Take a right turn.");
		solution.add("Go straight out of Node 2 and into Node 3. (3.0 ft)");
		solution.add("You have reached your destination after 6 ft.");
		
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
		n1.setType(Type.ROOM);
		n2.setType(Type.ROOM);
		n3.setType(Type.ROOM);
		n4.setType(Type.ROOM);
		n5.setType(Type.ROOM);
		n6.setType(Type.ROOM);
		n1.setMap("");
		n2.setMap("");
		n3.setMap("");
		n4.setMap("");
		n5.setMap("");
		n6.setMap("");
		path.add(n1);
		path.add(n2);
		path.add(n3);
		path.add(n4);
		path.add(n5);
		path.add(n6);
		List<String> testDirections = new ArrayList<String>();
		testDirections = DirectionBuilder.getDirectionsList(path,1,1);
		
		// System.out.println("Path and Directions:");
		// System.out.println(path);
		// System.out.println(testDirections);
		List<String> solution = new ArrayList<String>();
		
		solution.add("Take a straight.");
		solution.add("Go straight out of Node 1 and into Node 2. (4.0 ft)");
		solution.add("Take a sharp right turn.");
		solution.add("Go straight out of Node 2 and into Node 3. (4.242640687119285 ft)");
		solution.add("Take a left turn.");
		solution.add("Go straight out of Node 3 and into Node 4. (4.242640687119285 ft)");
		solution.add("Take a left turn.");
		solution.add("Go straight out of Node 4 and into Node 5. (10.0 ft)");
		solution.add("Take a sharp left turn.");
		solution.add("Go straight out of Node 5 and into Node 6. (4.0 ft)");
		solution.add("You have reached your destination after 26 ft.");
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
		n1.setType(Type.ROOM);
		n2.setType(Type.ROOM);
		n3.setType(Type.ROOM);
		n4.setType(Type.ROOM);
		n5.setType(Type.ROOM);
		n6.setType(Type.ROOM);
		n1.setMap("");
		n2.setMap("");
		n3.setMap("");
		n4.setMap("");
		n5.setMap("");
		n6.setMap("");
		path.add(n1);
		path.add(n2);
		path.add(n3);
		path.add(n4);
		path.add(n5);
		path.add(n6);
		List<String> testDirections = new ArrayList<String>();
		testDirections = DirectionBuilder.getDirectionsList(path,1,1);
		
		
//		System.out.println("Path and Directions:");
//		System.out.println(path);
//		System.out.println(testDirections);
		
		List<String> solution = new ArrayList<String>();
		solution.add("Take a straight.");
		solution.add("Go straight out of Node 1 and into Node 2. (3.0 ft)");
		solution.add("Take a slight left turn.");
		solution.add("Go straight out of Node 2 and into Node 3. (2.8284271247461903 ft)");
		solution.add("Take a sharp left turn.");
		solution.add("Go straight out of Node 3 and into Node 4. (2.0 ft)");
		solution.add("Take a slight right turn.");
		solution.add("Go straight out of Node 4 and into Node 5. (3.605551275463989 ft)");
		solution.add("Take a sharp right turn.");
		solution.add("Go straight out of Node 5 and into Node 6. (3.0 ft)");
		solution.add("You have reached your destination after 14 ft.");
		

		assertEquals(solution, testDirections);
	}
	
}
