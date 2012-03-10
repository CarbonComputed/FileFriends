/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package MainServer;

/**
 *
 * @author kevin
 */
import java.io.*;
import java.net.*;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import ServerConnection.MainServerConnection;

public class tcpTest {
    public static void main(String[] args) throws IOException {

        SSLSocket serverConn = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            try {
               // InetSocketAddress local = new InetSocketAddress("127.0.0.1",5555);
                SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            serverConn = (SSLSocket) sslsocketfactory.createSocket("127.0.0.1", 51414);
            final String[] enabledCipherSuites = { "SSL_DH_anon_WITH_RC4_128_MD5" };
               serverConn.setReuseAddress(true);

              serverConn.setEnabledCipherSuites(enabledCipherSuites);
                //serverConn = new Socket("127.0.0.1",serverPort);
                //serverConn.bind(local);
                //serverConn.connect(new InetSocketAddress("127.0.0.1",serverPort));
             } catch (UnknownHostException e) {
            System.err.println("Don't know about host: Server ");
            //System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                               + "the connection to: Server .");
            //System.exit(1);
        }


           // SocketAddress sockaddr = new InetSocketAddress("127.0.0.1", 55555);
            
            out = new PrintWriter(serverConn.getOutputStream(), true);
          //  out.write("test");
            in = new BufferedReader(new InputStreamReader(serverConn.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(MainServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
       

	while(serverConn.isConnected()){
        Scanner scan = new Scanner(System.in);
        String d= scan.next();
		out.println("SERVER:"+d);
		String i = in.readLine();
		
		System.out.println(i);
	}

	
    }
}
