/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Video9;

import scalingAlgorithms.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 *
 * @author Jeannie
 */
public class Main {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		
		//Encoder e = new Encoder("/home/osarenk1/Desktop/images", "compressed.ser", 2);
		//Encoder e = new Encoder("/home/onur/git/Images", "compressed.ser", 2);
		Decoder d = new Decoder("compressed.ser");
		/*
		BufferedImage test = null;
		try {
			test = ImageIO.read(new File("/home/onur/git/Images/wall_e.jpeg"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Upsampler up = new Upsampler();
		BufferedImage upsampled = up.BilinearUpsample(test, test.getWidth(), test.getHeight(), test.getWidth()*3, test.getHeight()*3);
		
		// Convolve with an sharpening filter
		BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, new float[]{-1, -1, -1,-1, 9, -1,-1, -1, -1}));
		upsampled = op.filter(upsampled, null);
		try {
			ImageIO.write(upsampled, "png", new File("bilinear.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		/*
		int argsLength = args.length;
		String EXTENSION = ".ser";
		int EXTENSIONLength = EXTENSION.length();

		if (argsLength > 0) {

			Commands cmd = Commands.valueOf(args[0]);
			
			switch (cmd) {
			case gui:
				@SuppressWarnings("unused")
				GUI gui = new GUI();
				break;

			case encode:

				if (argsLength < 4 && !"--output".equals(args[argsLength - 2])){ // if "--output" flag not found, exit encoding
					System.out.println("Command format invalid. No '--output' flag found or not enough arguments entered.");
					System.out.println("Encoding failed. Exiting program...");
				}
				else {

					// check if GUI gui = new GUI();output file has correct filename extension
					String outputFilename = args[argsLength - 1];
					int outputFileLength = outputFilename.length();
					String thisFileExtension;
					thisFileExtension = new String (outputFilename.substring(outputFileLength - EXTENSIONLength, outputFileLength));

					if (EXTENSION.equals(thisFileExtension) && outputFileLength > EXTENSIONLength){

						// pass all filenames to encode function
						String[] imageFiles;
						imageFiles = new String [argsLength - 3];

						for (int ii = 1; ii < argsLength - 2; ii++){
							imageFiles[ii - 1] = args[ii];
						}
						System.out.println(imageFiles[0]);
						System.out.println("Encoding started...\n");
						Encoder e = new Encoder(imageFiles, outputFilename);
						System.out.println("Encoding completed...\n");
					}
					else {
						System.out.println("Output file must be specified with extension " + EXTENSION + " Please try again. Exiting program...");
					}
				}
				break;

			case view:
				if (argsLength != 2 || args[1].length() <= EXTENSIONLength) {
					System.out.println("Incorrect view command format. Exiting program...");
				}
				else {
					String outputFilename = args[1];
					int outputFileLength = outputFilename.length();

					String thisFileExtension;
					thisFileExtension = new String (outputFilename.substring(outputFileLength - EXTENSIONLength, outputFileLength));

					if (EXTENSION.equals(thisFileExtension)){
						System.out.println("Viewer launching...\n");
						Decoder d = new Decoder(outputFilename);
					}
					else {
						System.out.println("Output file must be a " + EXTENSION + " file. Please try again. Exiting program...");
					}

				}
				break;
			default:
				System.out.println("Command not specified...");
				System.out.println("Exiting program...");


			}
		}
		else {
			System.out.println("Command not specified...");
			System.out.println("Exiting program...");
		}
		*/
	}
	
	public enum Commands{
		gui,
		encode,
		view
	}

}