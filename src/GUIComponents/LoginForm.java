package GUIComponents;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import ServerConnection.LoginSystem;

public class LoginForm extends JFrame {
	
	private JTextField Username;
	private JPasswordField Password;
	private static JFrame contentPane;
	public LoginForm(){
		super();
		contentPane=this;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel mainPanel = new JPanel(new BorderLayout());

		final JPanel panel = new JPanel(new SpringLayout());
		JPanel regPanel = new JPanel();

		
		JLabel FileFriends = new JLabel("FileFriends");
		
		JButton login = new JButton("Login");
		

		JLabel username = new JLabel("Username");
		JLabel password = new JLabel("Password");
		Username= new JTextField("",15);
		Password = new JPasswordField("",15);
                Password.addKeyListener(new KeyListener(){

            @Override
            public void keyTyped(KeyEvent ke) {
            //    throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                if(ke.getKeyCode()==KeyEvent.VK_ENTER){
                    new LoginSystem(Username.getText(),Password.getText(),contentPane);
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {
              //  throw new UnsupportedOperationException("Not supported yet.");
            }
                    
                });
		panel.add(username);
		panel.add(Username);
		panel.add(password);
		panel.add(Password);
		login.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new LoginSystem(Username.getText(),Password.getText(),contentPane);
			}
			
		});
		SpringUtilities.makeCompactGrid(panel,
                2, 2, //rows, cols
                6, 50,        //initX, initY
                6, 6); 
		
		
		JButton register = new JButton("Register");
		register.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new RegisterForm().show();
			}
			
		});

		mainPanel.add(panel,BorderLayout.CENTER);
		mainPanel.add(FileFriends,BorderLayout.NORTH);
		regPanel.add(register);
		regPanel.add(login);
		mainPanel.add(regPanel,BorderLayout.SOUTH);
		this.add(mainPanel);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		
		Username.requestFocusInWindow();
	}
	public static void main(String args[]){
        try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		new LoginForm();
	}
}
