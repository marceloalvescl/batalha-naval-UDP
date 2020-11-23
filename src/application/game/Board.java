package application.game;

import java.util.ArrayList;
import java.util.Random;


public class Board {
	
	int[][] first = new int[10][10];
	ArrayList<Integer> list = new ArrayList<Integer>();
	
	public int[][] getBoard (){
		init();
		return first;
	}
	
	public void init() {
		Board b = new Board();
		b.getSubamarinePositions();
		b.getCruzadersPositions();
		b.getAircraftCarrierPositions();
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				Integer posicaoAtual = Integer.valueOf(String.valueOf(i) + String.valueOf(j));
				if(b.list.contains(posicaoAtual)){
					first[i][j] = 1;
				}else{
					first[i][j] = 0;
				}
			}	
		}
	}
	
	private Integer getValidPosition(int qtdPosicoes) {
		Random rand = new Random();
		Integer num = Integer.valueOf(rand.nextInt(100));
		if(validPosition(num, qtdPosicoes)) {
			return num; 
		}else {
			return getValidPosition(qtdPosicoes);
		}
	}
	
	private boolean validPosition(Integer num, int qtdPosicoes) {
		if(list.contains(num)) {
			return false;
		}
		if(num % 10 == 0) {
			return false;
		}
		if(qtdPosicoes == 2 && (num + 1) % 10 == 0) {
			return false;
		}
		if(qtdPosicoes == 3 && ((num + 1) % 10 == 0 || (num + 2) % 10 == 0)) {
			return false;
		}
		return true;
	}
	
	private void getSubamarinePositions() {
		list.add(getValidPosition(1));
		list.add(getValidPosition(1));
		list.add(getValidPosition(1));
		
	}
	
	private void getCruzadersPositions(){
		int ctd = 0;
		while(ctd <= 2) {
			int num = getValidPosition(2);
			if(validPosition(num + 1, 2)) {
				ctd++;
				list.add(num);
				list.add(num + 1);
			}
			
		}
	}

	private void getAircraftCarrierPositions(){
		int ctd = 0;
		while(ctd <= 2) {
			int num = getValidPosition(3);
			if(validPosition(num + 1, 2) && validPosition(num + 2, 1)) {
				ctd++;
				list.add(num);
				list.add(num + 1);
				list.add(num + 2);
			}
		}
	}

}
