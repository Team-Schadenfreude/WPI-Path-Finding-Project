package AStar;

import AStar.Settings;
import AStar.TestJunit;
import AStar.Node;
import java.util.List;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class main_runner {

	Settings defaultSettings = new Settings(false, false, false);
	
	public static void main(String[] args){
		Result result = JUnitCore.runClasses(TestJunit.class);
	      for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	      }
	      System.out.println(result.wasSuccessful());
	}
	
	//Method to find a node given a location.
	public static Node findNodeAtLocation(String key)
	{
		return null;
	}
	
	//Method to find the path given a start node and an end node.
	public static List<Node> getPath(Node startNode, Node endNode)
	{
		return null;
	}
	
	//Method to draw a path on a map, given said path.
	public static void drawPath(List<Node> path)
	{
		
	}
	
}
