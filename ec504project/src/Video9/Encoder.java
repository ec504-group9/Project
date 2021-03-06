package Video9;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import scalingAlgorithms.Downsampler;

public class Encoder{

	public List<BufferedImage> buffered;
	private Video video;
	//private String imageFileType = "image/jpeg";
	private String imageFileType = "image/png";

	/* Encoder Constructor for the GUI */
	Encoder(String paths[], String[] arbitrary, String outputfile, int ratio, GUI guiHandler) {

		//load images
		getFiles(paths);
		System.out.println("111");

		// Down-sample the image by 4x
		Downsampler ds = new Downsampler(guiHandler);
		List<BufferedImage> DownsampledImages = new ArrayList<BufferedImage>();
		System.out.print(buffered.size());
		DownsampledImages = ds.Downsample(buffered, ratio);
		System.out.print(buffered.size());

		// Create a video file containing down-sampled images
		video = new Video(DownsampledImages.size(), DownsampledImages.get(0).getHeight(), DownsampledImages.get(0).getWidth(), DownsampledImages, ratio);
		getArbitraryFiles(arbitrary);
		if(guiHandler != null)
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
		if(guiHandler != null)
			guiHandler.progressbar.close();
		JOptionPane.showMessageDialog(null,
				"File succesfully saved!");
		System.out.print("File sucsessfully saved\n");
	}

	//get files from list of image files
	void getFiles(String[] paths){
		if(paths == null) return;
		int num = paths.length;
		if(num<=0) return;
		buffered = new ArrayList<BufferedImage>();

		//if file paths contains a folder
		File folder = new File(paths[0]);
		if(folder.isDirectory()){
			File[] listOfFiles = folder.listFiles();

			for (File file : listOfFiles) {
						buffered.add(Loadimage(file));
			}
		}

		//else it is a list of files
		else if(num>1){
			for (String filename : paths){
				File file = new File(filename);
						buffered.add(Loadimage(file));
			}
		}
		else
			return;

	}

	//get the arbitrary binary file
	void getArbitraryFiles(String[] paths){
		if(paths == null) return;
		int num = paths.length;
		if(num<=0) return;
		List<String> names = new ArrayList<String>();
		List<byte[]> listOfArbitrary = new ArrayList<byte[]>();


		//if file paths contains a folder
		File folder = new File(paths[0]);
		if(folder.isDirectory()){
			File[] listOfFiles = folder.listFiles();
			for (File file : listOfFiles) {
				byte[] arbitrary = new byte[(int) file.length()];
				FileInputStream fis;
				try {
					fis = new FileInputStream(file);
					fis.read(arbitrary);
					fis.close();
					listOfArbitrary.add(arbitrary);
					names.add(file.getName());
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		}

		//else it is list of files
		else if(num>1){
			for(String path : paths){
				File file = new File(path);
				byte[] arbitrary = new byte[(int) file.length()];
				FileInputStream fis;
				try {
					fis = new FileInputStream(path);
					fis.read(arbitrary);  
					fis.close();  
					listOfArbitrary.add(arbitrary);
					names.add(file.getName());
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		}
		else
			return;
		video.setArbitrary(listOfArbitrary);
		video.setArbitrary_name(names);
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


	/*
			try {
				String ext = Files.probeContentType(Paths.get(filename));
				System.out.print(ext+"\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	 */
}
