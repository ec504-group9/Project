package Video9;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * GUI Class. Launches a GUI that will allow all of the same operations as the command line interface.
 * @author johnny
 *
 */
@SuppressWarnings("serial")
public class GUI extends JFrame implements ActionListener {
	
	/*
	 * GUI Components
	 */
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem addMenuItem,quitMenuItem;
	
	private JPanel mainPanel;
	
	
	/*
	 * Default Constructor
	 */
	public GUI() {
		// set up gui
		setupGUI();
	}
	
	/*
	 * Listens for ActionEvents and processes them accordingly.
	 */
	public void actionPerformed (ActionEvent e) {
		// find out where the event is coming from
		Object source = e.getSource();
		
		if (source == quitMenuItem) {
			System.exit(0);				// quit
		}
		if (source == addMenuItem) {
			handleAddImageEvent();		// handle add menuItem
		}
	}
	
	
	/*
	 * Function that sets up the gui layout.
	 */
	protected void setupGUI() {
		//Create and set up the window.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // size of window
        setSize(400, 500);
        
        // menu bar
        menuBar = new JMenuBar();				// bar
        fileMenu = new JMenu("File");			// menu
        addMenuItem = new JMenuItem("Add...");	// item
        addMenuItem.addActionListener(this);
        quitMenuItem = new JMenuItem("Quit");	// item
        quitMenuItem.addActionListener(this);
        fileMenu.add(addMenuItem);
        fileMenu.add(quitMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
        
        // main panel - dark gray
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.DARK_GRAY);
        getContentPane().add(mainPanel);
        //Display the window.
        setVisible(true);
	}
	
	/*
	 * Handles an add image event
	 */
	protected void handleAddImageEvent() {
		System.out.println("hi");
	}
	

}
