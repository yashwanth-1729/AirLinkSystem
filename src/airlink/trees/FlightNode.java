package airlink.trees;

import airlink.models.Flight;

public class FlightNode {
    Flight flight;
    FlightNode left, right;

    public FlightNode(Flight flight) {
        this.flight = flight;
        left = right = null;
    }
}