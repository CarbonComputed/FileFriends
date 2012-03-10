package GUIComponents;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import ServerConnection.RegisterSystem;

public class RegisterForm extends JFrame {
	public RegisterForm(){
		final JFrame frame= this;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel(new SpringLayout());
		final JTextField Username =  new JTextField("",15);
		final JTextField Name =  new JTextField("",20);
		final JPasswordField password = new JPasswordField("",15);
		final JPasswordField confirmpassword = new JPasswordField("",15);
		JButton register = new JButton("Register");
		JLabel UsernameLabel = new JLabel("Username");
		JLabel passwordLabel = new JLabel("Password");
		JLabel nameLabel = new JLabel("Name");
		JLabel confirmPassword = new JLabel("Confirm Password");
		panel.add(UsernameLabel);
		panel.add(Username);
		panel.add(nameLabel);
		panel.add(Name);
		panel.add(passwordLabel);
		panel.add(password);
		panel.add(confirmPassword);
		panel.add(confirmpassword);
		SpringUtilities.makeCompactGrid(panel,
                4, 2, //rows, cols
                6, 50,        //initX, initY
                6, 6); 
		register.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new RegisterSystem(Username.getText(),password.getText(),confirmpassword.getText(),Name.getText(),frame);
			}
			
		});
		JPanel reg = new JPanel();
		reg.add(register);
		add(panel,BorderLayout.CENTER);
		add(reg,BorderLayout.SOUTH);
		this.pack();
		this.setVisible(true);

	}
	public static void main(String args[]){
		new RegisterForm();
	}
}
