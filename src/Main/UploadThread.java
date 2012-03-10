package Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;

public class UploadThread extends Thread {

	private DatagramSocket sender;
	private DatagramPacket packet;
	public static final int timeout=60000;
	public static final int numberofPackets=200;
	
	public UploadThread(DatagramSocket sender,DatagramPacket packet){
		this.sender=sender;
		this.packet=packet;
		
		
		
		
	}
	
	public boolean initPunchthrough(DatagramPacket packet){
		UDPHandle punch=new UDPHandle(sender,"punching");
		punch.start();
		byte[] data = "punching".getBytes();
		long endTimeMillis = System.currentTimeMillis() + timeout;
		
		while(punch.punched==false&&System.currentTimeMillis()<=endTimeMillis){
			packet.setData(data);
			try {
				sender.send(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				this.sleep(25);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(punch.punched==true){
			System.out.println("punched");
			return true;
		}
		if(System.currentTimeMillis()>=endTimeMillis){
		//	System.out.println("failed");
			return false;
		}
		return true;
		
	}
	
	
	public void sendFile(String fileName,DatagramPacket whoIs){
		//GenericFile file = FileList.search(fileName);
		GenericFile file = new GenericFile(".mp3","file","/home/kevin/Pictures/batman.png",new File("/home/kevin/Pictures/batman.png").length());
		if(file ==  null){
			System.out.println("file is null");
			return;
		}
		
			DivideandSend(file,whoIs);
			//receive ack
			
		
		
		
		
	}
	
	public static ArrayList<byte[]> divideFile(GenericFile file){
		long numPackets=(long)numberofPackets;
		if(file.getSize()<numberofPackets){
			numPackets=1;
		}
		System.out.println(file.getSize());
		File f= new File(file.getLocation());
		InputStream is = null;
		
		ArrayList<byte[]> data = new ArrayList<byte[]>();
		try {
			is = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int x=0;x<numPackets;x++){
			
			//String header= "File:"+Integer.toString(x)+":"+Integer.toString(x);
			String header="";
			int bufferLength =  (int) (file.getSize()/numPackets)+header.getBytes().length;
			System.out.println(bufferLength);
			int leftover =  (int) (file.getSize()%numPackets);
			byte[] buffer=null;
			if(x==numPackets-1){
				
				buffer =  new byte[bufferLength+leftover];
			}
			else{
				buffer =  new byte[bufferLength];
			}
			System.arraycopy(buffer,0, header.getBytes(),0,header.getBytes().length);
			int offset = header.getBytes().length;
		    int numRead = 0;
		    try {
		    	//offset+=packetOffset;
				while (offset < buffer.length
				       && (numRead=is.read(buffer, offset, buffer.length)) >= 0) {
				    offset += numRead;
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("offset "+ offset);
		    // Ensure all the bytes have been read in
		    if (offset < buffer.length) {
		        try {
					throw new IOException("Could not completely read file "+file.getName());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		    data.add(buffer);
		    data.remove(0);
		    // Close the input stream and return bytes
		    
		}
		try {
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    return data;
		
		
		
	}
	public void DivideandSend(GenericFile file,DatagramPacket whoIs){
		long numPackets=(long)numberofPackets;
		UDTStream udt=null;
		try {
			udt = new UDTStream();
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		

		if(file.getSize()<numberofPackets){
			numPackets=1;
		}
		System.out.println(file.getSize());
		File f= new File(file.getLocation());
		InputStream is = null;
		

		try {
			is = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int x=0;x<numPackets;x++){
			
			//String header= "File:"+Integer.toString(x)+":"+Integer.toString(x);
			String header="";
			int bufferLength =  (int) (file.getSize()/numPackets)+header.getBytes().length;
			System.out.println(bufferLength);
			int leftover =  (int) (file.getSize()%numPackets);
			byte[] buffer=null;
			if(x==numPackets-1){
				
				buffer =  new byte[bufferLength+leftover];
			}
			else{
				buffer =  new byte[bufferLength];
			}
			System.arraycopy(buffer,0, header.getBytes(),0,header.getBytes().length);
			int offset = header.getBytes().length;
		    int numRead = 0;
		    try {
		    	//offset+=packetOffset;
				while (offset < buffer.length
				       && (numRead=is.read(buffer, offset, buffer.length)) >= 0) {
				    offset += numRead;
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("offset "+ offset);
		    // Ensure all the bytes have been read in
		    if (offset < buffer.length) {
		        try {
					throw new IOException("Could not completely read file "+file.getName());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
			DatagramPacket filePacket = new DatagramPacket(buffer,buffer.length,whoIs.getAddress(),whoIs.getPort());
		    System.out.println("sending packet");
			System.out.println(filePacket.getLength());
			try {
				udt.send(filePacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				this.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    // Close the input stream and return bytes
		    
		}
		try {
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		
	}
	

	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(initPunchthrough(packet)==false){
			System.out.println("punchthrough failed");
			return;
		}
		try {
			this.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		byte[] buf =  new byte[1024];
		DatagramPacket request = new DatagramPacket(buf,buf.length);
		try {
			sender.receive(request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] data = new String(request.getData()).split(":");
		if(data[0].trim().equals("File")){
			String fileName=data[1].trim();
			//
			System.out.println(data[0]+data[1]);
			sendFile(fileName,request);
			
		}
	}
	public static void main(String args[]){
		GenericFile file = new GenericFile(".mp3","file","/home/kevin/Videos/Meteorite.The Hunt for Red October.mkv",new File("/home/kevin/Videos/Meteorite.The Hunt for Red October.mkv").length());
		if(file ==  null){
			System.out.println("file is null");
			return;
		}
		ArrayList<byte[]> bytes=divideFile(file);
	}
		
}
