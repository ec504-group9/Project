package Video9;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;

import javax.imageio.ImageIO;

public class Encoder{

	public int[][] breakdown;
	public List<BufferedImage> buffered;
	public BufferedImage[] listOfImages;

	Encoder(String paths, String outputfile) {

		//load images
		getFiles(paths);

		// Down-sample the image by 4x
		Downsampler ds = new Downsampler();
		List<BufferedImage> DownsampledImages = new ArrayList<BufferedImage>();
		DownsampledImages = ds.Downsample(buffered);
		
		// Create a video file containing downsampled images
		Video video = new Video(buffered.size(), buffered.get(0).getHeight(), buffered.get(0).getWidth(), DownsampledImages);

		// save the object to file
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(outputfile);
			out = new ObjectOutputStream(fos);
			out.writeObject(video);
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		System.out.print("File sucsessfully saved\n");
	}

	//get all image file from the folder
	void getFiles(String paths){
		
		File folder = new File(paths);
		File[] listOfFiles = folder.listFiles();
		buffered = new ArrayList<BufferedImage>();
		
		for (File file : listOfFiles) {
			if (file.isFile()) {
				//System.out.println(paths + "/" + file.getName());
				buffered.add(Loadimage(file));
			}
		}
		/*
		
		for (String filename : paths){
			File file = new File(filename);
			buffered.add(Loadimage(file));
		}
		*/
	}

	//loads images from the list of files
	BufferedImage Loadimage(File file){
		BufferedImage Image = null;

		try {
			Image = ImageIO.read(file);
		} catch (IOException e) {
		}
		return Image;
	}
}
