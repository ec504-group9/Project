package Video9;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class Viewer extends JPanel {
	private BufferedImage image;
	
	Viewer(){
		super();
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
