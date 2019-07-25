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
		public static final int NMAX = 100005; // 10^5

		int n;
		int m;

		@SuppressWarnings("unchecked")
		ArrayList<Integer> adj[] = new ArrayList[NMAX];
		
		int idx[];
		int low[];

		private void readInput() {
			try {
				Scanner sc = new Scanner(new BufferedReader(new FileReader(
								INPUT_FILE)));
				n = sc.nextInt();
				m = sc.nextInt();
				
				idx = new int[n + 1];
				low = new int[n + 1];
				Arrays.fill(idx, -1);
				Arrays.fill(low, -1);

				for (int i = 1; i <= n; i++)
					adj[i] = new ArrayList<>();
				for (int i = 1; i <= m; i++) {
					int x, y;
					x = sc.nextInt();
					y = sc.nextInt();
					adj[x].add(y);
					adj[y].add(x);
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(ArrayList<Integer> result) {
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
								OUTPUT_FILE)));
				for (int node : result) {
					pw.printf("%d ", node);
				}
				pw.printf("\n");
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		void dfsCV(int v, int time, ArrayList<Integer> sol) {
			idx[v] = time;
			low[v] = time;
			time++;
			Vector<Integer> children = new Vector<>();
			for (int u : adj[v]) {
				if (idx[u] == -1) {
					children.add(u); //push (u, v)
					dfsCV(u, time, sol);
					low[v] = Math.min(low[v], low[u]);
				}
				else {
					low[v] = Math.min(low[v], idx[u]);
				}
			}
			// critic => pop pana ajungem la o muchia cu 4 si ce noduri scoatem pune in sol
			if (idx[v] == low[v]) {
				if (children.size() >= 2) {
					sol.add(v);
				}
			}
			else {
				for (int u : children) {
					if (low[u] >= idx[v]) {
						sol.add(v);
						break;
					}
				}
			}
		}
		
		private ArrayList<Integer> getResult() {
			// TODO: Gasiti nodurile critice ale grafului neorientat stocat cu liste
			// de adiacenta in adj.
			ArrayList<Integer> sol = new ArrayList<>();
			int time = 0;
			for (int v = 1; v <= n; v++) {
				if (idx[v] == -1)
					dfsCV(v, time, sol);
			}
			return sol;
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
