package Video9;

import java.awt.Graphics;
import java.awt.image.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Video implements Serializable {

	private static final long serialVersionUID = -3254615303479932485L;
	private int videoLength, imageHeight, imageWidth;
	private transient List<BufferedImage> Images;


	public List<BufferedImage> getImages() {
		return Images;
	}

	public Video(int videoLength, int imageHeight, int imageWidth,
			List<BufferedImage> ImageSequence) {
		super();
		this.videoLength = videoLength;
		this.imageHeight = imageHeight;
		this.imageWidth = imageWidth;
		this.Images = ImageSequence;
	}

	private void writeObject(ObjectOutputStream out) throws IOException {


		int height, width;
		if(videoLength > 50){
			height = 50;
			width = (int) Math.ceil(((double)videoLength)/50);
		}
		else{
			height = videoLength;
			width = 1;
		}

		BufferedImage hugeImage = new BufferedImage(Images.get(0).getWidth()*width, Images.get(0).getHeight()*height, BufferedImage.TYPE_INT_ARGB);
		Graphics g2d = hugeImage.createGraphics();

		int x=0;
		int y=0;
		for(int i=0; i<videoLength; i++){
			if(y>=50){y=0; x++;}
			g2d.drawImage(Images.get(i), x*Images.get(0).getWidth(), y*Images.get(0).getHeight(), null);
			y++;
		}

		//TODO
		//serialize this file
		out.defaultWriteObject();
		ImageIO.write(hugeImage, "png", out); // png is lossless
		System.out.print("Saved!\n");

	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {

		in.defaultReadObject();
		BufferedImage hugeImage = ImageIO.read(in);
		int x=0, y=0;
		
		Images = new ArrayList<BufferedImage>();

		for(int i=0; i<videoLength; i++){
			if(y>=50){y=0; x++;}
			BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
			image = hugeImage.getSubimage(x*imageWidth, y*imageHeight, imageWidth, imageHeight);
			y++;
			Images.add(image);
		}	
		
	}
}