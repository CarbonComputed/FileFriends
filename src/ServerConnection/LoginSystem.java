/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ServerConnection;

import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import Main.Constants;
import Main.User;


/**
 *
 * @author kevin
 */
public class LoginSystem {


public static boolean loginSuccess=false;
private String username;

public LoginSystem(String username,String password,JFrame component){
	try {
		checkInfo(username,password);
	} catch (InvalidInputException e) {
		// TODO Auto-generated catch block
		JOptionPane.showMessageDialog(component, e.getMessage());
		return;
	}
	login(username,password);
}



    public void checkInfo(String UserName,String Password) throws InvalidInputException{
        if(UserName.length()<6||UserName.length()>17){
            throw new InvalidInputException("UserName must be longer than 5 characters and shorter than 18 characters");
            
        }
        if(Password.length()<6||Password.length()>17){
  
            throw new InvalidInputException("Password must be longer than 5 characters and shorter than 18 characters");
        }
        if(UserName.contains("/")||UserName.contains("=")||UserName.contains(";")){
         
            throw new InvalidInputException("Bad Characters in UserName");
            
        }
        if(Password.contains("/")||Password.contains("=")||Password.contains(";")){
            throw new InvalidInputException("Bad Characters in Password");

        }
       
    }
    public synchronized void login(String UserName, String Password){
        MD5Encryption m=new MD5Encryption(Password);
        Password=m.getMD5();
       
        String login="LOGIN:"+UserName+":"+Password;
       String req= MainThread.msc.writetoServer(login);
      //System.out.println(MainThread.msc);
       if(req.startsWith("LOGIN:SUCCESS")){
         Constants.user = new User(UserName,"");
           
         synchronized (MainThread.Main) {
    MainThread.Main.notify();
}
         	String data[] = req.split(":");
         	if(data.length==3){
         		Constants.user.setCookie(data[2]);
         	}
         	//Constants.user=new User(UserName,"name");
           loginSuccess=true;
           return;
           
      
       }
       if(req.equals("LOGIN:FAILED")){
        loginSuccess=false;
        JOptionPane.showMessageDialog(null,
    "Login Failed",
    "",
    JOptionPane.ERROR_MESSAGE);


       }
       else{
           loginSuccess=false;
           
       }
    }
}
