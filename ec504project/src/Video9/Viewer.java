package Video9;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.util.Vector;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
//import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class Viewer extends JFrame {
	private BufferedImage image;
//	private static int index;
	private JPanel contentPane;


	public Viewer(int width, int height) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, width, height);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		JLabel lblNewLabel = new JLabel(new ImageIcon("simple.png"));
		contentPane.add(lblNewLabel);
	}

	//changes the image being displayed in the panel
	public void changeImage(final BufferedImage img) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				image = img;
				repaint();
			}
		});
	}

	//overwrites default paint function so as to keep image we are modifying in context
	public void paint(Graphics g) {
		if (image != null) {
			g.drawImage(image, 0, 0, null);
		}
	}
}
