package airlink.models;

public class Flight {
    public int flightId;
    public String source;
    public String destination;
    public int delay;

    public Flight(int flightId, String source, String destination, int delay) {
        this.flightId = flightId;
        this.source = source;
        this.destination = destination;
        this.delay = delay;
    }

    @Override
    public String toString() {
        return "Flight ID: " + flightId +
                " | Source: " + source +
                " | Destination: " + destination +
                " | Delay: " + delay + " mins";
    }
}