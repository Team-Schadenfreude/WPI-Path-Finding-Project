package DataAccess;

import java.util.ArrayList;
import java.util.List;

import AStar.Node;
import junit.framework.TestCase;

public class TestFloor extends TestCase {
	Floor f;

	public void setUp() {
		f = new Floor("Stratton Floor 1");
	}

	public void testSetNodes() {
		List<Node> list = new ArrayList<Node>();
		list.add(new Node(1, 1, ""));
		f.setNodes(list);
		List<Node> temp = f.getNodes();
		assertEquals(list, temp);
	}

	public void testGetNodes() {
		List<Node> list = new ArrayList<Node>();
		list.add(new Node(1, 1, ""));
		f.setNodes(list);
		List<Node> temp = f.getNodes();
		assertEquals(list, temp);
	}

	public void testSetName() {
		f.setName("Higgins");
		assertEquals("Higgins", f.getName());
	}

	public void testGetName() {
		String s = f.getName();
		assertEquals("Stratton Floor 1", s);
	}
}
