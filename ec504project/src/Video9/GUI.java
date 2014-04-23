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
public class GUI extends JFrame implements ActionListener,Runnable {
	
	/*
	 * GUI Components - dummy comment
	 */
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem addMenuItem, viewMenuItem, quitMenuItem;
	
	private JPanel mainPanel, fileListPanel;
	
	private JPanel panel;
	JLabel label = new JLabel("Select File to Decode:");;
	JLabel labeleffect = new JLabel("Select realtime effects (Only one may be selected):");;
    JTextField textField = new JTextField();
    JCheckBox grayscaleBox = new JCheckBox("Grayscale");
    JCheckBox wrapCheckBox = new JCheckBox("Snowflake");
    JCheckBox wholeCheckBox = new JCheckBox("Unknown");
    JButton findButton = new JButton("Find File");
    JButton decodeButton = new JButton("Decode");
	
    private JFileChooser fc;
	
	/*
	 * Default Constructor
	 */
	public GUI() {
		this.run();
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
		if (source == viewMenuItem) {
			handleViewImageEvent();		// handle add menuItem
		}
		if(source == findButton)
		{
			int returnVal = fc.showOpenDialog(this);
			textField.setText(fc.getSelectedFile().getAbsolutePath());
		}
		if(source == decodeButton)
		{
			
		}
	}
	
	
	/*
	 * 
	 * Function that sets up the gui layout.
	 */
	 @Override
	public void run() {
		//Create and set up the window.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // size of window
        setSize(575, 500);
        
        // menu bar
        menuBar = new JMenuBar();				// bar
        fileMenu = new JMenu("File");			// menu
        addMenuItem = new JMenuItem("Add...");	// item
        addMenuItem.addActionListener(this);
        viewMenuItem = new JMenuItem("View");	// item
        viewMenuItem.addActionListener(this);
        quitMenuItem = new JMenuItem("Quit");	// item
        quitMenuItem.addActionListener(this);
        fileMenu.add(addMenuItem);
        fileMenu.add(viewMenuItem);
        fileMenu.add(quitMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
        
        // main panel - dark gray
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.DARK_GRAY);
        getContentPane().add(mainPanel);
        
        //Filechooser
        fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        findButton.addActionListener(this);
        decodeButton.addActionListener(this);
        
        //The decode part of GUI
        panel = new JPanel();
        getContentPane().add(panel, BorderLayout.SOUTH);
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                	.addComponent(label)
                	.addComponent(decodeButton))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(textField)
                    .addComponent(labeleffect)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(grayscaleBox)
                            .addComponent(wholeCheckBox))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(wrapCheckBox)
                            )))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(findButton)
                    )
            );
            
            layout.linkSize(SwingConstants.HORIZONTAL, findButton , decodeButton);
     
            layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(label)
                    .addComponent(textField)
                    .addComponent(findButton))
                 .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                     .addComponent(decodeButton)
                     .addComponent(labeleffect))
                    
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(grayscaleBox)
                            .addComponent(wrapCheckBox))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(wholeCheckBox)
                            ))
                 
                  )
            );
            
        //Display the window.
        setVisible(true);
	}
	
	/*
	 * Handles an add image event
	 */
	protected void handleViewImageEvent() {
		//Decoder d = new Decoder(outputFilename);
	}
	

}
