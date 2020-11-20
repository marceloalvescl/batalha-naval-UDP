package application.client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {

	public static String sendPosition(String positionX, String positionY) throws Exception{
		DatagramSocket ds = new DatagramSocket();
		String positions = positionX + ":" + positionY;
		byte[] b = positions.getBytes();
		
		InetAddress ia = InetAddress.getLocalHost();
		DatagramPacket dp = new DatagramPacket(b, b.length, ia, 1338);
		ds.send(dp);
		
		byte[] b1 = new byte[1024];
		DatagramPacket dp1 = new DatagramPacket(b1, b1.length);
		ds.receive(dp1);
		
		String str = new String(dp1.getData());
		ds.close();
		return str;
	}
	
}
