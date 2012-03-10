package Main;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import org.apache.commons.codec.binary.Base64;

public class UDTStream extends DatagramSocket {
        
	private int packetnumber=0;
	private int packetreceivednumber=-1;
	private DatagramSocket socket;
	private InetAddress IP;
	private int remotePort;
	private NetEncryption crypt;
	public boolean clientClosed=false;
	private boolean encryptionInit=false;
	private static int buffersize=35000;
	
	public UDTStream(int Localport) throws SocketException{
		super(Localport);
                
		this.socket=this;
		//socket.setReceiveBufferSize(64100);
		initEncryption();
               
                
	}
	
	public UDTStream() throws SocketException{
		super();
		this.socket=this;
		initEncryption();
	}
	
	public void listen() throws Exception{
		DatagramPacket p = null;
		try {
			p=receive(0);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		this.IP=p.getAddress();
		this.remotePort=p.getPort();
		System.out.println(p.getLength());
		System.out.println(p.getPort());
		System.out.println("Client Accepted");
		crypt.receiveRSAKey();
		Thread.sleep(10);
		crypt.sendRSAKey();
		crypt.receiveAESkey();
		encryptionInit=true;
		System.out.println("Listening");
	}

	public void connect(InetAddress IP,int port){
		this.IP=IP;
		this.remotePort=port;
		try {
			//throw  new IOException();
			send("asdf".getBytes());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//System.out.println("test");
			if(initPunchthrough()==false){
				System.out.println("Unable to connect to host");
				e.printStackTrace();
			}
			else{
				System.out.println("Connection Established");
			}
		}
		System.out.println("Connection Established");
		crypt.sendRSAKey();
		crypt.receiveRSAKey();
		crypt.sendAESkey();
		encryptionInit=true;
		
	}
	
	public void initEncryption(){
		crypt = new NetEncryption(this);
		
		
	}
	
	public boolean initPunchthrough(){
		DatagramPacket packet = new DatagramPacket(new byte[1024],1024,IP,remotePort);
		//UDPHandle punch=new UDPHandle(socket,"punching");
	//	punch.start();
		byte[] data = "punching".getBytes();
		long endTimeMillis = System.currentTimeMillis() + 10000;
		//boolean punched=false;
		boolean timedout=false;
		while(System.currentTimeMillis()<=endTimeMillis){
			try {
				Thread.sleep((long) (Math.random()*1000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				socket.send(packet);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//DatagramPacket receive = new DatagramPacket(new byte[512],512);
			try {
				socket.setSoTimeout(500);
			} catch (SocketException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if(timedout==false){
				
			}
			
		}
		return false;
		
	}
	
	
	public void send(File file) throws IOException{
		
	}
	
	
	
	public void send(byte[] dat) throws IOException{
		DatagramPacket packet = new DatagramPacket(dat,dat.length,IP,remotePort);
		int timepassed=0;
		long starttime= System.currentTimeMillis();

		if(encryptionInit==true){
		byte[] encrypted = Base64.encodeBase64(crypt.encrypt(dat));
		
		packet.setData((new String(encrypted)+":"+packetnumber).getBytes());
		}
		else{
			packet.setData((new String(Base64.encodeBase64(dat))+":"+packetnumber).getBytes());
		//	System.out.println(new String(packet.getData()));
		}
		while(true&&timepassed<=30000){
			boolean timeout=false;

			socket.send(packet);
			byte[] buf = new byte[1024];
			DatagramPacket receive = new DatagramPacket(buf,buf.length);
			socket.setSoTimeout(3000);
			try{
				socket.receive(receive);
			}catch (InterruptedIOException iioe)
			{
				System.err.println("missed packet");
				timeout=true;
			}
			if(timeout==false){
				String data = new String(receive.getData()).trim();
		//		System.out.println(data);
				if(data.equals("ACK:"+packetnumber)){
					
					packetnumber++;
					return;
				}
			}
			
			timepassed=(int)(System.currentTimeMillis()-starttime);
		//	System.out.println(timepassed);
		}
		throw new IOException("Socket timed out");
	}
	
	public DatagramPacket receive(int timeout) throws IOException{
	//	System.out.println("called");
		socket.setSoTimeout(timeout);
		
		int parsedpacket=-2;
		DatagramPacket receive=null;
		while(packetreceivednumber!=parsedpacket){
			byte buf[] = new byte[65000];
			receive = new DatagramPacket(buf,buf.length);
			boolean timedout=false;
			try{
				socket.receive(receive);
			}catch (InterruptedIOException iioe)
				{
					System.out.println("TIMEOUT");
				//	packetreceivednumber--;
					throw new IOException();
					
				}
		
			String[] dat=null;
			byte[] internalData = null;
			//internalData = Utilities.removeTrailingNulls(receive.getData());
			//System.out.println("packet "+new String(receive.getData()));
			dat = new String(receive.getData()).trim().split(":");
			parsedpacket = Integer.parseInt(dat[dat.length-1]);
			internalData =  Base64.decodeBase64(Arrays.copyOf(receive.getData(),receive.getData().length-( ":"+parsedpacket).getBytes().length));
			//System.out.println("packetnumber "+dat[1]);
			//System.out.println(new String(internalData));
			if(encryptionInit==true){
				
			String decrypted = new String(crypt.decrypt(internalData));
			receive.setData(crypt.decrypt(internalData));
		//	receive.setData(crypt.decrypt(Base64.decodeBase64(dat[0].getBytes())));
			//dat=decrypted.split(":");
			}
			else{
				//System.out.println(new String(Base64.encodeBase64(internalData)));
				receive.setData(internalData);
			}
			
			if(dat.length<2){
				sendACK(receive.getAddress(),receive.getPort(),-2);

				System.err.println("Malformed packet");
				return receive;
			}
			if(new String(internalData).equals("CLOSE")){
				clientClosed=true;
			}
			
			
			if(parsedpacket<0){
				throw new IOException();
			}
			sendACK(receive.getAddress(),receive.getPort(),parsedpacket);
			//data = Utilities.removeTrailingNulls(receive.getData());
			//System.out.println(data.length-(dat[dat.length-1].getBytes().length+1));
		//	System.out.println(data.length);
			//if(dat.length>1){
			
		//	byte[] ndata=Arrays.copyOf(data, data.length);
			//System.out.println(Character.valueOf((((char)data[data.length-1]))));
			//data=Arrays.copyOf(data, data.length-(dat[dat.length-1].getBytes().length+1));}
		//	receive.setData(data);
		}
			
			return receive;
	}
	
	
	private void sendACK(InetAddress address,int port,int parsedpacket) throws IOException{
		DatagramPacket send= new DatagramPacket(("ACK:"+parsedpacket).getBytes(),("ACK:"+parsedpacket).getBytes().length,address,port);
		packetreceivednumber++;
		socket.send(send);
	}
	public static void main(String args[]){
		//try {
			//System.out.println((int) (Math.random()*2000));
			UDTStream udt=null;
        try {
            udt = new UDTStream(20000);
        } catch (SocketException ex) {
            Logger.getLogger(UDTStream.class.getName()).log(Level.SEVERE, null, ex);
        }
		/*	try {
				udt.connect(InetAddress.getByName("127.0.0.1"),9999);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}base64Data
		*/
			try{
			//udt.listen();
				udt.listen();
			}catch(Exception e){
				e.printStackTrace();
			}
		    try {
				System.out.println(new String(udt.receive(10000).getData()));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
                    /*
			try {
				FileOutputStream fos = new FileOutputStream(new File("/Users/kevin/Documents/asdf.mkv"));
				while(!udt.clientClosed){
					DatagramPacket packet = udt.receive(10000);
					try{
					fos.write(packet.getData(), 0, buffersize);
					}catch (IndexOutOfBoundsException e){
						fos.close();
						return;
					}
				}
				fos.close();
				//System.out.println(new String(Base64.decodeBase64(udt.receive(30000).getData())));
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	*/
	
}
}
