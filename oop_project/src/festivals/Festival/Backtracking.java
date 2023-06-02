package Festival;

public class Backtracking {
	private static boolean Check(int[] x, int index) {
		// return true;
		if (index > 0 && x[index] == 1 && x[index - 1] == 1) {
			return false;
		}
		return true;
	}

	private static void Solution(int[] x, int n) {
		for (int digit : x) {
			System.out.print(digit);
		}
		System.out.println();
	}

	private static void Try(int[] x, int index) {
		if (index == x.length) {
			Solution(x, index);
			return;
		}

		x[index] = 0;
		if (Check(x, index)) {
			Try(x, index + 1);
		}

		x[index] = 1;
		if (Check(x, index)) {
			Try(x, index + 1);
		}
	}

	public static void Generate(int n) {
		int[] x = new int[n];
		Try(x,0);
	}
	
//	

}
