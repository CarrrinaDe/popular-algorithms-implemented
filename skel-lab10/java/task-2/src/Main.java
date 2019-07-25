import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Main {
	static class Task {
		public static final String INPUT_FILE = "in";
		public static final String OUTPUT_FILE = "out";
		public static final int NMAX = 200005;
		public static final int INF = 9999999;
		public static final int NIL = -1;


		int n;
		int m;

		public class Edge {
			public int node;
			public int cost;

			Edge(int _node, int _cost) {
				node = _node;
				cost = _cost;
			}
		}
		
		@SuppressWarnings("unchecked")
		ArrayList<Edge> adj[] = new ArrayList[NMAX];

		private void readInput() {
			try {
				Scanner sc = new Scanner(new BufferedReader(new FileReader(
								INPUT_FILE)));
				n = sc.nextInt();
				m = sc.nextInt();

				for (int i = 1; i <= n; i++)
					adj[i] = new ArrayList<>();
				for (int i = 1; i <= m; i++) {
					int x, y, w;
					x = sc.nextInt();
					y = sc.nextInt();
					w = sc.nextInt();
					adj[x].add(new Edge(y, w));
					adj[y].add(new Edge(x, w));
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(int result) {
			try {
				PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
				pw.printf("%d\n", result);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private int getResult() {
			/*
			TODO: Calculati costul minim al unui arbore de acoperire
			folosind algoritmul lui Prim.
			*/
			int minCost = 0;
			int[] d = new int[n + 1];
			int[] p = new int[n + 1];
			int[] visited = new int[n + 1];
			Arrays.fill(d, INF);
			Arrays.fill(p, NIL);
			Arrays.fill(visited, 0);
			int root = 1;
			d[root] = 0;
			PriorityQueue<Edge> pq = new PriorityQueue<>((x, y) -> x.cost - y.cost);
			pq.add(new Edge(root, d[root]));
			while (!pq.isEmpty()) {
				Edge min = pq.poll();
				int u = min.node;
				if (visited[u] == 0) {
					minCost += min.cost;
					visited[u] = 1;
					for (Edge e : adj[u]) {
						int v = e.node;
						if (visited[v] == 0 && e.cost < d[v]) {
							d[v] = e.cost;
							p[v] = u;
							pq.add(new Edge(v, d[v]));
						}
					}
				}
			}
			for (Edge e : adj[root]) {
				if (e.node == p[root]) {
					minCost -= e.cost;
				}
			}
			return minCost;
		}

		public void solve() {
			readInput();
			writeOutput(getResult());
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
