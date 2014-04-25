package Effects;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

public class Snow {
	
	private BufferedImage snowflakeImg;		// holds snowflake sprite
	private int snowflakeXoffset;		// first snowflake's x location
	private int snowflakeYoffset;		// first snowflake's y location
	private int snowflakeXVector;		// how fast snowflake travels on x-axis
	private int snowflakeYVector;		// how fast snowflake travels on y-axis
	private int numSnowflakes;		// total/max number of snowflakes
	
	private Random randomGenerator = new Random();	// Random generator object: for generating snowflake vectors
	int snowflakeWidth;
	int snowflakeHeight;
	protected ArrayList <snowflake> snowflakesList = new ArrayList <snowflake>();		// list of snowflakes

	
	public void makeSnowflakes(int minWidth, int minHeight){
		
		//load snowflake
		//TODO
		//change path
		String snowflakePath = "/home/sahin/Desktop/snowflake_50_55.jpg";
		
		try {
			// load snowflake sprite
			snowflakeImg = ImageIO.read(new File(snowflakePath));
			snowflakeWidth = snowflakeImg.getWidth();
			snowflakeHeight = snowflakeImg.getHeight();
			
			numSnowflakes = randomInteger(randomGenerator, 10, 40);
			
			// load all snowflakes for later drawing
			for (int ii = 0; ii < numSnowflakes; ii++) {
				
				// generate random vectors and offsets for snowflake movements/positions
				snowflakeXoffset = randomGenerator.nextInt(minWidth - snowflakeWidth);
				snowflakeYoffset = randomGenerator.nextInt(minHeight - snowflakeHeight);
				snowflakeXVector = randomInteger(randomGenerator, -12, 12);
				snowflakeYVector = randomInteger(randomGenerator, 3, 7);
				
				snowflake mySnowflake = new snowflake(snowflakeImg, snowflakeXoffset, snowflakeYoffset, snowflakeXVector, snowflakeYVector, minWidth);
				snowflakesList.add(mySnowflake);
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

	
	
	public BufferedImage drawSnow(BufferedImage img){
		
		int h = img.getHeight();
		int w = img.getWidth();		
	    
	    Color whitePixel = new Color(200, 200, 200); 	// Color white
	    int white = whitePixel.getRGB();				// Get RGB value

	    
	    //Video Effect: (IN PROGRESS--FALLING SNOWFLAKES)
	    for (int hh = 0; hh < snowflakeHeight; hh++) {
	    	for (int ww = 0; ww < snowflakeWidth; ww++) {
	    		
	    		// Draw snowflakes: Exclude white borders around snowflake image
	    		for (int ii = 0; ii < numSnowflakes; ii++) {
	    			if (snowflakesList.get(ii).snowflake.getRGB(ww,hh) <= white) {
	    				if (ww + snowflakesList.get(ii).xoffset < img.getWidth() 
	    						&& hh + snowflakesList.get(ii).yoffset < img.getHeight()
	    						&& ww + snowflakesList.get(ii).xoffset > 0
	    						&&  hh + snowflakesList.get(ii).yoffset > 0)
	    					img.setRGB(	ww + snowflakesList.get(ii).xoffset, 
	    						hh + snowflakesList.get(ii).yoffset, 
	    						snowflakesList.get(ii).snowflake.getRGB(ww,hh));
	    			}
	    		}
	    	}
	    }
	    
	    // Snowflake falling movements
	    for (int ii = 0; ii < numSnowflakes; ii++) {
	    	
	    	if (snowflakesList.get(ii).xoffset >= snowflakesList.get(ii).xRightLimit || snowflakesList.get(ii).xoffset >= w)
	    		snowflakesList.get(ii).xvector *= -1;
		    if (snowflakesList.get(ii).xoffset <= snowflakesList.get(ii).xLeftLimit || snowflakesList.get(ii).xoffset <= 0)
		    	snowflakesList.get(ii).xvector *= -1;
		    snowflakesList.get(ii).xoffset += snowflakesList.get(ii).xvector;
		    
		    // after snowflake reaches bottom of screen, "create" new snowflake by setting new offsets and vectors
		    if (snowflakesList.get(ii).yoffset >= h) {
		    	snowflakesList.get(ii).yoffset = 0;
		    	snowflakesList.get(ii).xoffset = randomGenerator.nextInt(w - snowflakeWidth);
		    	snowflakesList.get(ii).xvector = randomInteger(randomGenerator, -12, 12);
		    	snowflakesList.get(ii).yvector = randomInteger(randomGenerator, 3, 7);
		    }
		    snowflakesList.get(ii).yoffset += snowflakesList.get(ii).yvector;
		    
	    }		
		return img;
	}

	// produce random integer within a specific range
	private static int randomInteger(Random random, int start, int end){
	    if (start > end) {
	      throw new IllegalArgumentException("Start value cannot exceed end value.");
	    }
	    // get range, cast to long to avoid overflow problems
	    long range = (long)end - (long)start + 1;
	    // fraction of the range, 0 <= fraction < range
	    long fraction = (long)(range * random.nextDouble());
	    int randomNumber =  (int)(fraction + start);
	    return randomNumber;
	  }


}
