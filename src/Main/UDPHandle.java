package Main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPHandle extends Thread {
	
	private DatagramSocket udpClient;
	private DatagramPacket initPacket;
	private String data;
	public boolean punched=false;
	
	
	public UDPHandle(DatagramSocket udpClient,String data,DatagramPacket initPacket){
		this.udpClient=udpClient;
		this.data=data;
		this.initPacket=initPacket;
	}
	
	public UDPHandle(DatagramSocket udpClient,String data){
		this.udpClient=udpClient;
		this.data=data;
	}
	
	
	public void run(){
		downloadRequest();
		punchthrough();
		
	}
	public void downloadRequest(){
		System.out.println(data);
		if(data.equals("DownloadRequest")){
			DatagramSocket newSocket=null;
			try {
				newSocket =  new DatagramSocket();
			} catch (SocketException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			byte[] send =  new byte[320];
			int publicPort=0;
			String publicIP="";
			//REPLACE with domain name
			try {
				DatagramPacket getPublic = new DatagramPacket(send,send.length,InetAddress.getByName(Constants.domainName),64000);
				newSocket.send(getPublic);
				DatagramPacket Public = new DatagramPacket(send,send.length,initPacket.getAddress(),initPacket.getLength());
				
				
				
				newSocket.receive(Public);
				String[] data =  new String(Public.getData()).split(":");
				publicIP=data[0].trim();
                                Constants.user.setpublicIP(InetAddress.getByName(publicIP));
				//Constants.publicIP=publicIP;
				publicPort=Integer.parseInt(data[1].trim());
				
				
				
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			byte[] accept= ("DownloadAccepted:"+Integer.toString(publicPort)).getBytes();
			DatagramPacket acceptPacket =  new DatagramPacket(accept,accept.length,initPacket.getAddress(),initPacket.getPort());
			try {
				//for(int x=0;x<5;x++){
					udpClient.send(acceptPacket);
				//}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			new UploadThread(newSocket,acceptPacket).start();
		}
	}
	
	public void punchthrough(){
		if(data.equals("punching")){
			byte[] buf = new byte[32];
			DatagramPacket receive =  new DatagramPacket(buf,buf.length);
			try {
				udpClient.receive(receive);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String data = new String(receive.getData()).trim();
			if(data.equals("punching")){
				punched=true;
			}
		}
	}
	
	
}
