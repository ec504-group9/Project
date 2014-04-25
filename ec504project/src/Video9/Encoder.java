package Video9;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import scalingAlgorithms.Downsampler;

public class Encoder{

	public int[][] breakdown;
	public List<BufferedImage> buffered;
	private GUI guiHandler;
	
	/* Encoder for the Command Line  */
	Encoder(String paths, String outputfile, int ratio) {
		//load images
		getFiles(paths);

		// Down-sample the image by 4x
		Downsampler ds = new Downsampler(guiHandler);
		List<BufferedImage> DownsampledImages = new ArrayList<BufferedImage>();
		DownsampledImages = ds.Downsample(buffered, ratio);
		
		// Create a video file containing down-sampled images
		Video video = new Video(DownsampledImages.size(), DownsampledImages.get(0).getHeight(), DownsampledImages.get(0).getWidth(), DownsampledImages, ratio);
		
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
	
	/* Encoder Constructor for the GUI */
	Encoder(String paths, String outputfile, int ratio, GUI guiHandler) {
		
		this.guiHandler = guiHandler;
		//load images
		getFiles(paths);

		// Down-sample the image by 4x
		Downsampler ds = new Downsampler(guiHandler);
		List<BufferedImage> DownsampledImages = new ArrayList<BufferedImage>();
		DownsampledImages = ds.Downsample(buffered, ratio);
		
		// Create a video file containing down-sampled images
		Video video = new Video(DownsampledImages.size(), DownsampledImages.get(0).getHeight(), DownsampledImages.get(0).getWidth(), DownsampledImages, ratio);
		
		guiHandler.progressbar.setNote(String.format("Please Wait! Saving the encoded file!"));
		
		// save the object to file
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(new File(outputfile));
			out = new ObjectOutputStream(fos);
			out.writeObject(video);
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		guiHandler.progressbar.close();
		JOptionPane.showMessageDialog(null,
			    "File succesfully saved!");
		System.out.print("File sucsessfully saved\n");
	}

	//get all image file from the folder
	void getFiles(String path){
		
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		buffered = new ArrayList<BufferedImage>();
		
		for (File file : listOfFiles) {
			/*
			try {
				String ext = Files.probeContentType(Paths.get(file));
				System.out.print(ext+"\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			if (file.isFile()) {
				buffered.add(Loadimage(file));
			}
		}
	}
	
	//get files from list of files
	void getFiles(String[] paths){
		
		for (String filename : paths){
			try {
				String ext = Files.probeContentType(Paths.get(filename));
				System.out.print(ext+"\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			File file = new File(filename);
			buffered.add(Loadimage(file));
		}

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
