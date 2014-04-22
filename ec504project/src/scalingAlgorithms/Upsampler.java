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
	
	public BufferedImage BilinearUpsample(BufferedImage pixels, int w, int h, int w2, int h2) {
	    
		BufferedImage bigImage = new BufferedImage(w2, h2, BufferedImage.TYPE_INT_RGB);

	    int a, b, c, d, x, y, index ;
	    float x_ratio = ((float)(w-1))/w2 ;
	    float y_ratio = ((float)(h-1))/h2 ;
	    float x_diff, y_diff, blue, red, green ;
	    //int offset = 0 ;
	    for (int i=0;i<h2;i++) {
	        for (int j=0;j<w2;j++) {
	            x = (int)(x_ratio * j) ;
	            y = (int)(y_ratio * i) ;
	            x_diff = (x_ratio * j) - x ;
	            y_diff = (y_ratio * i) - y ;
	            //index = (y*w+x) ;                
	            a = pixels.getRGB(x, y);
	            b = pixels.getRGB(x+1, y);
	            c = pixels.getRGB(x, y+1);
	            d = pixels.getRGB(x+1, y+1);
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

	            int tempRGB = 0x00000000 | // hardcode alpha
	                    ((((int)red)<<16)&0xff0000) |
	                    ((((int)green)<<8)&0xff00) |
	                    ((int)blue) ;
	            bigImage.setRGB(j, i, tempRGB);
	            
	                    
	        }
	    }
	    return bigImage;
	}


}
