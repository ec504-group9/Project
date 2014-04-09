package Video9;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Decoder {

	//global objects to be changed and viewed
	public static int Index;
	public static Video video;
	//public static BufferedImage[] listOfImages;
	public static List<BufferedImage> listOfImages;
	public static int[][] encoded;

	Decoder(String videofile) {

		// read the object from file
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try {
			fis = new FileInputStream(videofile);
			in = new ObjectInputStream(fis);
			video = (Video) in.readObject();
			in.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		//build the images back from serialized video data
		listOfImages = video.getImages();

		//create a new Panel, the only panel used by this iteration of the function
		int width = listOfImages.get(0).getWidth();
		int height = listOfImages.get(0).getHeight();
		final Viewer frame = new Viewer(width, height);

		//make frame visible
		frame.setVisible(true);

		//manages the order in which the threads are executed. It is necessary to do something similar for GUI modification
		Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
				new Runnable() {
					@Override
					public void run() {
						frame.changeImage(nextImage());
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
