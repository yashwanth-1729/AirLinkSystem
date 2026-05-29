package airlink.algorithms;

public class Dijkstra {

    public static void dijkstra(int graph[][], int source) {

        int n = graph.length;

        int[] distance = new int[n];
        boolean[] visited = new boolean[n];

        for (int i = 0; i < n; i++) {
            distance[i] = Integer.MAX_VALUE;
        }

        distance[source] = 0;

        for (int count = 0; count < n - 1; count++) {

            int u = minDistance(distance, visited);

            visited[u] = true;

            for (int v = 0; v < n; v++) {

                if (!visited[v]
                        && graph[u][v] != 0
                        && distance[u] != Integer.MAX_VALUE
                        && distance[u] + graph[u][v] < distance[v]) {

                    distance[v] = distance[u] + graph[u][v];
                }
            }
        }

        System.out.println("Shortest Distances:");

        for (int i = 0; i < n; i++) {
            System.out.println("Airport " + i + " -> " + distance[i]);
        }
    }

    private static int minDistance(int[] distance, boolean[] visited) {

        int min = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int i = 0; i < distance.length; i++) {

            if (!visited[i] && distance[i] <= min) {
                min = distance[i];
                minIndex = i;
            }
        }

        return minIndex;
    }
}