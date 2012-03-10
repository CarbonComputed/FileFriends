package GUIComponents;

import Main.Constants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Main.Directory;
import Main.GenericFile;
import Main.User;
import ServerConnection.MainThread;
import java.io.FileOutputStream;
import java.io.FileWriter;

public class OptionsMenu extends JFrame {

	private ArrayList files;
	private String downloadLoc;
	public OptionsMenu(final User u){
		files= new ArrayList();
		final JFrame frame = this;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel =  new JPanel();
		JPanel midPanel = new JPanel();
		JPanel rightbuttons =  new JPanel();
		rightbuttons.setLayout(new BoxLayout(rightbuttons,BoxLayout.PAGE_AXIS));
		JPanel list = new JPanel(new BorderLayout());
		JLabel shareLabel = new JLabel("Share List");
		JLabel dlLabel = new JLabel("Download Location");
		final JList sharelist = new JList();
		JScrollPane scroll = new JScrollPane(sharelist);
		sharelist.setVisibleRowCount(5);
		final JTextField downloadFolder =  new JTextField("",25);
		JButton dlFolder = new JButton("Browse...");
		JPanel bottom =  new JPanel();
		JButton ok =  new JButton("OK");
		JButton cancel =  new JButton("Cancel");
		ButtonIcon add = new ButtonIcon("images/plus-icon.png",16,16);
		add.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JFileChooser browse =  new JFileChooser();
				browse.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				
				int file=browse.showOpenDialog(null);

				if(file==JFileChooser.CANCEL_OPTION){
					return;
				}
				File loc= browse.getSelectedFile();
				if(loc==null){
					return;
				}
				if(loc.isDirectory()){
					Directory d = new Directory(loc.getAbsolutePath());
					u.getSettings().getShare().addFiles(d.getFiles());
					files.add(d);
				}
				else{
					GenericFile f=new GenericFile(loc.getName().substring(loc.getName().lastIndexOf("."))
							,loc.getName(),loc.getAbsolutePath(),loc.length());
					files.add(f);
				}
				
				sharelist.setListData(files.toArray());
				
			}
			
		});
		
		add.setOpaque(false);
		add.setContentAreaFilled(false);
		ButtonIcon remove = new ButtonIcon("images/minus-icon.png",16,16);
		remove.setOpaque(false);
		remove.setContentAreaFilled(false);
		remove.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int index =sharelist.getSelectedIndex();
				if(index!=-1){
					files.remove(index);
				}
				sharelist.setListData(files.toArray());

			}
			
		});
		dlFolder.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JFileChooser browse = new JFileChooser();
				browse.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int file = browse.showOpenDialog(null);
				if(file !=JFileChooser.CANCEL_OPTION){
					downloadFolder.setText(browse.getSelectedFile().getAbsolutePath());
					downloadLoc=browse.getSelectedFile().getAbsolutePath();
				}
			}
			
		});
		ok.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				writeSettings(u);
				frame.dispose();
				synchronized (MainThread.Main) {
				    MainThread.Main.notify();
				}
			}
			
		});
		cancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				frame.dispose();
			}
			
		});
		rightbuttons.add(add);
		rightbuttons.add(Box.createRigidArea(new Dimension(0,5)));
		rightbuttons.add(remove);
		list.add(shareLabel,BorderLayout.WEST);
		list.add(scroll,BorderLayout.CENTER);
		midPanel.add(dlLabel);
		midPanel.add(downloadFolder);
		midPanel.add(dlFolder);
		panel.add(list);
		panel.add(rightbuttons);
		bottom.add(cancel);
		bottom.add(ok);
		add(panel,BorderLayout.NORTH);
		add(midPanel,BorderLayout.CENTER);
		add(bottom,BorderLayout.SOUTH);
		
		pack();
		setVisible(true);
	}
	
	
	private void writeSettings(User u){
        FileOutputStream fos = null;
        try {
            u.getSettings().setDownLoadLocation(downloadLoc);
            fos = new FileOutputStream(new File(Constants.user.getUserName()+".dat"));
            for(Object object:files){
                    if(object instanceof Directory){
                            Directory dir = (Directory) object;
                            u.getSettings().getShare().getFiles().addAll(dir.getFiles());
                            fos.write(("Share:"+dir.getAbsolutePath()+"\n").getBytes());
                            //fos.
                            //fos.
                    }
                    if(object instanceof GenericFile){
                            GenericFile f = (GenericFile) object;
                            u.getSettings().getShare().addFile(f);
                            fos.write(("Share:"+f.getAbsolutePath()+"\n").getBytes());
                    }
            }
            //u.getSettings().setDownLoadLocation(downloadLoc);
            fos.write(("DDir:"+downloadLoc+"\n").getBytes());
        } catch (IOException ex) {
            Logger.getLogger(OptionsMenu.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(OptionsMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                
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
		new OptionsMenu(new User("KMC392","Kevin"));
	}
}
