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
	private int SCALING_FACTOR;
	private final int FRAMES_PER_COL = 40;
	private List<byte[]> arbitrary;
	private List<String> arbitrary_name;
	
	public List<String> getArbitrary_name() {
		return arbitrary_name;
	}

	public void setArbitrary_name(List<String> arbitrary_name) {
		this.arbitrary_name = arbitrary_name;
	}

	public List<byte[]> getArbitrary() {
		return arbitrary;
	}

	public void setArbitrary(List<byte[]> arbitrary) {
		this.arbitrary = arbitrary;
	}

	public int getSCALING_FACTOR() {
		return SCALING_FACTOR;
	}

	public List<BufferedImage> getImages() {
		return Images;
	}

	public Video(int videoLength, int imageHeight, int imageWidth,
			List<BufferedImage> ImageSequence, int scaling) {
		super();
		this.videoLength = videoLength;
		this.imageHeight = imageHeight;
		this.imageWidth = imageWidth;
		this.Images = ImageSequence;
		this.SCALING_FACTOR = scaling;
		this.arbitrary = null;
	}

	private void writeObject(ObjectOutputStream out) throws IOException {


		int height, width;
		if(videoLength > FRAMES_PER_COL){
			height = FRAMES_PER_COL;
			width = (int) Math.ceil(((double)videoLength)/40);
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
			if(y>=FRAMES_PER_COL){y=0; x++;}
			g2d.drawImage(Images.get(i), x*Images.get(0).getWidth(), y*Images.get(0).getHeight(), null);
			y++;
			
			//guiHandler.progressbar.setNote(String.format("Encoding image %d of %d.\n", i , hr.size()));
		}
		
		//TODO
		//serialize this file
		out.defaultWriteObject();
		ImageIO.write(hugeImage, "png", out); // png is lossless
		
		/*
		 * FOR TESTING ONLY
		 * 
		File outputfile = new File("HUGE.png");
	    ImageIO.write(hugeImage, "png", outputfile);
		System.out.print("Saved!\n");
		*/
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {

		in.defaultReadObject();
		BufferedImage hugeImage = ImageIO.read(in);
		int x=0, y=0;
		
		Images = new ArrayList<BufferedImage>();

		for(int i=0; i<videoLength; i++){
			if(y>=FRAMES_PER_COL){y=0; x++;}
			BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
			image = hugeImage.getSubimage(x*imageWidth, y*imageHeight, imageWidth, imageHeight);
			y++;
			Images.add(image);
		}	
		
	}
}
