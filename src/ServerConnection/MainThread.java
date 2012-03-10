/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerConnection;

import GUIComponents.LoginForm;
import GUIComponents.OptionsMenu;

import java.io.FileNotFoundException;
import java.util.*;

import Main.Constants;
import Main.Directory;
import Main.Settings;
import Main.User;
import Main.Friend;

import Main.GenericFile;
import Main.stayAliveThread;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.util.Enumeration;

/**
 * 
 * @author kevin
 */
public class MainThread implements Runnable {

	public static Thread Main;
	static Thread serverConnection;
	LoginForm login = null;
	public static MainServerConnection msc;


	public MainThread() {
		// main = f;
		Main = new Thread(this);
		Main.start();
		msc = new MainServerConnection();
		serverConnection = new Thread(msc);
		serverConnection.start();

	}

	public void run() {
                
		ArrayList<Friend> users=null;
                
		showLoginForm();
		UDPServerConnect();
		try {
			users=getUserData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
                Constants.user.getSettings().setShare(null);
                openSettings();
           //     System.out.println(Constants.user.getSettings().getShare().getSize());
                System.out.println();
                if(Constants.user.getSettings().getShare()==null || Constants.user.getSettings().getDownLoadLocation()==null){
                  getSettings();  
                }
                new stayAliveThread().start();
	//	System.out.println(Constants.user.getSettings().getShare().getFiles().get(0).getName());
                
		
	}
        
        public void openSettings(){
            ArrayList<GenericFile> files=null;
        try {
            BufferedReader in  = new BufferedReader(new FileReader(Constants.user.getUserName()+".dat"));
            String str ="";
           
           files = new ArrayList<GenericFile>();
            
            while((str =in.readLine()) != null){
                //System.out.println(str);
                String[] data = str.split(":");
                if(data[0].equals("Share")){
                    File f = new File(data[1]);
                    if(f.exists()){
                      if(f.isDirectory()){
                          
                          Directory d = new Directory(data[1]);
                          files.addAll(d.getFiles());
                          
                      }
                      else{
                          files.add(new GenericFile(data[1])); 
                      }
                    }
                }
                if(data[1].equals("DDir")){
                    Constants.user.getSettings().setDownLoadLocation(data[1]);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(MainThread.class.getName()).log(Level.SEVERE, null, ex);
        
        }
     //   System.out.println(files.get(0).getName());
        Constants.user.getSettings().getShare().addFiles(files);
       // System.out.println(Constants.user.getSettings().getShare().getFiles().get(0).getName());

        //Constants.user.getSettings().setShare(files);
}
        
	public synchronized void showLoginForm() {
		login = new LoginForm();
		login.show();
		synchronized (Main) {
			try {
				Main.wait();
			} catch (InterruptedException ex) {
				Logger.getLogger(MainThread.class.getName()).log(Level.SEVERE,
						null, ex);
			}
		}

		login.dispose();

	}

	public synchronized void UDPServerConnect() {
		try {
			Constants.socket = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String IPandPort = null;
		try {

			Enumeration<NetworkInterface> nets = NetworkInterface
					.getNetworkInterfaces();
			for (NetworkInterface netint : Collections.list(nets)) {
				Enumeration<InetAddress> inetAddresses = netint
						.getInetAddresses();
				for (InetAddress inetAddress : Collections.list(inetAddresses)) {
					if ((inetAddress.getHostAddress().startsWith("192.168"))
							|| (inetAddress.getHostAddress().startsWith("10.0"))
							|| (inetAddress.getHostAddress()
									.startsWith("172.16"))) {
						Constants.user.setPrivateIP(InetAddress.getByName(inetAddress.getHostAddress()));
					}
				}
			}

			IPandPort = Constants.user.getprivateIP() + ":"
					+ Constants.socket.getLocalPort();

		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		byte[] data = (Constants.user.getCookie() + ":" + IPandPort).getBytes();
		DatagramPacket packet = null;
		try {
			packet = new DatagramPacket(data, data.length, InetAddress
					.getLocalHost(), Constants.UDPServerPort);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Constants.socket.send(packet);

			byte receiveData[] = new byte[100];
			DatagramPacket receive = new DatagramPacket(receiveData,
					receiveData.length);
			Constants.socket.receive(receive);
			String rData[] = new String(receive.getData()).trim().split(":");
			if (rData.length == 2) {
				Constants.user.setpublicIP(InetAddress.getByName(rData[0]));
				Constants.user.setPublicPort(Integer.parseInt(rData[1]));
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<Friend> getUserData() throws IOException {
		SSLSocket serverConn = null;
		ArrayList<Friend> users = null;
		try {
			// InetSocketAddress local = new
			// InetSocketAddress("127.0.0.1",5555);
			SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory
					.getDefault();
			serverConn = (SSLSocket) sslsocketfactory.createSocket("127.0.0.1",
					51414);
			final String[] enabledCipherSuites = { "SSL_DH_anon_WITH_RC4_128_MD5" };
			serverConn.setReuseAddress(true);
			serverConn.setEnabledCipherSuites(enabledCipherSuites);
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: Server ");
			// System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for "
					+ "the connection to: Server .");
			// System.exit(1);
		}

		PrintWriter out = new PrintWriter(serverConn.getOutputStream(), true);
		out.println("GETUSERINFO:" + Constants.user.getCookie() + ":" + Constants.user.getUserName());

		try {
			Main.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		InputStream is = serverConn.getInputStream();
		ObjectInputStream ois = new ObjectInputStream(is);

		try {
			System.out.println("Waiting for object "
					+ serverConn.getLocalPort());
		users = (ArrayList<Friend>) ois.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
               // System.out.println(users.size());
                Constants.user.setFriends(users);
		System.out.println("got object");
		out.close();
		ois.close();
		is.close();
		serverConn.close();
		return users;
	}
	
	public User getSettings(){
		//User user = new User(Constants.user.getUserName(),"");
		OptionsMenu options = new OptionsMenu(Constants.user);
		
		synchronized (Main) {
			try {
				Main.wait();
			} catch (InterruptedException ex) {
				Logger.getLogger(MainThread.class.getName()).log(Level.SEVERE,
						null, ex);
			}
		}

		options.dispose();
		return Constants.user;
	}
}
