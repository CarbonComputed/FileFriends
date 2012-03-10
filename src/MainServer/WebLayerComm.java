package MainServer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class WebLayerComm extends Thread{
	private int webCommPort=51412;
    private SSLServerSocket webComm;
    private boolean isStopped = false;

    
	public WebLayerComm(){
        initWebComm();
	}
	
    public void initWebComm(){
    	try {
            SSLServerSocketFactory sslserversocketfactory =
                    (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
                   

            webComm = (SSLServerSocket) sslserversocketfactory.createServerSocket(webCommPort);
             final String[] enabledCipherSuites = { "SSL_DH_anon_WITH_RC4_128_MD5" };
             webComm.setEnabledCipherSuites(enabledCipherSuites);

        } catch (IOException ex) {
            Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void getTCPRequestLoop() {
    	SSLSocket clientSocket = null;
    	Thread handle = null;
        while (!isStopped) {
            
            try {
                clientSocket = (SSLSocket) webComm.accept();

                
            } catch (IOException e) {
                if (isStopped) {
                    System.out.println("Server Stopped.");
                    return;
                }
                throw new RuntimeException(
                        "Error accepting client connection", e);
            }

           handle= new Thread(new HandleRequests(clientSocket,0));
           handle.start();

            
        }
        
        try {
			clientSocket.close();
			handle.join();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void run(){
    	getTCPRequestLoop();
    }
}
