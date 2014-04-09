package Video9;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;

import javax.imageio.ImageIO;

public class Encoder{

	public static int[][] breakdown;
	public static List<BufferedImage> buffered;
	public static BufferedImage[] listOfImages;

	public static void main(String[] args) throws IOException {

		//path to the folder containing the images*********************************************************************
		String folder = DirectoryPaths.getPathtoimages();
		String filename = DirectoryPaths.getPathtoserializable();

		//load images
		getFiles(folder);
		
		//create and save the video file
		Video video = new Video(buffered.size(), buffered.get(0).getHeight(), buffered.get(0).getWidth(), buffered);

		// save the object to file
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(filename);
			out = new ObjectOutputStream(fos);
			out.writeObject(video);
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		System.out.print("File sucsessfully saved\n");
	}

	//get all image file from the folder
	public static void getFiles(String path){

		File folder = new File(path);

		if (folder.isDirectory()) {
			buffered = new ArrayList<BufferedImage>();
			for (File listfiles : folder.listFiles()){
				buffered.add(Loadimage(listfiles));
			}
		}
	}

	//loads images from the list of files
	static BufferedImage Loadimage(File file){
		BufferedImage Image = null;

		try {
			Image = ImageIO.read(file);
		} catch (IOException e) {
		}
		return Image;
	}
}
