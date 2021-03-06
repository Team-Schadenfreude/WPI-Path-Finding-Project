package DataAccess;

import java.util.LinkedList;
import java.util.List;

import AStar.Node;

public class DirectionBuilder {

	public DirectionBuilder() {
		// TODO Auto-generated constructor stub
	}

	// Method to provide a list of directions from a list of nodes.
	public static List<String> getDirectionsList(List<Node> path, double xScale, double yScale) {

		List<String> directionsList = new LinkedList<String>();

		if (path.size() == 0) {
			directionsList.add("There is no path to follow!");
		}

		else {

			// Set all variables to be used in finding the directions.
			// Distance variables.
			int totalDistance = 0;
			double distance = 0;
			int tempDistance = 0;

			// Angle variables.
			int prevAngle = 0;
			int currentAngle = 0;
			int deltaAngle = 0;

			// Direction values.
			String dirVal = " ";
			String prevDirVal = " ";
			Direction dir = null;

			// Map change value.
			boolean mapChange = false;

			for (int i = 0; i < path.size() - 1; i++) {
				Node n1 = path.get(i);
				Node n2 = path.get(i + 1);

				// Transition to another map!
				if (n1.isTransition() && n2.isTransition() && !n1.getMap().equals(n2.getMap())) {

					// Record total distance so far.
					totalDistance += distance;

					System.out.println("Should be adding directions.");
					System.out.println(directionsList);

					// Print direction.
					if (n2.getMap().toLowerCase().equals("campus")) {
						directionsList.add("Proceed out to " + n2.getMap() + ". (" + distance + " ft)");
					} else {
						directionsList.add("Proceed into " + n2.getMap() + ".");
					}

					System.out.println("Should have added directions.");
					System.out.println(directionsList);

					// Reset variables.
					// Reset distance.
					distance = 0;

					// Reset angles.
					prevAngle = 0;
					currentAngle = 0;
					deltaAngle = 0;

					// Reset direction values.
					prevDirVal = " ";
					dirVal = " ";

					// Notify map change.
					mapChange = true;

				} else {

					// Finds angle between two nodes.
					currentAngle = getAngle(n1, n2);

					// Sets the difference between the angles.
					deltaAngle = 0;
					deltaAngle = currentAngle - prevAngle;

					// Translates the angles to degrees.
					double delta_angle_rad = (Math.PI / 180) * (double) deltaAngle;

					// Bind angle to range [-180,180]
					deltaAngle = (int) ((180 / Math.PI)
							* Math.atan2(Math.sin(delta_angle_rad), Math.cos(delta_angle_rad)));

					// Direction from the deltaAngle
					dirVal = getDirectionValueFromAngle(deltaAngle);

					// Get distance between the two nodes.
					distance = n1.distanceTo(n2);
					totalDistance += (int) distance;

					// Reset variables if map changes.
					if (i == 0 || mapChange) {
						deltaAngle = 0;
						dirVal = "straight";
						mapChange = false;
					}

					if ((n1.getType() == Node.Type.NONE && n2.getType() == Node.Type.NONE)
							|| (n1.getType() == Node.Type.NONE && n2.getType() == Node.Type.INTERSECTION)) {
						dirVal = "straight";

					}

					if (n1.getDescription().toLowerCase().contains("cross")
							&& n2.getDescription().toLowerCase().contains("cross")) {

						if (dir.distance != 0) {
							// directionsList.add(dir.createTurnDirection());
							directionsList.add(dir.createStraightDirection());
						}

						dir = new Direction(dirVal, (int) distance, n1, n2);
						directionsList.add(dir.createTurnDirection());
						directionsList.add(dir.createStraightDirection());

						dir = new Direction(dirVal, 0, n1, n2);

					} else if (dirVal.equals("straight") && !prevDirVal.equals("straight")) {

						if (!directionsList.isEmpty() && i != path.size() - 2) {

							if (!(directionsList.get(directionsList.size() - 1).toLowerCase().contains("proceed"))) {
								directionsList.remove(directionsList.size() - 1);
							}

						}

						if (i == path.size() - 2) {
							dir = new Direction(dirVal, (int) distance, n1, n2);
							directionsList.add(dir.createStraightDirection());
						} else {

							if (dir == null) {
								dir = new Direction(dirVal, (int) distance, n1, n2);
							} else {

								tempDistance = (int) (dir.distance + distance);
								dir.distance = tempDistance;
								tempDistance = 0;

							}

						}

					} else if (dirVal.equals("straight") && prevDirVal.equals("straight")) {

						if (n1.getDescription().toLowerCase().contains("cross")
								|| n2.getDescription().toLowerCase().contains("cross")) {
							dir = new Direction(dirVal, dir.distance, n1, n2);
						}
						dir.distance += (int) distance;

						if (i == path.size() - 2) {
							directionsList.add(dir.createStraightDirection());
						}

					} else if (!dirVal.equals("straight") && prevDirVal.equals("straight")) {

						if (!(directionsList.isEmpty())
								&& directionsList.get(directionsList.size() - 1).contains("straight")) {
							directionsList.add(dir.createTurnDirection());
						}
						
						if (dir.distance != 0){
							directionsList.add(dir.createStraightDirection());
						}
						

						dir = new Direction(dirVal, (int) distance, n1, n2);
						directionsList.add(dir.createTurnDirection());
						directionsList.add(dir.createStraightDirection());

					} else if (!dirVal.equals("straight") && !prevDirVal.equals("straight")) {

						// tempDistance = (int) (dir.distance + distance);
						dir = new Direction(dirVal, (int) distance, n1, n2);
						directionsList.add(dir.createTurnDirection());
						directionsList.add(dir.createStraightDirection());
						// tempDistance = 0;
					}

					prevAngle = currentAngle;
					prevDirVal = dirVal;

				}

			}

			// Setup final directions.
			directionsList
					.add("You have reached your destination after " + Integer.toString((int) totalDistance) + " ft.");
		}

		// Print final directions.
		return directionsList;
	}

	private static String getDirectionValueFromAngle(int angle) {
		if (-25 < angle && angle < 25)// Going Straight
		{
			return "straight";
		} else if (25 <= angle && angle < 50) {
			return "slight right turn";
		} else if (50 <= angle && angle < 130) {
			return "right turn";
		} else if (130 < angle && angle <= 180) {
			return "sharp right turn";
		} else if (-50 < angle && angle <= -25) {
			return "slight left turn";
		} else if (-130 < angle && angle <= -50) {
			return "left turn";
		} else if (-180 < angle && angle <= -130) {
			return "sharp left turn";
		} else {
			return " "; // GoingStraight
		}
	}

	// Returns the angle between two nodes in degrees
	private static int getAngle(Node n1, Node n2) {
		int dx = n2.getX() - n1.getX();
		int dy = n2.getY() - n1.getY();
		return (int) ((180 / Math.PI) * Math.atan2(dy, dx));
	}

}
