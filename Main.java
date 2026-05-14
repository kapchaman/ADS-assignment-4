import java.util.*;

class Graph {
    // task1 adjlist
    private Map<Character, List<Edge>> adjList = new TreeMap<>();

    static class Edge {
        char target;
        int weight;

        Edge(char target, int weight) {
            this.target = target;
            this.weight = weight;
        }
    }

    public void addVertex(char v) {
        adjList.putIfAbsent(v, new ArrayList<>());
    }

    public void addEdge(char u, char v, int weight) {
        adjList.get(u).add(new Edge(v, weight));
        adjList.get(v).add(new Edge(u, weight)); // undirected graph
    }

    public void printAdjacencyList() {
        System.out.println(" Task 1: Adjacency List ");
        for (Map.Entry<Character, List<Edge>> entry : adjList.entrySet()) {
            System.out.print(entry.getKey() + ": ");
            for (Edge e : entry.getValue()) {
                System.out.print("(" + e.target + ", w:" + e.weight + ") ");
            }
            System.out.println();
        }
    }

    // task2 bfs
    public void bfs(char startNode) {
        System.out.print("BFS Traversal starting from " + startNode + ": ");
        Set<Character> visited = new HashSet<>();
        Queue<Character> queue = new LinkedList<>();

        visited.add(startNode);
        queue.add(startNode);

        while (!queue.isEmpty()) {
            char curr = queue.poll();
            System.out.print(curr + " ");
            for (Edge e : adjList.get(curr)) {
                if (!visited.contains(e.target)) {
                    visited.add(e.target);
                    queue.add(e.target);
                }
            }
        }
        System.out.println();
    }

    // task2 dfs
    public void dfs(char startNode) {
        System.out.print("DFS Traversal starting from " + startNode + ": ");
        Set<Character> visited = new HashSet<>();
        dfsHelper(startNode, visited);
        System.out.println();
    }

    private void dfsHelper(char v, Set<Character> visited) {
        visited.add(v);
        System.out.print(v + " ");
        for (Edge e : adjList.get(v)) {
            if (!visited.contains(e.target)) {
                dfsHelper(e.target, visited);
            }
        }
    }

    // task3
    public void dijkstra(char startNode) {
        System.out.println(" Task 3: Dijkstra's (Source: " + startNode + ") ");
        Map<Character, Integer> distances = new HashMap<>();
        Map<Character, Character> previous = new HashMap<>();
        PriorityQueue<NodeDistance> pq = new PriorityQueue<>(Comparator.comparingInt(nd -> nd.dist));

        for (char v : adjList.keySet()) {
            distances.put(v, Integer.MAX_VALUE);
        }
        distances.put(startNode, 0);
        pq.add(new NodeDistance(startNode, 0));

        while (!pq.isEmpty()) {
            NodeDistance current = pq.poll();
            char u = current.node;

            if (current.dist > distances.get(u))
                continue;

            for (Edge e : adjList.get(u)) {
                int newDist = distances.get(u) + e.weight;
                if (newDist < distances.get(e.target)) {
                    distances.put(e.target, newDist);
                    previous.put(e.target, u);
                    pq.add(new NodeDistance(e.target, newDist));
                }
            }
        }

        for (char v : adjList.keySet()) {
            System.out.print("To " + v + ": Distance = " + distances.get(v) + ", Path = ");
            printPath(v, previous);
            System.out.println();
        }
    }

    private void printPath(char v, Map<Character, Character> previous) {
        if (!previous.containsKey(v)) {
            System.out.print(v);
            return;
        }
        printPath(previous.get(v), previous);
        System.out.print(" -> " + v);
    }

    static class NodeDistance {
        char node;
        int dist;

        NodeDistance(char node, int dist) {
            this.node = node;
            this.dist = dist;
        }
    }

    public static void main(String[] args) {
        Graph g = new Graph();
        char[] vertices = { 'A', 'B', 'C', 'D', 'E', 'F', 'G' };
        for (char v : vertices)
            g.addVertex(v);

        g.addEdge('B', 'A', 13);
        g.addEdge('C', 'B', 1);
        g.addEdge('D', 'C', 1);
        g.addEdge('E', 'A', 9);
        g.addEdge('F', 'E', 4);
        g.addEdge('G', 'B', 7);
        g.addEdge('E', 'D', 12);

        g.printAdjacencyList();
        System.out.println("\n Task 2: Search ");
        g.dfs('G');
        g.bfs('G');
        System.out.println();
        g.dijkstra('D');
    }
}