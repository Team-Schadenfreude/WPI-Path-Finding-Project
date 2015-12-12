package DataAccess;

import java.util.ArrayList;
import java.util.List;

import AStar.Node;

public class DirectionBuilder {

	public DirectionBuilder() {
		// TODO Auto-generated constructor stub
	}

	// Method to provide a list of directions from a list of nodes.
	public static List<String> getDirectionsList(List<Node> path, double xScale, double yScale) {

		List<String> directionsList = new ArrayList<String>();

		if (path.size() == 0) {
			directionsList.add("There is no path to follow!");
		}

		else {

			// Set all variables to be used in finding the directions.
			// Distance variables.
			double totalDistance = 0;
			double runningDistance = 0;
			double distance = 0;

			// Angle variables.
			int prevAngle = 0;
			int currentAngle = 0;
			int deltaAngle = 0;

			// Direction values.
			String dirVal = " ";
			String prevDirVal = " ";

			// Map change value.
			boolean mapChange = false;

			for (int i = 0; i < path.size() - 1; i++) {
				Node n1 = path.get(i);
				Node n2 = path.get(i + 1);

				// Transition to another map!
				if (n1.isTransition() && n2.isTransition() && !n1.getMap().equals(n2.getMap())) {

					// Record total distance so far.
					totalDistance += distance;

					// Print direction.
					directionsList.add(prevDirVal + " " + Integer.toString((int) distance) + " ft.");
					directionsList.add("Proceed into " + n2.getMap() + ".");

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

					// Reset variables if map changes.
					if (i == 0 || mapChange) {
						deltaAngle = 0;
						dirVal = "straight";
						mapChange = false;
					}

					// Room to intersection interaction.
					if (n1.getType() == Node.Type.ROOM && n2.getType() == Node.Type.INTERSECTION) {

						// If first of the iteration.
						if (i == 0) {
							totalDistance += distance;
							directionsList.add("Go " + dirVal + " out of " + n1.getName() + ".");
							runningDistance = 0;
						}

						// If direction is straight, record distance and
						// continue.
						else if (dirVal.equals("straight")) {
							runningDistance += distance;
							distance = 0;
						}

						// If direction is not straight and previous direction
						// is not straight, print the prior go
						// straight, and then the next direction.
						else if (!prevDirVal.equals("straight")) {

							directionsList.add("Go straight for " + runningDistance + " ft.");
							directionsList.add("Then take a " + dirVal + "." + " (Room -> Inter)");
							runningDistance = 0;

						}

						// If direction is not straight and previous direction
						// is straight, print the next direction.
						else if (prevDirVal.equals("straight")) {

							runningDistance += distance;
							totalDistance += runningDistance;

							directionsList.add("Take a " + dirVal + ".");

							runningDistance = 0;
						}

					}

					// Room to room interaction.
					else if (n1.getType() == Node.Type.ROOM && n2.getType() == Node.Type.ROOM) {

						// If first iteration.
						if (i == 0) {

							runningDistance += distance;
							totalDistance += runningDistance;

							directionsList.add(
									"Go " + dirVal + " out of " + n1.getName() + " and into " + n2.getName() + ".");
							runningDistance = 0;
						}

						// If direction is straight, record distance and
						// continue.
						else if (dirVal.equals("straight")) {
							runningDistance += distance;
							totalDistance += runningDistance;

							directionsList.add("Go straight from" + n1.getName() + " into " + n2.getName() + " ("
									+ runningDistance + " ft).");

							runningDistance = 0;
						}

						// If direction is not straight and previous direction
						// is not straight, print the prior go
						// straight, and then the next direction.
						else if (!prevDirVal.equals("straight")) {

							runningDistance += distance;
							totalDistance += runningDistance;

							directionsList.add("Go straight into " + n1.getName() + " (" + runningDistance + " ft).");
							directionsList.add("Then take a " + dirVal + " into " + n2.getName() + ".");

							runningDistance = 0;

						}

						// If direction is not straight and previous direction
						// is straight, print the next direction.
						else if (prevDirVal.equals("straight")) {

							runningDistance += distance;
							totalDistance += runningDistance;

							directionsList.add("Take a " + dirVal + " into " + n2.getName() + ".");

							runningDistance = 0;
						}

					}

					// Intersection to room interaction.
					else if (n1.getType() == Node.Type.INTERSECTION && n2.getType() == Node.Type.ROOM) {

						// If direction is straight, record distance and
						// continue.
						if (dirVal.equals("straight")) {
							runningDistance += distance;
							distance = 0;
						}

						// If direction is not straight and previous direction
						// is not straight, print the prior go
						// straight, and then the next direction.
						else if (!prevDirVal.equals("straight")) {

							runningDistance += distance;
							totalDistance += runningDistance;

							directionsList.add("Go straight for " + runningDistance + " ft.");
							directionsList.add("Then take a " + dirVal + ".");

							runningDistance = 0;

						}

						// If direction is not straight and previous direction
						// is straight, print the next direction.
						else if (prevDirVal.equals("straight")) {

							runningDistance += distance;
							totalDistance += runningDistance;

							directionsList.add("Take a " + dirVal + ".");

							runningDistance = 0;
						}

					}

					// Intersection to intersection interaction.
					else if (n1.getType() == Node.Type.INTERSECTION && n2.getType() == Node.Type.INTERSECTION) {

						// If direction is straight, record distance and
						// continue.
						if (dirVal.equals("straight")) {
							runningDistance += distance;
							distance = 0;
						}

						// If direction is not straight and previous direction
						// is not straight, print the prior go
						// straight, and then the next direction.
						else if (!prevDirVal.equals("straight")) {

							runningDistance += distance;
							totalDistance += runningDistance;

							directionsList.add("Go straight for " + runningDistance + " ft.");
							directionsList.add("Then take a " + dirVal + ".");

							runningDistance = 0;

						}

						// If direction is not straight and previous direction
						// is straight, print the next direction.
						else if (prevDirVal.equals("straight")) {

							runningDistance += distance;
							totalDistance += runningDistance;

							directionsList.add("Take a " + dirVal + ".");

							runningDistance = 0;
						}

					}

					prevAngle = currentAngle;
					prevDirVal = dirVal;

				}

			}

			// Setup final directions.
			totalDistance += distance;
			directionsList.add("You have reached your destination.");
			directionsList.add("Total distance is " + Integer.toString((int) totalDistance) + "ft.");
		}

		// Print final directions.
		return directionsList;
	}

	private static String getDirectionValueFromAngle(int angle) {
		if (-20 < angle && angle < 20)// Going Straight
		{
			return "straight";
		} else if (20 <= angle && angle < 60) {
			return "slight right turn";
		} else if (60 <= angle && angle < 120) {
			return "right turn";
		} else if (120 < angle && angle <= 180) {
			return "sharp right turn";
		} else if (-60 < angle && angle <= -20) {
			return "slight left turn";
		} else if (-120 < angle && angle <= -60) {
			return "left turn";
		} else if (-180 < angle && angle <= -120) {
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
