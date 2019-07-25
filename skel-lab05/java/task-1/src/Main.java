import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	static class Task {
		public final static String INPUT_FILE = "in";
		public final static String OUTPUT_FILE = "out";

		int n, k;

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));
				n = sc.nextInt();
				k = sc.nextInt();
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(ArrayList<ArrayList<Integer>> result) {
			try {
				PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
				pw.printf("%d\n", result.size());
				for (ArrayList<Integer> arr : result) {
					for (int i = 0; i < arr.size(); i++) {
						pw.printf("%d%c", arr.get(i), i + 1 == arr.size() ?
								'\n' : ' ');
					}
				}
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void bkt(ArrayList<ArrayList<Integer>> all, ArrayList<Integer> domain, ArrayList<Integer> aranjament) {
			if (aranjament.size() == k) {
				all.add(aranjament);
				return;
			}
			for (int i = 0; i < domain.size(); i++) {
				if (aranjament.contains(domain.get(i)))
					continue;
				ArrayList<Integer> newD = new ArrayList<Integer>();
				newD.addAll(domain);
				newD.remove(domain.get(i));
				ArrayList<Integer> newA = new ArrayList<Integer>();
				newA.addAll(aranjament);
				newA.add(domain.get(i));
				bkt(all, newD, newA);
			}
		}
		
		private ArrayList<ArrayList<Integer>> getResult() {
			ArrayList<ArrayList<Integer>> all = new ArrayList<>();
			ArrayList<Integer> domain = new ArrayList<Integer>();
			for (int i = 0; i < n; i++) 
				domain.add(i, i + 1);
			bkt(all, domain, new ArrayList<Integer>());

			// TODO: Construiti toate aranjamentele de N luate cate K ale
			// multimii {1, ..., N}.

			// Pentru a adauga un nou aranjament:
			//   ArrayList<Integer> aranjament;
			//   all.add(aranjament);

			return all;
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
