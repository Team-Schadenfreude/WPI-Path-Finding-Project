package AStar;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import AStar.Settings;
import AStar.AStar;
import java.util.List;
import java.util.ArrayList;
public class TestJunit{
	
   @Test
   public void test1() {
	   assertEquals(1,1);
   }
   
   @Test
   public void test_mcc_1(){
	   Settings defaultSettings = new Settings(false,false,false);
	   List<Node> map = new ArrayList<Node>();
	   Node n1 = new Node(0,0, "A");
	   Node n2 = new Node(1,0, "B");
	   Node n3 = new Node(2,0, "C");
	   n1.neighbors = new Edge[]{
				new Edge(n3,AStar.getDistance(n1,n3)),
		};
	   n2.neighbors = new Edge[]{
			   //new Edge(n1,AStar.getDistance(n1,n2)),
				new Edge(n3,AStar.getDistance(n2,n3))
		};
	   n3.neighbors = new Edge[]{
				new Edge(n2,AStar.getDistance(n2,n3)),
				new Edge(n1,AStar.getDistance(n1,n3))
		};
	   map.add(n1);
	   map.add(n2);
	   map.add(n3);
	   System.out.println(map);
	   AStar astar = new AStar(map,defaultSettings);
	   List<Node> path = astar.findPath(n1, n2);
	   System.out.println(path);
	   assertEquals(1,path);
   }
   
   @Test
   public void test_mcc_2(){
	   Settings defaultSettings = new Settings(false,false,false);
	   List<Node> map = new ArrayList<Node>();
	   Node n1 = new Node(0,0, "A");
	   Node n2 = new Node(1,0, "B");
	   Node n3 = new Node(2,0, "C");
	   n1.neighbors = new Edge[]{
				new Edge(n3,AStar.getDistance(n1,n3)),
		};
	   n2.neighbors = new Edge[]{
			   //new Edge(n1,AStar.getDistance(n1,n2)),
				new Edge(n3,AStar.getDistance(n2,n3))
		};
	   n3.neighbors = new Edge[]{
				new Edge(n2,AStar.getDistance(n2,n3)),
				new Edge(n1,AStar.getDistance(n1,n3))
		};
	   map.add(n1);
	   map.add(n2);
	   map.add(n3);
	   System.out.println(map);
	   AStar astar = new AStar(map,defaultSettings);
	   List<Node> path = astar.findPath(n1, n2);
	   System.out.println(path);
	   assertEquals(1,path);
   }
   
   @Test
   public void test_tsl_1(){
	   Settings defaultSettings = new Settings(false,false,false);
	   List<Node> map = new ArrayList<Node>();
	   Node n1 = new Node(0,0, "Node 1");
	   Node n2 = new Node(1,0, "Node 2");
	   Node n3 = new Node(2,0, "Node 3");
	   Node n4 = new Node(0,1, "Node 4");
	   Node n5 = new Node(1,1, "Node 5");
	   Node n6 = new Node(2,1, "Node 6");
	   Node n7 = new Node(0,2, "Node 7");
	   Node n8 = new Node(1,2, "Node 8");
	   Node n9 = new Node(2,2, "Node 9");
	   Node n10 = new Node(0,3, "Node 10");
	   Node n11 = new Node(1,3, "Node 11");
	   Node n12 = new Node(2,3, "Node 12");
	   n1.neighbors = new Edge[]{
				new Edge(n2,AStar.getDistance(n1,n2)),
				new Edge(n4,AStar.getDistance(n1,n4))
		};
	   n2.neighbors = new Edge[]{
			   	new Edge(n1,AStar.getDistance(n1,n2)),
				new Edge(n5,AStar.getDistance(n2,n5))
		};
	   n3.neighbors = new Edge[]{
				new Edge(n6,AStar.getDistance(n3,n6)),
		};
	   n4.neighbors = new Edge[]{
				new Edge(n1,AStar.getDistance(n1,n4)),
				new Edge(n7,AStar.getDistance(n4,n7))
		};
	   n5.neighbors = new Edge[]{
				new Edge(n2,AStar.getDistance(n2,n5)),
				new Edge(n8,AStar.getDistance(n5,n8))
		};
	   n6.neighbors = new Edge[]{
				new Edge(n3,AStar.getDistance(n3,n6)),
				new Edge(n9,AStar.getDistance(n6,n9))
		};
	   n7.neighbors = new Edge[]{
				new Edge(n4,AStar.getDistance(n4,n7)),
				new Edge(n10,AStar.getDistance(n7,n10))
		};
	   n8.neighbors = new Edge[]{
				new Edge(n5,AStar.getDistance(n5,n8)),
				new Edge(n11,AStar.getDistance(n8,n11))
		};
	   n9.neighbors = new Edge[]{
				new Edge(n6,AStar.getDistance(n6,n9)),
				new Edge(n12,AStar.getDistance(n9,n12))
		};
	   n10.neighbors = new Edge[]{
				new Edge(n7,AStar.getDistance(n7,n10)),
				new Edge(n11,AStar.getDistance(n10,n11))
		};
	   n11.neighbors = new Edge[]{
				new Edge(n8,AStar.getDistance(n8,n11)),
				new Edge(n10,AStar.getDistance(n10,n11)),
				new Edge(n12,AStar.getDistance(n11,n12))
		};
	   n12.neighbors = new Edge[]{
				new Edge(n9,AStar.getDistance(n9,n12)),
				new Edge(n11,AStar.getDistance(n11,n12))
		};
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
	   System.out.println(map);
	   AStar astar = new AStar(map,defaultSettings);
	   List<Node> path = astar.findPath(n1, n3);
	   System.out.println(path);
	   assertEquals(1,path);
   }
   
   
//   @Test(expected = ValueOutOfBoundsException.class)
//   public void test10()throws ValueOutOfBoundsException{ //Test out of bounds execption where input is beyond maximum value
//	   
//   }
  
}