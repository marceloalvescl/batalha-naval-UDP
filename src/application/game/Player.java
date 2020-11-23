package application.game;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;


public class Player{

	public static int[][] board;
	public static int[][] enemyBoard = new int[10][10];;
	public int portaUDP;
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		System.out.println("[1] Player1\n[2] Player2");
		String numPlayer = s.nextLine();
		Player player = new Player(numPlayer);
		if(numPlayer.equals("2")) {
			player.printBoard();
			System.out.println("Esperando pela primeira jogada do player 1");
			System.out.println(player.waitTurn(player.portaUDP));
			
			System.out.println("Vez do player 2");
		}
		while(true) {
			player.printBoard();
			System.out.println("Posicao X: ");
			String positionX = s.nextLine();
			System.out.println("Posicao Y: ");
			String positionY = s.nextLine();
			player.sendPosition(positionX, positionY, player.portaUDP);
			String response = player.waitTurn(player.portaUDP).trim();
			ArrayList<String> listaPosicoesBarcos = player.getShipsPositions();
			if(listaPosicoesBarcos.contains(response)) {
				System.out.println("destruiu barco");
			}else {
				System.out.println("Errou");
			}
		}
	}

	public Player(String player) {
		Scanner s = new Scanner(System.in);
		if (player.equals("1")) {
			portaUDP = 62252;
		}else {
			portaUDP = 62251;
		}
		init();
		
	}
	
	public ArrayList<String> getShipsPositions(){
		ArrayList<String> positions = new ArrayList<String>();
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				if(board[i][j] == 1) {
					String linha = Integer.toString(i);
					String coluna = Integer.toString(j);
					positions.add(linha + ":" + coluna);
				}
			}
		}
		return positions;
	}
	
	public void init() {
		Board b = new Board();
		//Inicializa o tabuleiro do jogador com seus navios
		board = b.getBoard();
		//Inicializa a visão atual do tabuleiro do inimigo  
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				enemyBoard[i][j] = 0;
			}
		}
	}
	
	public void printBoard() {
		for (int[] linha : board) {
			for (int posicao : linha) {
				System.out.print(posicao);
			}
			System.out.println();
		}
	}
	
	public void sendPosition(String positionX, String positionY, int portaUDP){
		try {
			DatagramSocket ds = new DatagramSocket(portaUDP);
			
			String positions = positionX + ":" + positionY;
			
			byte[] b = positions.getBytes();
			
			InetAddress ia = InetAddress.getLocalHost();
			DatagramPacket dp = new DatagramPacket(b, b.length, ia, 1225);
			System.out.println("Enviando posição do tiro ao adversário...");
			ds.send(dp);
			ds.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public String waitTurn(int portaUDP) {
		try {
			DatagramSocket ds = new DatagramSocket(portaUDP);
			byte[] b1 = new byte[1024];
			DatagramPacket dp1 = new DatagramPacket(b1, b1.length);
			ds.receive(dp1);
			String str = new String(dp1.getData());
			
			ds.close();
			return str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
