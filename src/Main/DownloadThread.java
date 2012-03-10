package Main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class DownloadThread extends Thread{
	
	private DatagramSocket receive;
	private DatagramPacket receiveData;
	private Friend currentFriend;
	private String request;
	public static final int timeout=60000;
	public static final int numberofPackets=200;
	
	public DownloadThread(Friend friend,String request){

		this.currentFriend=friend;
		this.request=request;
		try {
			receive = new DatagramSocket(52000);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		receiveData = new DatagramPacket(new byte[1024],1024);
		
	}
	public boolean initPunchthrough(){
		InetAddress host=null;
		int hostPort=0;
		if(Constants.user.getpublicIP().equals(currentFriend.getpublicIP())){
			host=Constants.user.getprivateIP();
			hostPort=51412;
		}
		else{
			host=Constants.user.getpublicIP();
			hostPort=currentFriend.getPort();
		}
		for(int x=0;x<200;x++){
                    
                }
		return true;
	}
	public void receiveData(){
		
	}
	
	public void run() {
		// TODO Auto-generated method stub
		
		if(initPunchthrough()==false){
			System.out.println("punchthrough failed");
			return;
		}
		
	}

	
	public static void main(String args[]){
		//Constants.publicIP="0.0.0.0";
		//Constants.privateIP="192.168.2.6";
		new DownloadThread(new Friend("kevin","kmc","192.168.2.6","0.0.0.0",51412,63465,true),"File:asdfsadf:fasdf").start();
	}
}
