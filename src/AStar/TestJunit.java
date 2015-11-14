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
   
   
//   @Test(expected = ValueOutOfBoundsException.class)
//   public void test10()throws ValueOutOfBoundsException{ //Test out of bounds execption where input is beyond maximum value
//	   
//   }
  
}