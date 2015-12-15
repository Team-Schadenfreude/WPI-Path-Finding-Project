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

	public void testSetRooms() {
		List<Room> list = new ArrayList<Room>();
		list.add(new Room("1", new Node(1, 1, "")));
		f.setRoomList(list);
		List<Room> temp = f.getRoomList();
		assertEquals(list, temp);
	}

	public void testGetRooms() {
		List<Room> list = new ArrayList<Room>();
		list.add(new Room("1", new Node(1, 1, "")));
		f.setRoomList(list);
		List<Room> temp = f.getRoomList();
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
