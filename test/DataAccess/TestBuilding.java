package DataAccess;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import AStar.Node;
import junit.framework.TestCase;

public class TestBuilding extends TestCase {
	Building b;

	public void setUp() {
		b = new Building("Higgins", "description");

	}

	public void testBuildingConstructor() {

		String desc = b.getDescription();
		String name = b.getName();
		assertEquals("Higgins", name);
		assertEquals("description", desc);

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
		Floor f1 = null;
		Floor f2 = null;
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
	@Test
	public void getAngleTest1()
	{
		b.setAngle(45);
		int testAngle = b.getAngle();
		assertEquals(45, testAngle);
	}
	
	@Test
	public void getAngleTest2()
	{
		b.setAngle(61);
		int testAngle = b.getAngle();
		assertEquals(61, testAngle);
	}
	
	

}
