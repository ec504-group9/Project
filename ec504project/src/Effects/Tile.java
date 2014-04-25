package Effects;

import java.awt.image.BufferedImage;

public class Tile {
	
	// Video Effect: TILED EFFECT
	private BufferedImage squareBI;					// stores a sub-image of the frame (per tile basis)
	private RGBSquare square;						// RGBsquare object made for finding most common RGB value or color
	private int avgRGB;							// stores most common RGB of section of frame (tile/square)
    
	private int squareWidth = 10;					// pixel width of tile
	private int squareHeight = 10;					// pixel height of tile
	private int rows;
	private int cols;
	
	public BufferedImage drawTiles(BufferedImage img){
	
		int h = img.getHeight();
		int w = img.getWidth();
		
		rows = h / squareHeight;			// number of tiles going down frame (image)
		cols = w / squareWidth;				// number of tiles across frame (image)
		
	    // create each tile and average its RGB
	    for (int ii = 0; ii < rows; ii++) {
	    	for (int jj = 0; jj < cols; jj++) {
	    		
	    		squareBI = img.getSubimage(squareWidth * jj, squareHeight * ii, squareWidth, squareHeight);	// create tile
	    		
	    		square = new RGBSquare(squareBI);			// create RGBSquare for tile
	    		avgRGB = square.getCommonColor();			// compute average RGB of tile/square
	    		
	    		int[] newRGBArray = new int[squareWidth * squareHeight];	// create RGB array
	    		
	    		// populate RGB array with the computed most common color
	    		for (int kk = 0; kk < squareWidth * squareHeight; kk++) {
	    			newRGBArray[kk] = avgRGB;
	    		}
	    		
	    		// set each image square/tile with the RGB array (the solid color square)
	    		img.setRGB(squareWidth * jj, squareHeight * ii, squareWidth, squareHeight, newRGBArray, 0, 0);
	    		
	    	}
	    }
	    
	    return img;
	}

}

