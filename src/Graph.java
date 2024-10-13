// Ismail-Ridwan jama
//Student id:w1768863

import java.io.File;
import java.util.*;

public class Graph implements GraphInter{
    private final int V;  // number of vertices
    private boolean[][] adj; // adjacency matrix
    private final boolean[] visited; // array to track visited vertices
    private final int[] parent; // array to store parent vertices
    private final int[] outDegree; // array to store the number of outgoing edges for each vertex

    public Graph(int V) {
        this.V = V;
        adj = new boolean[V][V];
        visited = new boolean[V];
        parent = new int[V];
        outDegree = new int[V];
        Arrays.fill(parent, -1); // initialize parent vertices with -1
    }

    public void addEdge(int v, int w) {
        adj[v][w] = true;

        outDegree[v]++; // this will update the number of outgoing edges for vertex v
    }
    public void removeEdge(int v, int w) {
        adj[v][w] = false;

        outDegree[v]--; // decrement the number of outgoing edges for vertex v
    }
    public void removeSink() {


        while(true) {
            // Find the vertex with no outgoing edges
            int vertex = -1;
            for (int i = 0; i < V; i++) {
                if (outDegree[i] == 0) {
                    vertex = i;
                    break;
                }
            }

            // If no such vertex is found, return
            if (vertex == -1) {
                return;
            }

            // Remove the vertex and its incoming edges
            for (int i = 0; i < V; i++) {
                if (adj[i][vertex]) {
                    System.out.println(" Found a sink at " + vertex + " with an incoming edge at vertex " + i +  " ");
                    System.out.println( " The edge between "  + i + " and "  + vertex + ": [HAS BEEN ELIMINATED] ");
                    System.out.println("");
                    removeEdge(i, vertex);
                }
            }


        }
    }
    public void createGraphFromFile(String filename) {
        Scanner graphFile = null;

        try {
            graphFile = new Scanner(new File("c_40_0.txt"));
        } catch (Exception e) {
            System.out.println("Could not open input file: " + filename);
            System.exit(0);
        }

        // get number of vertices in graph - first value in the file
        int numVertices = graphFile.nextInt();

        // create a new adjacency matrix
        boolean[][] newAdj = new boolean[numVertices][numVertices];

        // copy the values from the old matrix to the new one
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                newAdj[i][j] = adj[i][j];
            }

        }

        // update the adjacency matrix
        adj = newAdj;

        // While there are numbers left, read two and use them to add an edge
        while (graphFile.hasNextInt()) {
            int v = graphFile.nextInt();
            int w = graphFile.nextInt();

            addEdge(v, w);
        }
    }

    public void hasSink() {
        // this will loop through all nodes in the graph
        for (int v = 0; v < V; v++) {
            // this resets the visited and parent arrays for everytime it does a DS traversal
            Arrays.fill(visited, false);
            Arrays.fill(parent, -1);

            if (hasSink(v)) {
                removeSink();
                return;
            }
        }
        System.out.println("there is no sink in this graph");
    }
    private boolean hasSink(int v) {
        visited[v] = true;

        // Check if any adjacent vertex is a sink
        for (int i = 0; i < V; i++) {
            if (adj[v][i]) {
                // If the vertex is not visited, recursively check for sinks
                if (!visited[i]) {
                    parent[i] = v;
                    if (hasSink(i)) {
                        return true;
                    }
                }
                // If the vertex is visited and it is not the parent, then it is a sink
                else if (i != parent[v]) {
                    return true;
                }
            }
        }
        return false;
    }

    public void hasCycle() {
        // this will call the DFS function for each vertex
        for (int v = 0; v < V; v++) {
            boolean[] visited = new boolean[V];
            int[] parent = new int[V];
            Arrays.fill(parent, -1); // initializes the parent vertices with -1
            if (hasCycle(v, visited, parent)) {
                return;
            }
        }
    }

    private boolean hasCycle(int v, boolean[] visited, int[] parent) {
        visited[v] = true;

        // Check if any adjacent vertex is a part of a cycle
        for (int i = 0; i < V; i++) {
            if (adj[v][i]) {
                // If the vertex is visited and it is not the parent then it is part of a cycle
                if (visited[i] && i != parent[v]) {
                    printCycle(v, i, parent);
                    return false;
                }

                // If the vertex is not visited, recursively check for cycles
                if (!visited[i]) {
                    parent[i] = v;
                    if (hasCycle(i, visited, parent)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }



    private void printCycle(int v, int w, int[] parent) {
        List<Integer> cycle = new ArrayList<>();
        cycle.add(w);
        int current = v;
        while (current != w) {
            if (parent[current] == -1) {
                return;
            }
            cycle.add(current);
            current = parent[current];
        }
        cycle.add(w);

        System.out.print(cycle.get(cycle.size() - 1));
        for (int i = cycle.size() - 2; i >= 0; i--) {
            System.out.print(" <- " + cycle.get(i));
        }
        System.out.println();
    }






}


