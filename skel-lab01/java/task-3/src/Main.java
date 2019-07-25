import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
	static class Task {
		public final static String INPUT_FILE = "in";
		public final static String OUTPUT_FILE = "out";

		int n, x, y;

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));
				n = sc.nextInt();
				x = sc.nextInt();
				y = sc.nextInt();
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(int answer) {
			try {
				PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
				pw.printf("%d\n", answer);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private int getAnswer(int n, int x, int y) {
			// TODO: Calculati valoarea de pe pozitia (x, y) din matricea de dimensiune
			// 2^N * 2^N.
			int Ox = 1, Oy = 1, dim = (int) Math.pow(2, n), valMin = 1, midx, midy;
			while (dim > 2) {
				midx = Ox + dim / 2 - 1;
				midy = Oy + dim / 2 - 1;
				if (x <= midx && y <= midy) {
					
				}
				if (x > midx && y <= midy) {
					Ox = midx + 1;
					valMin += 2 * Math.pow(dim, 2) / 4;
				}
				if (x <= midx && y > midy) {
					Oy = midy + 1;
					valMin += Math.pow(dim, 2) / 4;

				}
				if (x > midx && y > midy) {
					Ox = midx + 1;
					Oy = midy + 1;
					valMin += 3 * Math.pow(dim, 2) / 4;
				}
				dim = dim / 2;
			}
			if (dim == 2) {
				if (x == Ox && y == Oy)
					return valMin;
				if (x == Ox + 1 && y == Oy)
					return valMin + 2;
				if (x == Ox && y == Oy + 1)
					return valMin + 1;
				if (x == Ox + 1 && y == Oy + 1)
					return valMin + 3;
			}
			return 0;
		}

		public void solve() {
			readInput();
			writeOutput(getAnswer(n, x, y));
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
