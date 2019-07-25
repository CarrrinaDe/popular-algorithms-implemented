import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Main {
	static class Task {
		public static final String INPUT_FILE = "in";
		public static final String OUTPUT_FILE = "out";
		public static final int NMAX = 1005;

		int n;
		int m;

		@SuppressWarnings("unchecked")
		ArrayList<Integer> adj[] = new ArrayList[NMAX];
		int C[][];

		private void readInput() {
			try {
				Scanner sc = new Scanner(new BufferedReader(new FileReader(
								INPUT_FILE)));
				n = sc.nextInt();
				m = sc.nextInt();

				C = new int[n + 1][n + 1];
				for (int i = 1; i <= n; i++) {
					adj[i] = new ArrayList<>();
				}
				for (int i = 1; i <= m; i++) {
					int x, y, z;
					x = sc.nextInt();
					y = sc.nextInt();
					z = sc.nextInt();
					adj[x].add(y);
					adj[y].add(x);
					C[x][y] += z;
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(int result) {
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
								OUTPUT_FILE)));
				pw.printf("%d\n", result);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private Vector<Integer> bfs(int s, int t) {
			int[] color = new int[n + 1];
			int[] dist = new int[n + 1];
			int[] parent = new int[n + 1];
			Arrays.fill(color, 'a');
			Arrays.fill(dist, -1);
			Arrays.fill(parent, 0);
			Vector<Integer> path = new Vector<Integer>();
			Queue<Integer> q = new LinkedList<>();
			q.add(s);
			color[s] = 'g';
			dist[s] = 0;
			while (!q.isEmpty()) {
				Integer u = q.peek();
				for (int v : adj[u]) {
					if (color[v] == 'a' && C[u][v] > 0) {
						color[v] = 'g';
						dist[v] = dist[u] + 1;
						parent[v] = u;
 						q.add(v);
					}
				}
				color[u] = 'n';
				q.poll();
			}
			if (dist[t] == -1)
				return null;
			int tmp = t;
			while (tmp != s) {
				path.add(tmp);
				tmp = parent[tmp];
			}
			path.add(s);
			Collections.reverse(path);
			return path;
		}
		
		private int getResult() {
			// TODO: Calculati fluxul maxim pe graful orientat dat.
			// Sursa este nodul 1.
			// Destinatia este nodul n.
			//
			// In adj este stocat graful neorientat obtinut dupa ce se elimina orientarea
			// arcelor, iar in C sunt stocate capacitatile arcelor.
			// De exemplu, un arc (x, y) de capacitate z va fi tinut astfel:
			// C[x][y] = z, adj[x] contine y, adj[y] contine x.
			int flow = 0;
			
			for (;;) {
				Vector<Integer> minPath = bfs(1, n);
				if (minPath == null)
					break;
				int cfMin = C[minPath.get(0)][minPath.get(1)];
				for (int i = 1; i < minPath.size() - 1; i++) {
					int u = minPath.get(i);
					int v = minPath.get(i + 1);
					if (C[u][v] < cfMin) 
						cfMin = C[u][v];
				}
				flow += cfMin;
				for (int i = 0; i < minPath.size() - 1; i++) {
					int u = minPath.get(i);
					int v = minPath.get(i + 1);
					C[v][u] += cfMin;
					C[u][v] -= cfMin;
				}
			}
			
			return flow;
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
