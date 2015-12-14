package DataAccess;

import AStar.Node;

public class Direction {

	String dirVal;
	double distance;
	Node n1;
	Node n2;

	public Direction(String dirVal, double distance, Node n1, Node n2) {

		this.n1 = n1;
		this.n2 = n2;
		this.dirVal = dirVal;
		this.distance = distance;
	}

	public String createStraightDirection() {

		String stringDirection = " ";

		if (n1.getType() == Node.Type.ROOM && n2.getType() == Node.Type.INTERSECTION) {

			stringDirection = ("Go straight out of " + n1.getName() + ". (" + distance + " ft)");

		} else if (n1.getType() == Node.Type.ROOM && n2.getType() == Node.Type.ROOM) {

			stringDirection = ("Go straight out of " + n1.getName() + " and into " + n2.getName() + ". (" + distance
					+ " ft)");

		} else if (n1.getType() == Node.Type.INTERSECTION && n2.getType() == Node.Type.ROOM) {
			stringDirection = ("Go straight into " + n2.getName() + ". (" + distance + " ft)");

		} else if (n1.getType() == Node.Type.INTERSECTION && n2.getType() == Node.Type.INTERSECTION) {
			System.out.println("INTERSECTION TO INSECTION STRAIGHT DISTANCE FOUND");
			stringDirection = ("Go straight for " + distance + " ft.");
		} else if (n1.getType() == Node.Type.ELEVATOR) {
			if (n2.getType() == Node.Type.ROOM) {
				stringDirection = ("Leave the elevator and enter " + n2.getName() + ". (" + distance + " ft)");
			} else {
				stringDirection = ("Go straight from the elevator. (" + distance + " ft)");
			}

		} else if (n2.getType() == Node.Type.ELEVATOR) {
			if (n1.getType() == Node.Type.ROOM) {
				stringDirection = ("Go straight out of " + n1.getType() + " into the elevator. (" + distance + " ft)");
			} else {
				stringDirection = ("Go straight into the elevator. (" + distance + " ft)");
			}

		} else if (n1.getType() == Node.Type.STAIRS) {
			if (n2.getType() == Node.Type.ROOM) {
				stringDirection = ("Go straight from the stairs and enter " + n2.getName() + ". (" + distance + " ft)");
			} else {
				stringDirection = ("Go straight from the stairs. (" + distance + " ft)");
			}

		} else if (n2.getType() == Node.Type.STAIRS) {
			if (n1.getType() == Node.Type.ROOM) {
				stringDirection = ("Go straight out of " + n1.getType() + " to the stairs. (" + distance + " ft)");
			} else {
				stringDirection = ("Go straight to the stairs. (" + distance + " ft)");
			}

		} else if (n1.getType() == Node.Type.ENTRANCE) {
			if (n2.getType() == Node.Type.ROOM) {
				stringDirection = ("Go straight from the " + n1.getName() + " and enter " + n2.getName() + ". ("
						+ distance + " ft)");
			} else {
				stringDirection = ("Go straight from the " + n1.getName() + ". (" + distance + " ft)");
			}

		} else if (n2.getType() == Node.Type.ENTRANCE) {
			if (n1.getType() == Node.Type.ROOM) {
				stringDirection = ("Go straight out of " + n1.getType() + " to the " + n2.getName() + ". (" + distance
						+ " ft)");
			} else {
				stringDirection = ("Go straight to the " + n2.getName() + ". (" + distance + " ft)");
			}

		}

		return stringDirection;
	}

	public String createTurnDirection() {
		String stringDirection = " ";
		stringDirection = ("Take a " + dirVal + ".");

		return stringDirection;

	}

}
