package DataAccess;

import java.util.LinkedList;
import java.util.List;

import AStar.Node;
import javafx.geometry.NodeOrientation;

public class DirectionBuilder {

	
	public DirectionBuilder() {
		// TODO Auto-generated constructor stub
	}

	//Method to provide a list of directions from a list of nodes.
	public static List<String> getDirectionsList(List<Node> path, double xScale, double yScale)
	{
		
		List<String> directionsList = new LinkedList<String>();
		
		if(path.size() == 0)
		{
			directionsList.add("There is no path to follow");
		}
		else
		{
			double totalDistance = 0;
			double distance = 0;
			int prevAngle = 0;
			int currentAngle = 0;
			int prevDirVal = 0;
			int dirVal = 0;
			int deltaAngle = 0;
			boolean mapChange = false;
			System.out.println(path);
						
			for(int i = 0; i < path.size() -1 ; i++)				
			{
				Node n1 = path.get(i);
				Node n2 = path.get(i+1);
				
				// Check node type.
				// System.out.println(n1.getType());
				
				if (n1.isTransition() && n2.isTransition() && !n1.getMap().equals(n2.getMap()))
				{
					
					//String direction = "Procede into " + n2.map;
					totalDistance += distance;
					String direction = getStringFromDirectionValue(prevDirVal);
					directionsList.add(direction + " " + Integer.toString((int)distance) + " ft");
					directionsList.add("Proceed into " + n2.getMap());
					distance = 0;
					prevDirVal = 0;
					prevAngle = 0;
					currentAngle = 0;
					prevDirVal = 0;
					dirVal = 0;
					deltaAngle = 0;
					mapChange = true;
					
				}
				
				else 
				{
					// Is the intersection found?
					// System.out.println("INTERSECTION FOUND");
					
					// Finds angle between two nodes. 
					currentAngle = getAngle(n1, n2);
					
					// Sets the difference between the angles.
					deltaAngle = 0;
					deltaAngle = currentAngle - prevAngle;
					
					// Translates the angles to degrees.
					double delta_angle_rad = (Math.PI / 180) * (double) deltaAngle;
					
					// Bind angle to range [-180,180]
					deltaAngle = (int) ((180 / Math.PI) * Math.atan2(Math.sin(delta_angle_rad), Math.cos(delta_angle_rad)));
					
					// Direction from the deltaAngle
					dirVal = getDirectionValueFromAngle(deltaAngle);
					
					// Reset variables if map changes.
					if (i == 0 || mapChange)
					{
						deltaAngle = 0;
						dirVal = 0;
						mapChange = false;
					}
					
					// If direction exists then set the direction.
					if (n1.getType() == Node.Type.INTERSECTION && n2.getType() == Node.Type.ROOM)
					{
						
						totalDistance += distance;
						String direction = getStringFromDirectionValue(prevDirVal);
						System.out.println(direction);
						directionsList.add("Take a " + direction);
						directionsList.add("Go straight into " + n2.getName() + " after " + Integer.toString((int)distance) + " ft");

					}
					
					// If direction exists then set the direction.
					if (n1.getType() == Node.Type.ROOM && n2.getType() == Node.Type.INTERSECTION)
					{
						
						totalDistance += distance;
						String direction = getStringFromDirectionValue(prevDirVal);
						directionsList.add("Go straight out of " + n1.getName() + " for " + Integer.toString((int)distance) + " ft");

					}			
					
					// If direction exists then set the direction.
					else if (dirVal != 0)
					{
						totalDistance += distance;
						String direction = getStringFromDirectionValue(prevDirVal);
						directionsList.add(direction + " " + Integer.toString((int)distance) + " ft");
						distance = 0;
						prevDirVal = dirVal;
					}
					
					
					prevAngle = currentAngle;
					distance = n1.distanceTo(n2);
					
				}
				
				
			}
			
			// Setup final directions.
			totalDistance += distance;
			String direction = getStringFromDirectionValue(prevDirVal);
			directionsList.add("You have reached your destination.");
			directionsList.add("Total distance is " + Integer.toString((int)totalDistance) + "ft.");
		}
		
		// Print final directions.
		return directionsList;
	}
	
	private static int getDirectionValueFromAngle(int angle)
	{
		if (-20 < angle && angle < 20)//Going Straight
		{
			return 0;
		}
		else if (20 <= angle && angle < 60)
		{
			return 1;
		}
		else if (60 <= angle && angle < 120)
		{
			return 2;
		}
		else if (120 < angle && angle <= 180)
		{
			return 3;
		}
		else if (-60 < angle && angle <= -20)
		{
			return 4;
		}
		else if (-120 < angle && angle <= -60)
		{
			return 5;
		}
		else if (-180 < angle && angle <= -120)
		{
			return 6;
		}
		else
		{
			return 0; //GoingStraight
		}
	}
	
	private static String getStringFromDirectionValue(int val)
	{
		switch(val)
		{
		case 0:
			return "Go straight";
		case 1:
			return "slight right turn";
		case 2:
			return "right turn";
		case 3:
			return "sharp right turn";
		case 4:
			return "slight left turn";
		case 5:
			return "left turn";
		case 6:
			return "sharp left turn";
		default:
			return ""; //GoingStraight
		}
	}
	
	//Returns the angle between two nodes in degrees
	private static int getAngle(Node n1, Node n2)
	{
		int dx = n2.getX() - n1.getX();
		int dy = n2.getY() - n1.getY();
		return (int) ((180 / Math.PI) * Math.atan2(dy,dx));
	}

}
