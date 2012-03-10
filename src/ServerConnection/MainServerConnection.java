/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ServerConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 *
 * @author kevin
 */
public class MainServerConnection implements Runnable {
    SSLSocket serverConn=null;
    int serverPort=51414;
    int localPort=55555;
    String clientpublicKey=null;
    PublicKey pk=null;
    PrintWriter out = null;
    BufferedReader in = null;
    
    
    public MainServerConnection(){

    }

    public void run(){
        try {
            try {
               // InetSocketAddress local = new InetSocketAddress("127.0.0.1",5555);
                SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            serverConn = (SSLSocket) sslsocketfactory.createSocket("127.0.0.1", serverPort);
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
       
        //handleEncryption();


    }
   
  public String writetoServer(String outRequest){
            
            out.println(outRequest);
       // System.out.println(outRequest);
        try {
            String i=in.readLine();
           // System.out.println(i);
            return i;
        } catch (IOException ex) {
            Logger.getLogger(MainServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
            return null;
}
}
