package Video9;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class GUI extends JFrame implements ActionListener {
	
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem addMenuItem;
	private JLabel label;
	
	public GUI() {
		//Create and set up the window.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // size of window
        setSize(400, 500);
        
        // menu bar
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        addMenuItem = new JMenuItem("Add");
        addMenuItem.addActionListener(this);
        
        fileMenu.add(addMenuItem);
        
        menuBar.add(fileMenu);
        
        setJMenuBar(menuBar);
 
        //Display the window.
        setVisible(true);
	}
	
	public void actionPerformed (ActionEvent e) {
		Object source = e.getSource();
		if (source == addMenuItem) {
			handleAddImageEvent();
		}
	}
	
	protected void handleAddImageEvent() {
		System.out.println("hi");
	}

}
