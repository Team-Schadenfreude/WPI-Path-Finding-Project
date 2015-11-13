package AStar;

import java.lang.String;
import java.util.Scanner;

public class Main {
	public void main() {
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter your start location: ");
		String startLoc = reader.next();
		System.out.println("Enter your destinationn: ");
		String dest = reader.next();
		
		reader.close();
		
		NodeList nlist = new NodeList();
		Node startNode = nlist.findNode(startLoc);
		Node destNode = nlist.findNode(dest);
	}
}
