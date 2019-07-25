import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
	static class Homework {
		public int deadline;
		public int score;

		public Homework() {
			deadline = 0;
			score = 0;
		}
	}

	class HwComparator implements Comparator<Homework> {

		@Override
		public int compare(Homework o1, Homework o2) {
			if (o1.deadline > o2.deadline)
				return -1;
			if (o1.deadline < o2.deadline)
				return 1;
			return o2.score - o1.score;
		}
		
	}
	
	static class Task {
		public final static String INPUT_FILE = "in";
		public final static String OUTPUT_FILE = "out";

		int n;
		Homework[] hws;

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));
				n = sc.nextInt();
				hws = new Homework[n];
				for (int i = 0; i < n; i++) {
					hws[i] = new Homework();
					hws[i].deadline = sc.nextInt();
					hws[i].score = sc.nextInt();
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
			// TODO: Aflati punctajul maxim pe care il puteti obtine
			// planificand optim temele.
			Main main = new Main();
			int lastDeadline = 0, score = 0;
			for (Homework h : hws) {
				if (h.deadline > lastDeadline)
					lastDeadline = h.deadline;
			}
			Arrays.sort(hws, main.new HwComparator());
			//lastDeadline--;
			//score += hws[0].score;
			for (int i = 0; i < hws.length; i++) {
				if (hws[i].deadline != hws[i + 1].deadline) {
					continue;
				} 
				
			}
			return score;
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
