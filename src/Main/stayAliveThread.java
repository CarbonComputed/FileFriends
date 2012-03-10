package Main;

import java.net.DatagramPacket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class stayAliveThread extends Thread {
	
	private DatagramPacket keepalive;
	
        public stayAliveThread(){
            
        }
	@Override
	public void run() {
		// TODO Auto-generated method stub
            while(true){
            for(int x=0;x<Constants.user.getFriends().size();x++){
                
                System.out.println(Constants.user.getFriends().get(x).toString());
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(stayAliveThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
		
	}

}
