package GUIComponents;

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ButtonIcon extends JButton {
	public ButtonIcon(String image,int width,int height){
		super();
		ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(image));
	    Image img = icon.getImage();
	    Image newimg = img.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH);  
	    super.setIcon(new ImageIcon(newimg));
	}
}
