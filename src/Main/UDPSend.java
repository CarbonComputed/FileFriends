package Main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class UDPSend implements Runnable {
	
	public static final int numberofPackets=20;
	private DatagramSocket udpClient;
	private ArrayList<Friend> friends;
	public static boolean interrupt;
	
	public UDPSend(FriendList friends){
		try {
			udpClient = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.friends=friends.getFriends();
	}
	public void sendPacket(InetAddress IP, int port){
		byte[] buf = "".toString().getBytes();
		DatagramPacket packet= new DatagramPacket(buf,buf.length,IP,port);
		try {
			udpClient.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!interrupt){
			for(Friend friend:friends){
				if(friend.isOnline()){
				for(int x=0;x<numberofPackets;x++){
					try {
						sendPacket(InetAddress.getByName(friend.getpublicIP()),friend.getPort());
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			}
		}
		
	}
}
