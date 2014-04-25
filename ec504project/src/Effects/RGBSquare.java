package Effects;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.*;

public class RGBSquare {
	
	// constructor
	public RGBSquare(BufferedImage mySquare) {
		square = mySquare;
		w = square.getWidth();
		h = square.getHeight();
		//rgbArray = square.getRGB(0, 0, w, h, rgbArray, 0, w);	// array of RGB values for image
		pixelCounter = 0;
	}
	
	// return average RGB value of image sample
	public int getCommonColor() {
		
		red = new ArrayList<Integer>();
		green = new ArrayList<Integer>();
		blue = new ArrayList<Integer>();
		int blockSize = 5;	// test every [blockSize] pixels
		
		//--------- SEPARATE RGB's OF EACH PIXEL OF IMAGE SAMPLE ---------
		for (int ii = 0; ii < w; ii++) {
			for (int jj = 0; jj < h; jj++) {
				c = new Color(square.getRGB(ii, jj));
				red.add(c.getRed());
				blue.add(c.getBlue());
				green.add(c.getGreen());
				pixelCounter++;
			}
		}
		
		//--------- OBTAIN AVERAGE RGB VALUES ---------
		// get average of red
		int sum = 0;
		for (int ii = 0; ii < red.size(); ii++) {
			sum += red.get(ii);
		}
		avgR = sum / pixelCounter;
		
		// get average of green
		sum = 0;
		for (int ii = 0; ii < green.size(); ii++) {
			sum += green.get(ii);
		}
		avgG = sum / pixelCounter;
		
		// get average of blue
		sum = 0;
		for (int ii = 0; ii < blue.size(); ii++) {
			sum += blue.get(ii);
		}
		avgB = sum / pixelCounter;
		
		//--------- RETURN AVERAGE RGB OF MOST COMMON COLOR ---------
		avgColor = new Color(avgR, avgG, avgB);
		int avgRGB = avgColor.getRGB();
		
		return avgRGB;
		
	}
	
	// return average Color value of image sample
	public Color getCommonColorValue() {
		
		red = new ArrayList<Integer>();
		green = new ArrayList<Integer>();
		blue = new ArrayList<Integer>();
		int blockSize = 5;	// test every [blockSize] pixels
		
		//--------- SEPARATE RGB's OF EACH PIXEL OF IMAGE SAMPLE ---------
		for (int ii = 0; ii < w; ii++) {
			for (int jj = 0; jj < h; jj++) {
				c = new Color(square.getRGB(ii, jj));
				red.add(c.getRed());
				blue.add(c.getBlue());
				green.add(c.getGreen());
				pixelCounter++;
			}
		}
		
		//--------- OBTAIN AVERAGE RGB VALUES ---------
		// get average of red
		int sum = 0;
		for (int ii = 0; ii < red.size(); ii++) {
			sum += red.get(ii);
		}
		avgR = sum / pixelCounter;
		
		// get average of green
		sum = 0;
		for (int ii = 0; ii < green.size(); ii++) {
			sum += green.get(ii);
		}
		avgG = sum / pixelCounter;
		
		// get average of blue
		sum = 0;
		for (int ii = 0; ii < blue.size(); ii++) {
			sum += blue.get(ii);
		}
		avgB = sum / pixelCounter;
		
		//--------- RETURN MOST COMMON COLOR ---------
		avgColor = new Color(avgR, avgG, avgB);
		
		return avgColor;
		
	}
	
	public BufferedImage newSquare(){
		
		newSquare = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = newSquare.createGraphics();
		g2d.setColor(avgColor);
		g2d.fill(new Rectangle(0, 0, w, h));
		g2d.dispose();
		
		return newSquare;
		
	}
	
	public BufferedImage square, newSquare;					// block of image sample
	public ArrayList<Integer> red, green, blue;		// RGB samples in array
	public Color c, avgColor;									// color
	public int w, h;								// width and height of image sample
	public int avgR, avgG, avgB;					// average values
	public int[] rgbArray;							// RGB array to test each pixel in
	public int pixelCounter;						// counts total number of tested pixels

}
