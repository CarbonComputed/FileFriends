package Main;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SaveSettings {

	User me;
	public SaveSettings(User user){
		me=user;
	}
	
	public void writePersons(String filename) {
	        
	        ObjectOutputStream outputStream = null;
	        
	        try {
	            
	            //Construct the LineNumberReader object
	            outputStream = new ObjectOutputStream(new FileOutputStream(filename));
	            
	            
//	            outputStream.writeObject(person);

	            
	            
	        } catch (FileNotFoundException ex) {
	            ex.printStackTrace();
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        } finally {
	            //Close the ObjectOutputStream
	            try {
	                if (outputStream != null) {
	                    outputStream.flush();
	                    outputStream.close();
	                }
	            } catch (IOException ex) {
	                ex.printStackTrace();
	            }
	        }
	    }
	    
	    
	}
	

