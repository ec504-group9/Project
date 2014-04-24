package Video9;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

/**
 * GUI Class. Launches a GUI that will allow all of the same operations as the
 * command line interface.
 * 
 * @author johnny
 * 
 */
@SuppressWarnings("serial")
public class GUI extends JFrame implements ActionListener, Runnable {

	/*
	 * GUI Components - dummy comment
	 */

	public static List<BufferedImage> listOfImages;
	public static int Index;

	private JMenuBar menuBar;
	private JMenuBar encoderBar;
	private JMenu fileMenu;
	private JMenu encoderMenu;
	private JMenuItem addMenuItem, viewMenuItem, quitMenuItem, filesMenuItem;

	private JPanel encoder, decoder;
	private Viewer viewer;

	public ProgressMonitor progressbar = new ProgressMonitor(this, "", "", 0, 0);

	// for the decoder
	JLabel label = new JLabel("Select File to Decode:");;
	JLabel labeleffect = new JLabel(
			"Select realtime effects (Only one may be selected):");

	JTextField textField = new JTextField();
	JRadioButton normalBox = new JRadioButton("None", true);
	JRadioButton grayscaleBox = new JRadioButton("Grayscale", false);
	JRadioButton snowflakeBox = new JRadioButton("Snowflake", false);
	JRadioButton UnknownBox = new JRadioButton("Unknown", false);
	JButton findButton = new JButton("Find File");
	JButton decodeButton = new JButton("Decode");
	static JButton filesButton = new JButton("Browse Folders");
	static JTextField imageFolderPath = new JTextField();
	static JButton destinationButton = new JButton("Specify Destination Folder");
	static JTextField destinationPath = new JTextField();
	ButtonGroup effectsgroup = new ButtonGroup();

	private JFileChooser fc;
	private JFileChooser folderBrowser;
	private JFileChooser destinationBrowser;

	/*
	 * Default Constructor
	 */
	public GUI() {
		this.run();
	}

	/*
	 * Listens for ActionEvents and processes them accordingly.
	 */
	public void actionPerformed(ActionEvent e) {
		// find out where the event is coming from
		Object source = e.getSource();

		if (source == quitMenuItem) {
			System.exit(0); // quit
		}
		if (source == viewMenuItem) {
			// handleViewImageEvent(); // handle add menuItem
			//Encodeworker en = new Encodeworker(this);
			//en.execute();
			// progressbar.close();
		}
		if (source == findButton) {
			int returnVal = fc.showOpenDialog(this);
			textField.setText(fc.getSelectedFile().getAbsolutePath());
		}
		if (source == decodeButton) {
			try {
				handleViewImageEvent();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		if(source == filesButton){
			int returnVal = folderBrowser.showOpenDialog(this);
			imageFolderPath.setText(folderBrowser.getSelectedFile().getAbsolutePath());
		}
		if(source == destinationButton){
			int returnVal = destinationBrowser.showOpenDialog(this);
			destinationPath.setText(destinationBrowser.getSelectedFile().getAbsolutePath());
		}
		if(source == filesMenuItem){
			encodingUserMenu();
		}
	}

	class Encodeworker extends SwingWorker<Void, Integer> {
		
		GUI mygui;
		String destionationPath;
		String fileName;
		String pathToImages;
		int compresstionRatio;

		public Encodeworker(GUI myparent, String pathToImages, int compresstionRatio, String destionationPath, String fileName) {
			this.mygui = myparent;
			this.compresstionRatio = compresstionRatio;
			this.destionationPath = destionationPath;
			this.fileName = fileName;
			this.pathToImages = pathToImages;
		}

		@Override
		public Void doInBackground() {
			String fullName = destionationPath + "/" + fileName + ".ser";
			Encoder en = new Encoder(pathToImages,
					fullName, compresstionRatio, mygui);
			/*
			 * Encoder en = new Encoder("/home/osarenk1/Desktop/images",
					"compressed.ser", 2, mygui);
					*/
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
		filesMenuItem = new JMenuItem("Select Images Folder");
		filesMenuItem.addActionListener(this);
		fileMenu.add(addMenuItem);
		fileMenu.add(viewMenuItem);
		fileMenu.add(quitMenuItem);
		encoderMenu.add(filesMenuItem);
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
		filesButton.addActionListener(this);
		destinationButton.addActionListener(this);

		// Folder Browser for Selecting the Image Folder
		folderBrowser = new JFileChooser();
		folderBrowser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		// Folder Browser for Specifying the Destination Folder
		destinationBrowser = new JFileChooser();
		destinationBrowser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

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
	 * class progressbar extends ProgressMonitor{
	 * 
	 * public progressbar(Component parentComponent, Object message, String
	 * note, int min, int max) { super(parentComponent, message, note, min,
	 * max); // TODO Auto-generated constructor stub
	 * 
	 * }
	 * 
	 * }
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
		setSize(width, height + 200);

		viewer.setSize(width, height);
		viewer.setVisible(true);

		// manages the order in which the threads are executed. It is necessary
		// to do something similar for GUI modification
		Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
				new Runnable() {
					@Override
					public void run() {
						viewer.changeImage(nextImage());
					}
				}, 0, 150, TimeUnit.MILLISECONDS);
	}

	// gets the next image in the array list, circles around if it is done
	static BufferedImage nextImage() {
		Index++;
		if (Index >= listOfImages.size())
			Index = 0;
		return listOfImages.get(Index);
	}

	/*
	 * The following function pops up a menu for specifying the encoding options
	 * 
	 * Returns a new EncodingOptions class 
	 * 
	 * */
	private void encodingUserMenu() {

		JPanel panel = new JPanel(new GridLayout(0, 1));
		
		// Select the path to images
		panel.add(new JLabel("Select the Folder Containing Images :"));
		panel.add(filesButton);
		panel.add(imageFolderPath);

		// Specify the Quality-Space Trade-Off
		panel.add(new JLabel("Select Quality Level (1 Highest) :"));
		Integer[] items = {1,2,3,4};
		JComboBox combo = new JComboBox(items);
		panel.add(combo);

		// Specify the Destination Folder to Save Compressed File
		panel.add(new JLabel("Select Destination Folder :"));
		panel.add(destinationButton);
		panel.add(destinationPath);

		// Specify the Output File Name
		panel.add(new JLabel("Enter Output File Name: "));
		JTextField fileNameField = new JTextField("");
		panel.add(fileNameField);
		
		int result = JOptionPane.showConfirmDialog(null, panel, "Video Encoding Options",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION) {
			Encodeworker en = new Encodeworker(this, imageFolderPath.getText(), combo.getSelectedIndex()+1, destinationPath.getText(), fileNameField.getText());
			en.execute();
			/*System.out.println(combo.getSelectedItem()
					+ " " + fileNameField.getText());
					*/
		} else {
			//System.out.println("Cancelled");
		}

		//System.out.println(userOptions.getCompressionRatio() + userOptions.getDestinationPath() + userOptions.getFileName());
		
	}
}
