package Effects;

import java.awt.image.BufferedImage;

public class snowflake {

	
	public int range = 50;	// +- range for x value to move back and forth (snowflake) 

	//Variables for the snowflake falling effect
	int minWidth;						// min width of all images
	int minHeight;						// min height of all images
	
	public BufferedImage snowflake;
	public int height, width, xoffset, yoffset, xRightLimit, xLeftLimit, xvector, yvector, picWidth;

	

	public snowflake(BufferedImage mySnowflake, int myxoffset, int myyoffset, int myxvector, int myyvector, int myPicWidth) {
		
		snowflake = mySnowflake;
		height = mySnowflake.getHeight();
		width = mySnowflake.getWidth();
		xoffset = myxoffset;
		yoffset = myyoffset;
		picWidth = myPicWidth;
		
		// x boundaries for snowflake to move back and forth between
		if (myxoffset + range >= picWidth) xRightLimit = picWidth;
		else xRightLimit = myxoffset + range;
		
		if (myxoffset - range < 0) xLeftLimit = 0;
		else xLeftLimit = myxoffset - range;
		
		xvector = myxvector;
		yvector = myyvector;
	}
	
	
	

}
