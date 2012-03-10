package Main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class UDPReceive extends Thread implements Runnable {
	
	public static final int numberofPackets=20;
	private DatagramSocket udpClient;
	private ArrayList<Friend> friends;
	public static boolean interrupt;
	
	public UDPReceive(){
		try {
			udpClient = new DatagramSocket(51412);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//this.friends=friends.getFriends();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!interrupt){
			byte[] buf = new byte[512];
			DatagramPacket packet =  new DatagramPacket(buf,buf.length);
			try {
				udpClient.receive(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String data =  new String(packet.getData()).trim();
			new UDPHandle(udpClient,data,packet).start();
			
		}
		
	}
	public static void main(String args[]){
		UDPReceive rec = new UDPReceive();
		rec.start();
	}
}