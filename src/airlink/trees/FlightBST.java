package airlink.trees;

import airlink.models.Flight;

public class FlightBST {

    private FlightNode root;

    public void insert(Flight flight) {
        root = insertRec(root, flight);
    }

    private FlightNode insertRec(FlightNode root, Flight flight) {

        if (root == null) {
            return new FlightNode(flight);
        }

        if (flight.flightId < root.flight.flightId) {
            root.left = insertRec(root.left, flight);
        } else if (flight.flightId > root.flight.flightId) {
            root.right = insertRec(root.right, flight);
        }

        return root;
    }

    public void display() {
        inorder(root);
    }

    private void inorder(FlightNode root) {

        if (root != null) {
            inorder(root.left);
            System.out.println(root.flight);
            inorder(root.right);
        }
    }

    public Flight search(int id) {
        return searchRec(root, id);
    }

    private Flight searchRec(FlightNode root, int id) {

        if (root == null)
            return null;

        if (root.flight.flightId == id)
            return root.flight;

        if (id < root.flight.flightId)
            return searchRec(root.left, id);

        return searchRec(root.right, id);
    }
}