package ui;

public class CompareTwoBoolean2DArrays {
	public static boolean equals(boolean[][] left, boolean[][] right) {
		if (left.length != right.length) {
			return false;
		}
		boolean result = true;
		for (int i = left.length - 1; i >= 0; i--) {
			result &= equals(left[i], right[i]);
		}
		return result;
	}
	public static boolean equals(boolean[] left, boolean[] right) {
		if (left.length != right.length) {
			return false;
		}
		boolean result = true;
		for (int i = left.length - 1; i >= 0; i--) {
			result &= left[i] == right[i];
		}
		return result;
	}
}