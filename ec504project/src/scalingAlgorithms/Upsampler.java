package scalingAlgorithms;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;


public class Upsampler {

	private List<BufferedImage> UpsampledImageList;

	public List<BufferedImage> NNUpsample(List<BufferedImage> lr){

		UpsampledImageList = new ArrayList<BufferedImage>();

		for(int i=0; i<lr.size();i++){

			BufferedImage image = new BufferedImage(lr.get(0).getWidth()*2, lr.get(0).getHeight()*2, BufferedImage.TYPE_INT_RGB);

			for(int j=0;j<lr.get(0).getWidth();j++)
				for(int k=0;k<lr.get(0).getHeight();k++){
					image.setRGB(2*j, 2*k, lr.get(i).getRGB(j,k));
					image.setRGB(2*j+1, 2*k, lr.get(i).getRGB(j,k));
					image.setRGB(2*j, 2*k+1, lr.get(i).getRGB(j,k));
					image.setRGB(2*j+1, 2*k+1, lr.get(i).getRGB(j,k));
				}

			UpsampledImageList.add(image);

		}
		File out2 = new File("NNUpsampled.png");
		try {
			ImageIO.write(UpsampledImageList.get(0), "png", out2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//BufferedImage image = new BufferedImage(hr, imageHeight, BufferedImage.TYPE_INT_RGB);

		return UpsampledImageList;
	}

	public List<BufferedImage> BilinearUpsample(List<BufferedImage> frames, int w, int h, int scalingFactor) {

		List<BufferedImage> upsampledFrames = new ArrayList<BufferedImage>();

		int a, b, c, d, x, y, w2, h2;
		w2 = w*scalingFactor;
		h2 = h*scalingFactor;
		float x_ratio = ((float)(w-1))/(w2) ;
		float y_ratio = ((float)(h-1))/(h2) ;
		float x_diff, y_diff, blue, red, green ;
		//int offset = 0 ;
		for(int f=0;f<frames.size(); f++){
			BufferedImage original = frames.get(f);
			BufferedImage upSampled = new BufferedImage(w2, h2, BufferedImage.TYPE_INT_RGB);
			for (int i=0;i<h2;i++) {
				for (int j=0;j<w2;j++) {
					x = (int)(x_ratio * j) ;
					y = (int)(y_ratio * i) ;
					x_diff = (x_ratio * j) - x ;
					y_diff = (y_ratio * i) - y ;
					//index = (y*w+x) ;                
					a = original.getRGB(x, y);
					b = original.getRGB(x+1, y);
					c = original.getRGB(x, y+1);
					d = original.getRGB(x+1, y+1);
					//System.out.println(a + " " + b + " " + c + " " + d + " ");
					// blue element
					// Yb = Ab(1-w)(1-h) + Bb(w)(1-h) + Cb(h)(1-w) + Db(wh)
					blue = (a&0xff)*(1-x_diff)*(1-y_diff) + (b&0xff)*(x_diff)*(1-y_diff) +
							(c&0xff)*(y_diff)*(1-x_diff)   + (d&0xff)*(x_diff*y_diff);

					// green element
					// Yg = Ag(1-w)(1-h) + Bg(w)(1-h) + Cg(h)(1-w) + Dg(wh)
					green = ((a>>8)&0xff)*(1-x_diff)*(1-y_diff) + ((b>>8)&0xff)*(x_diff)*(1-y_diff) +
							((c>>8)&0xff)*(y_diff)*(1-x_diff)   + ((d>>8)&0xff)*(x_diff*y_diff);

					// red element
					// Yr = Ar(1-w)(1-h) + Br(w)(1-h) + Cr(h)(1-w) + Dr(wh)
					red = ((a>>16)&0xff)*(1-x_diff)*(1-y_diff) + ((b>>16)&0xff)*(x_diff)*(1-y_diff) +
							((c>>16)&0xff)*(y_diff)*(1-x_diff)   + ((d>>16)&0xff)*(x_diff*y_diff);

					int tempRGB = 0xFF000000 | // hardcode alpha
							((((int)red)<<16)&0xff0000) |
							((((int)green)<<8)&0xff00) |
							((int)blue) ;
					upSampled.setRGB(j, i, tempRGB);
				}
			}
			upsampledFrames.add(upSampled);
		}
		System.out.print("Up sampled succesfully!\n");
		return upsampledFrames;
	}

	public List<BufferedImage> ICBIUpsample(List<BufferedImage> frames, int w, int h, int scalingFactor) {
		List<BufferedImage> upsampledFrames = new ArrayList<BufferedImage>();

		int w2 = w*2;
		int h2 = h*2;
		int I1, I2, I3, I4, I5, I6, I7, I8, I9, I30, I31, I32;
		// For each frame 
		for(int f=0;f<frames.size(); f++){

			BufferedImage original = frames.get(f);
			BufferedImage upSampled = new BufferedImage(w2, h2, BufferedImage.TYPE_INT_RGB);

			//fill new image with pixels from old image
			for (int i=0;i<h;i++) {
				for (int j=0;j<w;j++) {
					upSampled.setRGB(2*j, 2*i, original.getRGB(j, i));		
					/*upSampled.setRGB(2*j, 2*i+1, 0xFF0000);
					upSampled.setRGB(2*j+1, 2*i, 0xFF0000);
					upSampled.setRGB(2*j+1, 2*i+1, 0xFF0000);
					 */
				}
			}

			// First Pass - For Odd Indexed Pixels
			for (int j=3;j<h2-3;j+=2) {
				for (int i=3;i<w2-3;i+=2) {

					int tempPixel = 0xFF000000;

					// Get the neighbor pixel values for  the derivative calculation 
					I1 = upSampled.getRGB(i-1,j-3);
					I2 = upSampled.getRGB(i+1,j-3);
					I3 = upSampled.getRGB(i-3,j-1);
					I4 = upSampled.getRGB(i-1,j-1);
					I5 = upSampled.getRGB(i+1,j-1);
					I6 = upSampled.getRGB(i+3,j-1);
					I7 = upSampled.getRGB(i-3,j+1);
					I8 = upSampled.getRGB(i-1,j+1);
					I9 = upSampled.getRGB(i+1,j+1);
					I30 = upSampled.getRGB(i+3,j+1);
					I31 = upSampled.getRGB(i-1,j+3);
					I32 = upSampled.getRGB(i+1,j+3);

					// Interpolate blue channel
					int I11Blue = (I2&0xFF) + (I4&0xFF) + (I7&0xFF) 
							+ (I6&0xFF) + (I9&0xFF) + (I31&0xFF) 
							- 3*(I5&0xFF) -3*(I8&0xFF);

					int I22Blue = (I1&0xFF) + (I5&0xFF) + (I30&0xFF) 
							+ (I3&0xFF) + (I8&0xFF) + (I32&0xFF) 
							- 3*(I4&0xFF) -3*(I9&0xFF);

					if(I11Blue > I22Blue)
						tempPixel = tempPixel | ((int)((I4&0xFF) + (I9&0xFF))/2);
					else
						tempPixel = tempPixel | ((int)((I5&0xFF) + (I8&0xFF))/2);					

					// Interpolate green channel
					int I11Green = ((I2>>8)&0xff) + ((I4>>8)&0xff) + ((I7>>8)&0xff) 
							+ ((I6>>8)&0xff) + ((I9>>8)&0xff) + ((I31>>8)&0xff) 
							- 3*((I5>>8)&0xff) - 3*((I8>>8)&0xff);

					int I22Green = ((I1>>8)&0xff) + ((I5>>8)&0xff) + ((I30>>8)&0xff) 
							+ ((I3>>8)&0xff) + ((I8>>8)&0xff) + ((I32>>8)&0xff) 
							- 3*((I4>>8)&0xff) - 3*((I9>>8)&0xff);

					if(I11Green > I22Green)
						tempPixel = tempPixel | ((((int)((((I4>>8)&0xff) + ((I9>>8)&0xff))/2))<<8)&0xff00);
					else
						tempPixel = tempPixel | ((((int)((((I5>>8)&0xff) + ((I8>>8)&0xff))/2))<<8)&0xff00);

					// Interpolate red channel
					int I11Red = ((I2>>8)&0xff) + ((I4>>8)&0xff) + ((I7>>8)&0xff) 
							+ ((I6>>8)&0xff) + ((I9>>8)&0xff) + ((I31>>8)&0xff) 
							- 3*((I5>>8)&0xff) - 3*((I8>>8)&0xff);

					int I22Red = ((I1>>16)&0xff) + ((I5>>16)&0xff) + ((I30>>16)&0xff) 
							+ ((I3>>16)&0xff) + ((I8>>16)&0xff) + ((I32>>16)&0xff) 
							- 3*((I4>>16)&0xff) - 3*((I9>>16)&0xff);

					if(I11Red > I22Red)
						tempPixel = tempPixel | ((((int)((((I4>>16)&0xff) + ((I9>>16)&0xff))/2))<<16)&0xff0000);
					else
						tempPixel = tempPixel | ((((int)((((I5>>16)&0xff) + ((I8>>16)&0xff))/2))<<16)&0xff0000);

					// Set the interpolated pixel value
					upSampled.setRGB(i, j, tempPixel);
				}
			}

			// Second Pass - For Remaining Pixels
			for (int j=3;j<h2-3;j++) {
				for (int i=3-(j%2);i<w2-3;i+=2) {

					int tempPixel = 0xFF000000;
					
					// Get the neighbor pixel values for  the derivative calculation 
					I1 = upSampled.getRGB(i-1,j-2);
					I2 = upSampled.getRGB(i+1,j-2);
					I3 = upSampled.getRGB(i-2,j-1);
					I4 = upSampled.getRGB(i  ,j-1);
					I5 = upSampled.getRGB(i+2,j-1);
					I6 = upSampled.getRGB(i-1,j);
					I7 = upSampled.getRGB(i+1,j);
					I8 = upSampled.getRGB(i-2,j+1);
					I9 = upSampled.getRGB(i  ,j+1);
					I30 = upSampled.getRGB(i+2,j+1);
					I31 = upSampled.getRGB(i-1,j+2);
					I32 = upSampled.getRGB(i+1,j+2);

					// Interpolate blue channel
					int I11Blue = (I3&0xFF) + (I4&0xFF) + (I5&0xFF) 
							+ (I8&0xFF) + (I9&0xFF) + (I30&0xFF) 
							- 3*(I6&0xFF) -3*(I6&0xFF);

					int I22Blue = (I1&0xFF) + (I6&0xFF) + (I31&0xFF) 
							+ (I2&0xFF) + (I7&0xFF) + (I32&0xFF) 
							- 3*(I4&0xFF) -3*(I9&0xFF);

					if(I11Blue > I22Blue)
						tempPixel = tempPixel | ((int)((I4&0xFF) + (I9&0xFF))/2);
					else
						tempPixel = tempPixel | ((int)((I6&0xFF) + (I7&0xFF))/2);					

					// Interpolate green channel
					int I11Green = ((I3>>8)&0xff) + ((I4>>8)&0xff) + ((I5>>8)&0xff) 
							+ ((I8>>8)&0xff) + ((I9>>8)&0xff) + ((I30>>8)&0xff) 
							- 3*((I6>>8)&0xff) - 3*((I7>>8)&0xff);

					int I22Green = ((I1>>8)&0xff) + ((I6>>8)&0xff) + ((I31>>8)&0xff) 
							+ ((I2>>8)&0xff) + ((I7>>8)&0xff) + ((I32>>8)&0xff) 
							- 3*((I4>>8)&0xff) - 3*((I9>>8)&0xff);

					if(I11Green > I22Green)
						tempPixel = tempPixel | ((((int)((((I4>>8)&0xff) + ((I9>>8)&0xff))/2))<<8)&0xff00);
					else
						tempPixel = tempPixel | ((((int)((((I6>>8)&0xff) + ((I7>>8)&0xff))/2))<<8)&0xff00);

					// Interpolate red channel
					int I11Red = ((I3>>8)&0xff) + ((I4>>8)&0xff) + ((I5>>8)&0xff) 
							+ ((I8>>8)&0xff) + ((I9>>8)&0xff) + ((I30>>8)&0xff) 
							- 3*((I6>>8)&0xff) - 3*((I7>>8)&0xff);

					int I22Red = ((I1>>16)&0xff) + ((I6>>16)&0xff) + ((I31>>16)&0xff) 
							+ ((I2>>16)&0xff) + ((I7>>16)&0xff) + ((I32>>16)&0xff) 
							- 3*((I4>>16)&0xff) - 3*((I9>>16)&0xff);

					if(I11Red > I22Red)
						tempPixel = tempPixel | ((((int)((((I4>>16)&0xff) + ((I9>>16)&0xff))/2))<<16)&0xff0000);
					else
						tempPixel = tempPixel | ((((int)((((I6>>16)&0xff) + ((I7>>16)&0xff))/2))<<16)&0xff0000);

					// Set the interpolated pixel value
					upSampled.setRGB(i, j, tempPixel);
				}
			}

			try {
				ImageIO.write(upSampled, "png", new File("upsampledimage.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			upsampledFrames.add(upSampled);
			System.out.println(f);
		}

		System.out.print("Up sampled succesfully!\n");
		return upsampledFrames;
	}
}
