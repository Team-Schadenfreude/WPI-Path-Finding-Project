package DataAccess;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class TestBuilding extends TestCase {
	Building b;

	public void setUp() {
		b = new Building("Higgins", "description", "8am-5pm");

	}

	public void testBuildingConstructor() {

		String desc = b.getDesc();
		String times = b.getTimes();
		String name = b.getName();
		assertEquals("Higgins", name);
		assertEquals("description", desc);
		assertEquals("8am-5pm", times);
	}

	public void testSetFloorsWithEmptyList() {
		boolean bool;
		List<Floor> f = null;
		bool = b.setFloors(f);
		assertEquals(false, bool);

	}

	public void testSetFloorWithListOfFloors() {
		boolean bool;
		List<Floor> list = new ArrayList<Floor>();
		Floor f1 =null;
		Floor f2 =null;
		bool = b.setFloors(list);
		assertEquals(true, bool);
	}

	public void testGetFloors() {
		List<Floor> list = new ArrayList<Floor>();
		Floor f1;
		Floor f2;
		b.setFloors(list);
		List<Floor> contr = b.getFloors();
		assertEquals(list, contr);
	}

}
