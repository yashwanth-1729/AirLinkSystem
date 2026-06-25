package airlink.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import airlink.models.Flight;

/**
 * SQLite persistence layer for the AirLink System.
 *
 * The in-memory data structures (BST, Graph) remain the working structures used
 * by the program at run time. This class simply mirrors the data into a SQLite
 * database file ({@code airlink.db}) so that flights and airport routes survive
 * across runs of the program.
 */
public class Database {

    // The database file is created in the working directory on first use.
    private static final String URL = "jdbc:sqlite:airlink.db";

    private Database() {
        // Utility class - no instances.
    }

    /**
     * Opens a new connection to the SQLite database.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    /**
     * Creates the tables if they do not already exist. Call once at startup.
     */
    public static void initialize() {

        String createFlights =
                "CREATE TABLE IF NOT EXISTS flights (" +
                "    flight_id   INTEGER PRIMARY KEY," +
                "    source      TEXT NOT NULL," +
                "    destination TEXT NOT NULL," +
                "    delay       INTEGER NOT NULL" +
                ")";

        String createRoutes =
                "CREATE TABLE IF NOT EXISTS routes (" +
                "    id          INTEGER PRIMARY KEY AUTOINCREMENT," +
                "    source      TEXT NOT NULL," +
                "    destination TEXT NOT NULL" +
                ")";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(createFlights);
            stmt.execute(createRoutes);

        } catch (SQLException e) {
            System.out.println("Database initialization failed: " + e.getMessage());
        }
    }

    /**
     * Saves (inserts or updates) a single flight. Uses the flight id as the key
     * so re-adding an existing id updates the stored row instead of duplicating.
     */
    public static void saveFlight(Flight flight) {

        String sql = "INSERT OR REPLACE INTO flights " +
                "(flight_id, source, destination, delay) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, flight.flightId);
            ps.setString(2, flight.source);
            ps.setString(3, flight.destination);
            ps.setInt(4, flight.delay);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Could not save flight: " + e.getMessage());
        }
    }

    /**
     * Loads all stored flights, ordered by flight id.
     */
    public static List<Flight> loadFlights() {

        List<Flight> flights = new ArrayList<>();
        String sql = "SELECT flight_id, source, destination, delay " +
                "FROM flights ORDER BY flight_id";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                flights.add(new Flight(
                        rs.getInt("flight_id"),
                        rs.getString("source"),
                        rs.getString("destination"),
                        rs.getInt("delay")));
            }

        } catch (SQLException e) {
            System.out.println("Could not load flights: " + e.getMessage());
        }

        return flights;
    }

    /**
     * Saves a single airport route (a directed edge in the graph).
     */
    public static void saveRoute(String source, String destination) {

        String sql = "INSERT INTO routes (source, destination) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, source);
            ps.setString(2, destination);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Could not save route: " + e.getMessage());
        }
    }

    /**
     * Loads all stored routes as {source, destination} pairs.
     */
    public static List<String[]> loadRoutes() {

        List<String[]> routes = new ArrayList<>();
        String sql = "SELECT source, destination FROM routes ORDER BY id";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                routes.add(new String[] {
                        rs.getString("source"),
                        rs.getString("destination") });
            }

        } catch (SQLException e) {
            System.out.println("Could not load routes: " + e.getMessage());
        }

        return routes;
    }
}
