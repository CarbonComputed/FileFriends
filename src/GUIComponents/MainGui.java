/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIComponents;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 *
 * @author kevin
 */
public class MainGui {
    public MainGui(){
        JFrame window = new JFrame("FileFriends");
        JPanel topPanel = new JPanel();
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        JLabel header = new JLabel("FileFriends");
        topPanel.add(header);
        JTable filelist= null;
        initFileTable(filelist);
        JTable downloads = new JTable();
        JList friends = new JList();
        
        
    }
    
    private void initFileTable(JTable table){
        
       
        //table = new JTable(columnNames);
        
    }
    public static void main(String args[]){
        
    }
}
