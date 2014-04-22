package scalingAlgorithms;

import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;

/*
 * This function sub-samples the given image by factors of 1,2,3,4 
 * 
 * 
 * Function: Downsample(arguments)
 * Inputs: List<BufferedImage> (List of Frames), int ScaleFactor
 * Returns: List<BufferedImage> (Sub-sampled list of frames)
 * */

public class Downsampler {
	
	private List<BufferedImage> DownsampledImageList;
	
	public List<BufferedImage> Downsample(List<BufferedImage> hr, int ScaleFactor){
		
		DownsampledImageList = new ArrayList<BufferedImage>();
		
		for(int i=0; i<hr.size();i++){
			
			BufferedImage image = new BufferedImage(hr.get(0).getWidth()/ScaleFactor, hr.get(0).getHeight()/ScaleFactor, BufferedImage.TYPE_INT_RGB);
			
			for(int j=0;j<image.getWidth();j++)
				for(int k=0;k<image.getHeight();k++){
					image.setRGB(j, k, hr.get(i).getRGB(ScaleFactor*j, ScaleFactor*k));
				}
			
			DownsampledImageList.add(image);
			
		}
		File out2 = new File("small.png");
		try {
			ImageIO.write(DownsampledImageList.get(0), "png", out2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//BufferedImage image = new BufferedImage(hr, imageHeight, BufferedImage.TYPE_INT_RGB);
		
		System.out.print("Down sampled succesfully!\n");
		return DownsampledImageList;
	}

}
