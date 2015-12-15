package DataAccess;

import AStar.Node;

public class Direction {

	String dirVal;
	int distance;
	Node n1;
	Node n2;

	public Direction(String dirVal, int distance, Node n1, Node n2) {

		this.n1 = n1;
		this.n2 = n2;
		this.dirVal = dirVal;
		this.distance = distance;
	}

	public String createStraightDirection() {

		String stringDirection = " ";

		if (n1.getDescription().toLowerCase().contains("cross")
				&& n2.getDescription().toLowerCase().contains("cross")) {

			stringDirection = ("Cross the street. (" + this.distance + " ft)");

		}

		else if (n1.getType() == Node.Type.ROOM && n2.getType() == Node.Type.INTERSECTION) {

			stringDirection = ("Go straight out of " + n1.getName() + ". (" + this.distance + " ft)");

		} else if (n1.getType() == Node.Type.ROOM && n2.getType() == Node.Type.ROOM) {

			stringDirection = ("Go straight out of " + n1.getName() + " and into " + n2.getName() + ". ("
					+ this.distance + " ft)");

		} else if (n1.getType() == Node.Type.INTERSECTION && n2.getType() == Node.Type.ROOM) {
			stringDirection = ("Go straight into " + n2.getName() + ". (" + this.distance + " ft)");

		} else if (n1.getType() == Node.Type.INTERSECTION && n2.getType() == Node.Type.INTERSECTION) {
			if (n1.getMap().toLowerCase().equals("campus")) {
				stringDirection = ("Follow the path for " + this.distance + " ft.");
			} else {
				stringDirection = ("Go straight for " + this.distance + " ft.");
			}

		} else if (n1.getType() == Node.Type.ELEVATOR) {
			if (n2.getType() == Node.Type.ROOM) {
				stringDirection = ("Leave the elevator and enter " + n2.getName() + ". (" + this.distance + " ft)");
			} else {
				stringDirection = ("Go straight from the elevator. (" + this.distance + " ft)");
			}

		} else if (n2.getType() == Node.Type.ELEVATOR) {
			if (n1.getType() == Node.Type.ROOM) {
				stringDirection = ("Go straight out of " + n1.getType() + " into the elevator. (" + this.distance
						+ " ft)");
			} else {
				stringDirection = ("Go straight into the elevator. (" + this.distance + " ft)");
			}

		} else if (n1.getType() == Node.Type.STAIRS) {
			if (n2.getType() == Node.Type.ROOM) {
				stringDirection = ("Go straight from the stairs and enter " + n2.getName() + ". (" + this.distance
						+ " ft)");
			} else {
				stringDirection = ("Go straight from the stairs. (" + this.distance + " ft)");
			}

		} else if (n2.getType() == Node.Type.STAIRS) {
			if (n1.getType() == Node.Type.ROOM) {
				stringDirection = ("Go straight out of " + n1.getType() + " to the stairs. (" + this.distance + " ft)");
			} else {
				stringDirection = ("Go straight to the stairs. (" + this.distance + " ft)");
			}

		} else if (n1.getType() == Node.Type.ENTRANCE) {
			if (n2.getType() == Node.Type.ROOM) {
				stringDirection = ("Go straight from the " + n1.getName() + " and enter " + n2.getName() + ". ("
						+ this.distance + " ft)");
			} else {
				stringDirection = ("Go straight from the " + n1.getName() + ". (" + this.distance + " ft)");
			}

		} else if (n2.getType() == Node.Type.ENTRANCE) {
			if (n1.getType() == Node.Type.ROOM) {
				stringDirection = ("Go straight out of " + n1.getType() + " to the " + n2.getName() + ". ("
						+ this.distance + " ft)");
			} else {
				stringDirection = ("Go straight to the " + n2.getName() + ". (" + this.distance + " ft)");
			}

		} else if (n1.getType() == Node.Type.NONE && n2.getType() == Node.Type.INTERSECTION) {
			stringDirection = ("Follow the path for " + this.distance + " ft.");
		} else if (n1.getType() == Node.Type.INTERSECTION && n2.getType() == Node.Type.NONE) {
			stringDirection = ("Follow the path for " + this.distance + " ft.");
		}

		return stringDirection;
	}

	public String createTurnDirection() {
		String stringDirection = " ";

		if (!(n1.getType() == Node.Type.NONE)) {
			stringDirection = ("Take a " + dirVal + ".");
		}

		return stringDirection;

	}

}
