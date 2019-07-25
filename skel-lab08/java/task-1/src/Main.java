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
		@SuppressWarnings("unchecked")
		ArrayList<Integer> adjt[] = new ArrayList[NMAX];
		
		int color[];
		Stack<Integer> S = new Stack<>();

		private void readInput() {
			try {
				Scanner sc = new Scanner(new BufferedReader(new FileReader(
								INPUT_FILE)));
				n = sc.nextInt();
				m = sc.nextInt();
				
				color = new int[n + 1];

				for (int i = 1; i <= n; i++) {
					adj[i] = new ArrayList<>();
					adjt[i] = new ArrayList<>();
					color[i] = 'w';
				}
				for (int i = 1; i <= m; i++) {
					int x, y;
					x = sc.nextInt();
					y = sc.nextInt();
					adj[x].add(y);
					adjt[y].add(x);
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(ArrayList<ArrayList<Integer>> result) {
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
								OUTPUT_FILE)));
				pw.printf("%d\n", result.size());
				for (ArrayList<Integer> ctc : result) {
					for (int nod : ctc) {
						pw.printf("%d ", nod);
					}
					pw.printf("\n");
				}
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		void dfs(int v) {
			color[v] = 'g';
			for (int u : adj[v]) {
				if (color[u] == 'w')
					dfs(u);
			}
			S.push(v);
			color[v] = 'b';
		}
		
		void dfsT(int v, ArrayList<Integer> comp) {
			color[v] = 'g';
			for (int u : adjt[v]) {
				if (color[u] == 'w')
					dfsT(u, comp);
			}
			comp.add(v);
			color[v] = 'b';
		}
		
		private ArrayList<ArrayList<Integer>> getResult() {
			// TODO: Gasiti componentele tare conexe ale grafului orientat cu
			// n noduri, stocat in adj. Rezultatul se va returna sub forma
			// unui ArrayList, ale carui elemente sunt componentele tare conexe
			// detectate. Nodurile si componentele tare conexe pot fi puse in orice
			// ordine in arraylist.
			//
			// Atentie: graful transpus este stocat in adjt.
			ArrayList<ArrayList<Integer>> sol = new ArrayList<>();
			for (int u = 1; u <= n; u++) {
				if (color[u] == 'w')
					dfs(u);
			}
			for (int u = 1; u <= n; u++) {
				color[u] = 'w';
			}
			while (!S.isEmpty()) {
				int u = S.pop();
				if (color[u] == 'w') {
					ArrayList<Integer> comp = new ArrayList<>();
					dfsT(u, comp);
					sol.add(comp);
				}
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
