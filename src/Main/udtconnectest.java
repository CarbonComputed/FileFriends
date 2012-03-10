package Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;

public class udtconnectest {
	public static void main(String args[]){
		int buffersize=35000;

		//try {
			UDTStream udt=null;

        try {
            udt = new UDTStream(20002);
            /*	try {
            udt.connect(InetAddress.getByName("127.0.0.1"),9999);
            } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            }
             */
        } catch (SocketException ex) {
            Logger.getLogger(udtconnectest.class.getName()).log(Level.SEVERE, null, ex);
        }
			
			//udt.listen();
                        
			try {
				udt.connect(InetAddress.getByName("127.0.0.1"),20000);
			} catch (UnknownHostException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
                      try {
						UDTStream us = new UDTStream();
						
					} catch (SocketException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			/*
			long dataread=0;;
			try {
				File file = new File("/Users/kevin/Movies/Fast Five.mkv");
				FileInputStream fis = new FileInputStream(file);
				while(dataread<=file.length()){
					byte[] data = new byte[buffersize];
					try {
						System.out.println((float)((float)dataread/(float)file.length())*100+ "%");
						fis.read(data);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						udt.send(data);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						Thread.sleep(0);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					dataread+=buffersize;
				}
				try {
					System.out.println("Closing");
					udt.send(("CLOSE".getBytes()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
                         
                         
	}
                         
                         */
                
                }
}
