package Video9;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import scalingAlgorithms.Upsampler;

public class Decoder {

	//global objects to be changed and viewed
	public static int Index;
	public static Video video;
	private static List<BufferedImage> listOfImages;
	public static List<BufferedImage> compressed;
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
		compressed = video.getImages();

		// Up-sizing the image
		Upsampler up = new Upsampler();
		listOfImages = up.BilinearUpsample(compressed, compressed.get(0).getWidth(), compressed.get(0).getHeight(), video.getSCALING_FACTOR());

	}

	public static List<BufferedImage> getListOfImages() {
		return listOfImages;
	}
}
