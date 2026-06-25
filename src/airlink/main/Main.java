package airlink.main;

import java.util.Scanner;

import airlink.models.Flight;
import airlink.trees.FlightBST;
import airlink.graph.Graph;
import airlink.algorithms.Dijkstra;
import airlink.sorting.FlightSorting;
import airlink.db.Database;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        FlightBST bst = new FlightBST();
        Graph graph = new Graph();

        Flight[] flights = new Flight[100];
        int flightCount = 0;

        // Set up the database and load any previously saved data back into the
        // in-memory data structures so the system resumes where it left off.
        Database.initialize();

        List<Flight> savedFlights = Database.loadFlights();
        for (Flight savedFlight : savedFlights) {
            if (flightCount >= flights.length) {
                break;
            }
            bst.insert(savedFlight);
            flights[flightCount] = savedFlight;
            flightCount++;
        }

        for (String[] route : Database.loadRoutes()) {
            graph.addRoute(route[0], route[1]);
        }

        if (flightCount > 0) {
            System.out.println("Loaded " + flightCount + " flight(s) from database.");
        }

        while (true) {

            System.out.println("\n===== AIRLINK SYSTEM =====");
            System.out.println("1. Add Flight");
            System.out.println("2. Display Flights");
            System.out.println("3. Search Flight");
            System.out.println("4. Add Airport Route");
            System.out.println("5. Display Routes");
            System.out.println("6. BFS Traversal");
            System.out.println("7. DFS Traversal");
            System.out.println("8. Shortest Route");
            System.out.println("9. Sort Flights By Delay");
            System.out.println("10. Exit");

            System.out.print("Enter Choice: ");
            int choice = sc.nextInt();

            switch (choice) {

                case 1:

                    System.out.print("Enter Flight ID: ");
                    int id = sc.nextInt();

                    sc.nextLine();

                    System.out.print("Enter Source: ");
                    String source = sc.nextLine();

                    System.out.print("Enter Destination: ");
                    String destination = sc.nextLine();

                    System.out.print("Enter Delay (mins): ");
                    int delay = sc.nextInt();

                    Flight flight = new Flight(id, source, destination, delay);

                    bst.insert(flight);

                    flights[flightCount] = flight;
                    flightCount++;

                    Database.saveFlight(flight);

                    System.out.println("Flight Added Successfully!");

                    break;

                case 2:

                    if (flightCount == 0) {
                        System.out.println("No Flights Available!");
                    } else {
                        System.out.println("\n===== FLIGHT LIST =====");
                        bst.display();
                    }

                    break;

                case 3:

                    System.out.print("Enter Flight ID To Search: ");
                    int searchId = sc.nextInt();

                    Flight found = bst.search(searchId);

                    if (found != null) {
                        System.out.println(found);
                    } else {
                        System.out.println("Flight Not Found!");
                    }

                    break;

                case 4:

                    sc.nextLine();

                    System.out.print("Enter Source Airport: ");
                    String src = sc.nextLine();

                    System.out.print("Enter Destination Airport: ");
                    String dest = sc.nextLine();

                    graph.addRoute(src, dest);

                    Database.saveRoute(src, dest);

                    System.out.println("Route Added Successfully!");

                    break;

                case 5:

                    graph.displayRoutes();

                    break;

                case 6:

                    sc.nextLine();

                    System.out.print("Enter Starting Airport For BFS: ");
                    String bfsStart = sc.nextLine();

                    graph.bfs(bfsStart);

                    break;

                case 7:

                    sc.nextLine();

                    System.out.print("Enter Starting Airport For DFS: ");
                    String dfsStart = sc.nextLine();

                    graph.dfs(dfsStart);

                    break;

                case 8:

                    int[][] airportGraph = {
                            {0, 4, 0, 0},
                            {4, 0, 8, 0},
                            {0, 8, 0, 7},
                            {0, 0, 7, 0}
                    };

                    System.out.println("Running Dijkstra Algorithm...");
                    Dijkstra.dijkstra(airportGraph, 0);

                    break;

                case 9:

                    if (flightCount == 0) {

                        System.out.println("No Flights To Sort!");

                    } else {

                        Flight[] tempFlights = new Flight[flightCount];

                        for (int i = 0; i < flightCount; i++) {
                            tempFlights[i] = flights[i];
                        }

                        FlightSorting.sortByDelay(tempFlights);
                    }

                    break;

                case 10:

                    System.out.println("Exiting AirLink System...");
                    sc.close();
                    System.exit(0);

                default:

                    System.out.println("Invalid Choice!");
            }
        }
    }
}