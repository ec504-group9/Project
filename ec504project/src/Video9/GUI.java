package Video9;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
	
	public static List<BufferedImage> listOfImages;
	public static int Index;
	
	private JMenuBar menuBar;
	private JMenuBar encoderBar;
	private JMenu fileMenu;
	private JMenu encoderMenu;
	private JMenuItem addMenuItem, viewMenuItem, quitMenuItem;
	
	private JPanel encoder, decoder;
	private Viewer viewer;
	
	public ProgressMonitor progressbar = new ProgressMonitor(this, "", "", 0, 0);

	//for the decoder
	JLabel label = new JLabel("Select File to Decode:");;
	JLabel labeleffect = new JLabel("Select realtime effects (Only one may be selected):");
	
    JTextField textField = new JTextField();
    JRadioButton normalBox = new JRadioButton("None",true);
    JRadioButton grayscaleBox = new JRadioButton("Grayscale",false);
    JRadioButton snowflakeBox = new JRadioButton("Snowflake",false);
    JRadioButton UnknownBox = new JRadioButton("Unknown",false);
    JButton findButton = new JButton("Find File");
    JButton decodeButton = new JButton("Decode");
	ButtonGroup effectsgroup = new ButtonGroup();
	
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
			//handleViewImageEvent();		// handle add menuItem
			Encodeworker en = new Encodeworker(this);
			en.execute();
			//progressbar.close();

		}
		if(source == findButton)
		{
			int returnVal = fc.showOpenDialog(this);
			textField.setText(fc.getSelectedFile().getAbsolutePath());
		}
		if(source == decodeButton)
		{
			try{
				handleViewImageEvent();
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	class Encodeworker extends SwingWorker<Void,Integer>{
		GUI mygui;
		public Encodeworker(GUI myparent)
		{
			this.mygui =myparent;
		}
		@Override
		public Void doInBackground()
		{
			Encoder en = new Encoder("/home/osarenk1/Desktop/images", "compressed.ser", 2, mygui);
			return null;
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
        setSize(685, 200);
        
        // menu bar
        menuBar = new JMenuBar();				// bar
        fileMenu = new JMenu("File");			// menu
        encoderBar = new JMenuBar();				// 
        encoderMenu = new JMenu("Encoder");			// 
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
        menuBar.add(encoderMenu);
        setJMenuBar(menuBar);
        
        //Add radiobuttons to group
        effectsgroup.add(normalBox);
        effectsgroup.add(grayscaleBox);
        effectsgroup.add(snowflakeBox);
        effectsgroup.add(UnknownBox);
        
        //Filechooser
        fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        findButton.addActionListener(this);
        decodeButton.addActionListener(this);
        
        // encoder part of the gui - dark gray
        encoder = new JPanel(new BorderLayout());
        encoder.setBackground(Color.DARK_GRAY);
        getContentPane().add(encoder);

        // viewer part of the gui - dark gray
        viewer = new Viewer();
        viewer.setBackground(Color.DARK_GRAY);
        getContentPane().add(viewer);
        viewer.setVisible(false);
        
        // decode part of the gui
        decoder = new JPanel();
        getContentPane().add(decoder, BorderLayout.SOUTH);
        GroupLayout layout = new GroupLayout(decoder);
        decoder.setLayout(layout);
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
                             .addComponent(normalBox)
                            .addComponent(UnknownBox))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(snowflakeBox)
                            .addComponent(grayscaleBox)
                            )))
                      //.addComponent(labeldecodenote))
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
                            .addComponent(normalBox)
                            .addComponent(snowflakeBox))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(UnknownBox)
                            .addComponent(grayscaleBox)
                            ))
                 
                  )
                // .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                  //   .addComponent(labeldecodenote))
            );
            
        //Display the window.
        setVisible(true);
	}
	 /*
	class progressbar extends ProgressMonitor{
		
		public progressbar(Component parentComponent, Object message,
				String note, int min, int max) {
			super(parentComponent, message, note, min, max);
			// TODO Auto-generated constructor stub
			
		}
		
	}
	*/
	/*
	 * Handles an add image event
	 */
	protected void handleViewImageEvent() {
		
		Decoder d = new Decoder(textField.getText());
		listOfImages = d.getListOfImages();
		Index = 0;
		
		int width = listOfImages.get(0).getWidth();
		int height = listOfImages.get(0).getHeight();
        setSize(width, height+200);

		viewer.setSize(width, height);
		viewer.setVisible(true);

		
		//manages the order in which the threads are executed. It is necessary to do something similar for GUI modification
		Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
				new Runnable() {
					@Override
					public void run() {
						viewer.changeImage(nextImage());
					}
				}, 0, 150, TimeUnit.MILLISECONDS);
	}
	
	//gets the next image in the array list, circles around if it is done
	static BufferedImage nextImage() {
		Index++;
		if(Index>=listOfImages.size()) Index=0;
		return listOfImages.get(Index);
	}
}
