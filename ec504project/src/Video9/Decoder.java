package Video9;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import scalingAlgorithms.Upsampler;

public class Decoder {

	//global objects to be changed and viewed
	public static Video video;
	private static List<BufferedImage> listOfImages;
	public static List<BufferedImage> compressed;

	Decoder(String videofile, GUI guiHandler) {

		// Notify user to wait for the deserialization
		if(guiHandler != null){
			guiHandler.progressbar.setMaximum(100);
			guiHandler.progressbar.setNote(String.format("Wait! Deserializing the File!"));
		}
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
		
		if(video.getArbitrary() != null) setArbitraryFiles(videofile);

		// Up-sizing the image
		Upsampler up = new Upsampler();

		// Use ICBI algorithm for scaling by x2
		if(video.getSCALING_FACTOR() == 2){
			listOfImages = up.ICBIUpsample(compressed, compressed.get(0).getWidth(), compressed.get(0).getHeight(), video.getSCALING_FACTOR(), guiHandler);
			System.out.println("ICBI Interpolation algorithm launched!");
		}
		else{
			listOfImages = up.BilinearUpsample(compressed, compressed.get(0).getWidth(), compressed.get(0).getHeight(), video.getSCALING_FACTOR(), guiHandler);
			System.out.println("Bilinear interpolation algorithm launched!");
		}

	}

	public static List<BufferedImage> getListOfImages() {
		return listOfImages;
	}
	
	
	//set the arbitrary binary file
	void setArbitraryFiles(String path){
		if(path == null) return;

		path = path.substring(0, path.lastIndexOf('/'));
		List<byte[]> listOfArbitrary = video.getArbitrary();
		List<String> listOfNames = video.getArbitrary_name();
		
		if(listOfArbitrary == null) return;
		
		int num = listOfArbitrary.size();

		if(num <= 0) return;
		
		for(int i=0; i<num; i++){
			String name = listOfNames.get(i);
			byte[] arbitrary = listOfArbitrary.get(i);
			try {
				FileOutputStream fis;
				fis = new FileOutputStream(path+"/"+name);
				fis.write(arbitrary);  
				fis.close();  
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}
}
