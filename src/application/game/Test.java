package application.game;

import java.util.ArrayList;
import java.util.Random;

public class Test {

	public static void main(String[] args) {
		Board b = new Board();
		int[][] board = b.getBoard();
		int ctd = 0;
		System.out.print("    ");
		for (int i = 0; i < 10; i++) {
			System.out.print(i);
		}
		System.out.println();
		System.out.print("    ");
		for (int i = 0; i < 10; i++) {
			System.out.print("-");
		}
		System.out.println();
		for (int[] is : board) {
			System.out.print(ctd + " | ");
			for (int is2 : is) {
				System.out.print(is2);
			}
			ctd++;
			System.out.println();
		}
	}
}
