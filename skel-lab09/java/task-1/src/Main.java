	import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Main {
	static class Task {
		public static final String INPUT_FILE = "in";
		public static final String OUTPUT_FILE = "out";
		public static final int NMAX = 50005;
		public static final int INF = 500050;

		int n;
		int m;
		int source;

		public class Edge {
			public int node;
			public int cost;

			Edge(int _node, int _cost) {
				node = _node;
				cost = _cost;
			}
		}

		public class Pair {
			public int node;
			public int distFromSource;
			
			Pair(int n, int dist) {
				node = n;
				distFromSource = dist;
			}
		}
		 
		class QComparator implements Comparator<Pair> {

			@Override
			public int compare(Pair o1, Pair o2) {
				return o1.distFromSource - o2.distFromSource;
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
				source = sc.nextInt();

				for (int i = 1; i <= n; i++) {
					adj[i] = new ArrayList<>();
				}
				for (int i = 1; i <= m; i++) {
					int x, y, w;
					x = sc.nextInt();
					y = sc.nextInt();
					w = sc.nextInt();
					adj[x].add(new Edge(y, w));
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(ArrayList<Integer> result) {
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(
								OUTPUT_FILE));
				StringBuilder sb = new StringBuilder();
				for (int i = 1; i <= n; i++) {
					sb.append(result.get(i)).append(' ');
				}
				sb.append('\n');
				bw.write(sb.toString());
				bw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private ArrayList<Integer> getResult() {
			// TODO: Gasiti distantele minime de la nodul source la celelalte noduri
			// folosind Dijkstra pe graful orientat cu n noduri, m arce stocat in adj.
			//	d[node] = costul minim / lungimea minima a unui drum de la source la
			//	nodul node;
			//	d[source] = 0;
			//	d[node] = -1, daca nu se poate ajunge de la source la node.
			// Atentie:
			// O muchie este tinuta ca o pereche (nod adiacent, cost muchie):
			//	adj[x].get(i).node = nodul adiacent lui x,
			//	adj[x].get(i).cost = costul.
			ArrayList<Integer> d = new ArrayList<>();
			d.add(0);
			for (int i = 1; i <= n; i++)
				d.add(INF);
			d.set(source, 0);
			ArrayList<Integer> P = new ArrayList<>();
			for (int i = 0; i <= n; i++)
				P.add(-1);
			int[] selectat = new int[n + 1];
			Arrays.fill(selectat, 0);
			PriorityQueue<Pair> Q = new PriorityQueue<>(new QComparator());
			Q.add(new Pair(source, d.get(source)));
			while (!Q.isEmpty()) {
				Pair p = Q.poll();
				int u = p.node;
				selectat[u] = 1;
				for (Edge e : adj[u]) {
					if (selectat[e.node] == 0 && d.get(e.node) > d.get(u) + e.cost) {
						d.set(e.node, d.get(u) + e.cost);
						P.set(e.node, u);
						Q.add(new Pair(e.node, d.get(e.node)));
					}
				}
			}
			for (int i = 1; i <= n; i++) {
				Vector<Integer> v = new Vector<>();
				if (d.get(i) != INF) {
					int tmp = i;
					for(;;) {
						v.add(tmp);
						if (tmp == source)
							break;
						tmp = P.get(tmp);
					}
				}
				Collections.reverse(v);
				System.out.println(v);
			}
			for (int i = 1; i <= n; i++)
				if (d.get(i) == INF)
					d.set(i, -1);
			return d;
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
