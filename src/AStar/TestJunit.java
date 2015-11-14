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
   public void test_mcc_1(){ //Test for horizontal nodes where path is curved
	   Settings defaultSettings = new Settings(false,false,false);
	   List<Node> map = new ArrayList<Node>();
	   Node n1 = new Node(0,0, "A");
	   Node n2 = new Node(1,0, "B");
	   Node n3 = new Node(2,0, "C");
	   n1.neighbors = new Edge[]{
				new Edge(n3,AStar.getDistance(n1,n3)),
		};
	   n2.neighbors = new Edge[]{
				new Edge(n3,AStar.getDistance(n2,n3))
		};
	   n3.neighbors = new Edge[]{
				new Edge(n2,AStar.getDistance(n2,n3)),
				new Edge(n1,AStar.getDistance(n1,n3))
		};
	   map.add(n1);
	   map.add(n2);
	   map.add(n3);
	   List<Node> best_path = new ArrayList<Node>();
	   best_path.add(n1);
	   best_path.add(n3);
	   best_path.add(n2);
	   AStar astar = new AStar(map,defaultSettings);
	   List<Node> path = astar.findPath(n1, n2);
	   assertEquals(best_path,path);
   }
   @Test
   public void test_mcc_2(){ //Test for complex search
	   Settings defaultSettings = new Settings(false,false,false);
	   List<Node> map = new ArrayList<Node>();
	   Node a = new Node(0,0, "A");
	   Node b = new Node(1,0, "B");
	   Node c = new Node(2,0, "C");
	   Node d = new Node(3,0, "D");
	   Node e = new Node(4,0, "E");
	   Node f = new Node(5,0, "F");
	   Node g = new Node(6,0, "G");
	   //Goal = g; Start = A
	   a.neighbors = new Edge[]{
				new Edge(b,1),
				new Edge(c,3)
		};
	   b.neighbors = new Edge[]{
				new Edge(a,1),
				new Edge(d,2),
				new Edge(e,4)
		};
	   c.neighbors = new Edge[]{
				new Edge(a,3),
				new Edge(e,1)
		};
	   d.neighbors = new Edge[]{
				new Edge(b,2)
		};
	   e.neighbors = new Edge[]{
				new Edge(b,4),
				new Edge(c,1),
				new Edge(f,5),
				new Edge(g,6)
		};
	   f.neighbors = new Edge[]{
				new Edge(e,5)
		};
	   g.neighbors = new Edge[]{
				new Edge(g,6)
		};
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
	   AStar astar = new AStar(map,defaultSettings);
	   List<Node> path = astar.findPath(a, g);
	   assertEquals(best_path,path);
   }
   @Test
   public void test_mcc_3(){ //Test for disconnected node
	   Settings defaultSettings = new Settings(false,false,false);
	   List<Node> map = new ArrayList<Node>();
	   Node a = new Node(0,0, "A");
	   Node b = new Node(1,0, "B");
	   Node c = new Node(2,0, "C");
	   Node d = new Node(3,0, "D");
	   Node e = new Node(4,0, "E");
	   Node f = new Node(5,0, "F");
	   Node g = new Node(6,0, "G");
	   //Goal = g; Start = A
	   a.neighbors = new Edge[]{
				new Edge(b,1)
		};
	   b.neighbors = new Edge[]{
				new Edge(a,1),
				new Edge(d,2),
				new Edge(e,4)
		};
	   c.neighbors = new Edge[]{ //C does not connect to any nodes
		};
	   d.neighbors = new Edge[]{
				new Edge(b,2)
		};
	   e.neighbors = new Edge[]{
				new Edge(b,4),
				new Edge(f,5),
				new Edge(g,6)
		};
	   f.neighbors = new Edge[]{
				new Edge(e,5)
		};
	   g.neighbors = new Edge[]{
				new Edge(g,6)
		};
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
	   AStar astar = new AStar(map,defaultSettings);
	   List<Node> path = astar.findPath(a, g);
	   assertEquals(best_path,path);
   }
   @Test
   public void test_mcc_4(){ //Test for disconnected node
	   Settings defaultSettings = new Settings(false,false,false);
	   List<Node> map = new ArrayList<Node>();
	   Node a = new Node(0,0, "A");
	   Node b = new Node(1,0, "B");
	   //Goal = B; Start = A
	   a.neighbors = new Edge[]{ //No connections
		};
	   b.neighbors = new Edge[]{ //No connections
		};
	   map.add(a);
	   map.add(b);
	   List<Node> best_path = new ArrayList<Node>();
	   AStar astar = new AStar(map,defaultSettings);
	   List<Node> path = astar.findPath(a, b);
	   assertEquals(best_path,path);
   }
//   @Test(expected = ValueOutOfBoundsException.class)
//   public void test10()throws ValueOutOfBoundsException{ //Test out of bounds execption where input is beyond maximum value
//	   
//   }
  
}