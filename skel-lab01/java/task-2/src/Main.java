import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
	static class Task {
		public final static String INPUT_FILE = "in";
		public final static String OUTPUT_FILE = "out";

		double n;

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));
				n = sc.nextDouble();
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(double x) {
			try {
				PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
				pw.printf("%.4f\n", x);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private double computeSqrt() {
			// TODO: Calculeaza sqrt(n) cu o precizie de 10^-3.
			// Precizie de 10^(-x) inseamna |valoarea_ta - valoarea_reala| < 10^(-x).
			double low, high, mid;
			
			if (n < 1) {
				low = 0;
				high = 1.0;
			}
			else {
				low = 1.0;
				high = n;
			}		
			while (low <= high) {
				mid = (low + high) / 2;
				if (Math.abs(mid * mid - n) < 0.001)
					return mid;
				if (mid * mid < n)
					low = mid;
				else
					high = mid;
			}
			return 0.0;
		}

		public void solve() {
			readInput();
			writeOutput(computeSqrt());
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
