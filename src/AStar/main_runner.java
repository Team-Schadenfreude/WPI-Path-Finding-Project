package AStar;

import AStar.Settings;
import AStar.TestJunit;
import AStar.Node;
import AStar.AStar;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import AStar.NodeList;

public class main_runner {

	private static Settings defaultSettings = new Settings(false, false, false);
	private static NodeList nlist = new NodeList();
	private static List<Node> map = new ArrayList<Node>();
	
	public static void main(String[] args){
		Result result = JUnitCore.runClasses(TestJunit.class);
	      for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	      }
	      System.out.println(result.wasSuccessful());
	      
	    Scanner reader = new Scanner(System.in);
		System.out.println("Enter your start location: ");
		String startLoc = reader.next();
		System.out.println("Enter your destinationn: ");
		String dest = reader.next();
		reader.close();
		
		getPathFromString(startLoc, dest);
	}
	
	//Method to find the path given a start node and an end node.
	public static List<Node> getPathFromNode(Node startNode, Node endNode)
	{
		AStar astar = new AStar(map, defaultSettings);
		return astar.findPath(startNode, endNode);
	}
	//Method to find path when given a string 
	public static List<Node> getPathFromString(String startName, String destName)
	{
		Node startNode = nlist.findNode(startName);
		Node destNode = nlist.findNode(destName);
		return getPathFromNode(startNode, destNode);
		//drawPath(path);
	}
	
	//Method to draw a path on a map, given said path.
	public static void drawPath(List<Node> path)
	{
		
	}
	
}
