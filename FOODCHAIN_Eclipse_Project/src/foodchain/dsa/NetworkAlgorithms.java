package foodchain.dsa;

import java.util.*;

public class NetworkAlgorithms {
    private NetworkAlgorithms() {}

    // Edmonds-Karp maximum flow. Capacity matrix is copied internally.
    public static int edmondsKarp(int[][] capacity, int source, int sink) {
        int n = capacity.length;
        if (source < 0 || sink < 0 || source >= n || sink >= n || source == sink)
            throw new IllegalArgumentException("Invalid source/sink");
        int[][] residual = new int[n][n];
        for (int i = 0; i < n; i++) residual[i] = Arrays.copyOf(capacity[i], n);
        int maxFlow = 0;
        int[] parent = new int[n];
        while (bfs(residual, source, sink, parent)) {
            int pathFlow = Integer.MAX_VALUE;
            for (int v = sink; v != source; v = parent[v])
                pathFlow = Math.min(pathFlow, residual[parent[v]][v]);
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                residual[u][v] -= pathFlow;
                residual[v][u] += pathFlow;
            }
            maxFlow += pathFlow;
        }
        return maxFlow;
    }

    private static boolean bfs(int[][] residual, int s, int t, int[] parent) {
        Arrays.fill(parent, -1);
        parent[s] = s;
        Queue<Integer> q = new ArrayDeque<>();
        q.add(s);
        while (!q.isEmpty()) {
            int u = q.remove();
            for (int v = 0; v < residual.length; v++) {
                if (parent[v] == -1 && residual[u][v] > 0) {
                    parent[v] = u;
                    if (v == t) return true;
                    q.add(v);
                }
            }
        }
        return false;
    }

    // Bipartite matching: left-side vertices to right-side vertices.
    public static int maximumBipartiteMatching(boolean[][] edges) {
        int left = edges.length;
        int right = left == 0 ? 0 : edges[0].length;
        int[] matchRight = new int[right];
        Arrays.fill(matchRight, -1);
        int count = 0;
        for (int u = 0; u < left; u++) {
            if (augment(u, edges, matchRight, new boolean[right])) count++;
        }
        return count;
    }

    private static boolean augment(int u, boolean[][] edges, int[] matchRight, boolean[] seen) {
        for (int v = 0; v < matchRight.length; v++) {
            if (edges[u][v] && !seen[v]) {
                seen[v] = true;
                if (matchRight[v] == -1 || augment(matchRight[v], edges, matchRight, seen)) {
                    matchRight[v] = u;
                    return true;
                }
            }
        }
        return false;
    }
}
