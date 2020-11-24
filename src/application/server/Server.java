package application.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {

	public static void main(String args[]) throws Exception {

		int porta = 1225;
		int numConn = 1;
		
		DatagramSocket serverSocket = new DatagramSocket(porta);

		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];

		while (true) {

			DatagramPacket receivePacket = new DatagramPacket(receiveData,
					receiveData.length);
			System.out.println("Esperando por datagrama UDP na porta " + porta);
			serverSocket.receive(receivePacket);
			System.out.print("Datagrama UDP [" + numConn + "] recebido...");
			
			sendToOtherPlayer(receivePacket, sendData, serverSocket);

			/*
			
			String sentence = new String(receivePacket.getData());
			String capitalizedSentence = sentence.toUpperCase();

			sendData = capitalizedSentence.getBytes();

			DatagramPacket sendPacket = new DatagramPacket(sendData,
					sendData.length, IPAddress, port);
			
			System.out.print("Enviando " + capitalizedSentence + "...");

			serverSocket.send(sendPacket);
			System.out.println("OK\n");
			*/
		}
	}
	
	public static void sendToOtherPlayer(DatagramPacket receivedPacket,byte[] sendData, DatagramSocket serverSocket) {
		
		int port = receivedPacket.getPort();
		System.out.println("Porta: " + port);
		if(port == 62252) {
			port = 62251;
		}else {
			port = 62252;
		}
		
		String sentence = new String(receivedPacket.getData());
		System.out.println(sentence);
		
		InetAddress IPAddress = receivedPacket.getAddress();

		sendData = sentence.getBytes();

		DatagramPacket sendPacket = new DatagramPacket(sendData,
				sendData.length, IPAddress, port);
		
		System.out.print("Enviando " + sentence + "...");
		
		try {
			serverSocket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("OK\n");
	}
	
}
