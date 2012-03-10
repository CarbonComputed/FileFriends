/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ServerConnection;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


/**
 *
 * @author kevin
 */
public class RegisterSystem {

	private JFrame frame;
    public RegisterSystem(String UserName,String password,String confirmpassword,String Name,JFrame frame){
    	this.frame=frame;
        try {
			verifyInfo(UserName,password,confirmpassword,Name);
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage());
			return;
		}
         
        
        sendInfo(UserName,password,Name);
    }
    public void verifyInfo(String UserName,String password,String confirmpassword,String Name) throws InvalidInputException{
        if(UserName.length()<6||UserName.length()>17){
            throw new InvalidInputException("UserName must be longer than 5 characters and shorter than 18 characters");
        }
        if(password.length()<6||password.length()>17){
            throw new InvalidInputException("Password must be longer than 5 characters and shorter than 18 characters");

        }
        if(Name.length()>50){
            throw new InvalidInputException("Name must be shorter than 50 characters");

        }
        if(!confirmpassword.equals(password)){
            throw new InvalidInputException("Make sure your password is correct");

        }
        
    }

    @SuppressWarnings("deprecation")
    public void sendInfo(String UserName,String Password,String Name){
        MD5Encryption md5=new MD5Encryption(Password);

        String send="REGISTER:"+UserName+":"+md5.getMD5()+":"+Name;
        String rback=MainThread.msc.writetoServer(send);
        if(rback.equals("REGISTER:Failed")){
            JOptionPane.showMessageDialog(null,
    "UserName already in use",
    "",
    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(rback.equals("REGISTER:Complete")){
            JOptionPane.showMessageDialog(null, "Registration Complete!");
            frame.dispose();
            return;

        }
    }
}
