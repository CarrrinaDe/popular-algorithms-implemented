import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	static class Task {
		public final static String INPUT_FILE = "in";
		public final static String OUTPUT_FILE = "out";

		int n;

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));
				n = sc.nextInt();
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

		private void bkt(ArrayList<ArrayList<Integer>> all, ArrayList<Integer> domain, ArrayList<Integer> submultime) {
			
			if (submultime.size() <= n) {
				all.add(submultime);
				if (submultime.size() == n)
					return;
			}
				//return;
				int i = submultime.size() > 0? submultime.get(submultime.size() - 1) - 1 : 0;
				for (; i < domain.size(); i++) {
					if (submultime.contains(domain.get(i)))
						continue;
					submultime.add(domain.get(i));
					bkt(all, domain, submultime);
				}
			
		}
		
		private ArrayList<ArrayList<Integer>> getResult() {
			ArrayList<ArrayList<Integer>> all = new ArrayList<>();
			ArrayList<Integer> domain = new ArrayList<Integer>();
			for (int i = 0; i < n; i++) 
				domain.add(i + 1);
			bkt(all, domain, new ArrayList<Integer>());
			// TODO: Construiti toate submultimele multimii {1, ..., N}.

			// Pentru a adauga o noua submultime:
			//   ArrayList<Integer> submultime;
			//   all.add(submultime);

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
