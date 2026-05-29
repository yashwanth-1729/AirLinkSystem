package airlink.graph;

import java.util.*;

public class Graph {

    private Map<String, List<String>> graph = new HashMap<>();

    public void addRoute(String source, String destination) {

        graph.putIfAbsent(source, new ArrayList<>());
        graph.putIfAbsent(destination, new ArrayList<>());

        graph.get(source).add(destination);
    }

    public void displayRoutes() {

        for (String airport : graph.keySet()) {
            System.out.println(airport + " -> " + graph.get(airport));
        }
    }

    public void bfs(String start) {

        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        visited.add(start);
        queue.add(start);

        System.out.println("BFS Traversal:");

        while (!queue.isEmpty()) {

            String airport = queue.poll();
            System.out.print(airport + " ");

            for (String neighbor : graph.get(airport)) {

                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        System.out.println();
    }

    public void dfs(String start) {

        Set<String> visited = new HashSet<>();
        System.out.println("DFS Traversal:");
        dfsRec(start, visited);
        System.out.println();
    }

    private void dfsRec(String airport, Set<String> visited) {

        visited.add(airport);
        System.out.print(airport + " ");

        for (String neighbor : graph.get(airport)) {

            if (!visited.contains(neighbor)) {
                dfsRec(neighbor, visited);
            }
        }
    }
}