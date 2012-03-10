package Main;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class test {
	public test(int port) throws FileNotFoundException{
		int[] b = new int[6535300];
		System.out.println(b.length);
		DatagramSocket socket=null;
		try {
			socket =new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] punching = "punching".getBytes();
		try {
			DatagramPacket packet = new DatagramPacket(punching,punching.length,InetAddress.getLocalHost(),port);
			try {
				socket.send(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FileOutputStream fos = new FileOutputStream("test.png");
			byte[] File = "File:asdasdf".getBytes();
			packet = new DatagramPacket(File,File.length,InetAddress.getLocalHost(),port);
			try {
				socket.send(packet);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for(int x=0;x<200;x++){
				byte[] buf=new byte[1000];
				DatagramPacket p= new DatagramPacket(buf,buf.length);
				try {
					socket.receive(p);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				ArrayList<Byte> l = new ArrayList<Byte>();
				for(int y=0;y<buf.length;y++){
					l.add(Byte.valueOf(p.getData()[y]));
				}
				int ctr = 0;
				for(int y=l.size()-1;y>=0;y--){

					if(l.get(y)=='\u0000'){
						l.remove(y);
					}
					else{
						break;
					}
					if(l.size()==535){
						break;
						}
					ctr++;
				}
				byte[] dat= new byte[l.size()];
				for(int y=0;y<dat.length;y++){
					dat[y]=Byte.parseByte(l.get(y).toString());
				}
				System.out.println(dat.length);
				
				try {
					fos.write(dat);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			test t = new test(38422);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
