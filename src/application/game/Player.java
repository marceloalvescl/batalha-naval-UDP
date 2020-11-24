package application.game;

import java.io.IOException;
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

	public Player(String player) {
		if (player.equals("1")) {
			portaUDP = 62252;
		}else {
			portaUDP = 62251;
		}
		init();
		
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
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		System.out.println("[1] Player1\n[2] Player2");
		String numPlayer = s.nextLine();
		Player player = new Player(numPlayer);
		if(numPlayer.equals("2")) {
			
			player.printBoard();
			
			System.out.println("Esperando pela primeira jogada do player 1");
			String response  = player.waitTurn(player.portaUDP).trim();			
			System.out.println("Vez do player 2");
		
		}
		while(true) {
			player.printBoard();
			
			String positions = player.getAttemptPosition();			
			player.sendPosition(positions, player.portaUDP);
			String response = player.waitTurn(player.portaUDP).trim();
			System.out.println(response + " --- ");
			
		}
	}
	
	public String getAttemptPosition() {

		Scanner s = new Scanner(System.in);
		
		System.out.println("Posicao X: ");
		String positionX = s.nextLine();
		System.out.println("Posicao Y: ");
		String positionY = s.nextLine();
		
		String positions = positionX + ":" + positionY;
		return positions;
	}

	public void sendPosition(String positions, int portaUDP){
		try {
			DatagramSocket ds = new DatagramSocket(portaUDP);
			
			byte[] b = new String(positions).getBytes();
			
			InetAddress ia = InetAddress.getLocalHost();
			DatagramPacket dp = new DatagramPacket(b, b.length, ia, 1225);
			System.out.println("Enviando posição do tiro ao adversário...");
			ds.send(dp);
			ds.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void sendResponse(String response, int portaUDP){
		try {
			DatagramSocket ds = new DatagramSocket(portaUDP);
			
			byte[] b = response.getBytes();
			
			InetAddress ia = InetAddress.getLocalHost();
			DatagramPacket dp = new DatagramPacket(b, b.length, ia, 1225);
			System.out.println("Enviando ao servidor: " + response);
			ds.send(dp);
			ds.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getResponse(int portaUDP) {
		try{
			DatagramSocket ds = new DatagramSocket(portaUDP);
			byte[] b1 = new byte[1024];
			DatagramPacket dp1 = new DatagramPacket(b1, b1.length);
			ds.receive(dp1);
			String response = new String(dp1.getData());
			System.out.println(response);
			ds.close();
			return response.trim();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public String waitTurn(int portaUDP) {
		try {
			String response = getResponse(portaUDP);
			if(response.substring(1,2).equals(":")) {
				ArrayList<String> listaPosicoesBarcos = getShipsPositions();
				if(listaPosicoesBarcos.contains(response)) {
					response = new String("Destruiu barco");
				} else {
					response = new String("Errou");
				}
			}else {
				response = new String("Sua vez");
			}
			sendResponse(response, portaUDP);
			String newResponse = getResponse(portaUDP);
			return newResponse;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
	
	public void printBoard() {
		for (int[] linha : board) {
			for (int posicao : linha) {
				System.out.print(posicao);
			}
			System.out.println();
		}
	}
	
	
}
