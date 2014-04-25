package Video9;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


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
		
		//GUI gui = new GUI();
		//Encoder e = new Encoder("/home/osarenk1/Desktop/images", "compressed.ser", 2);
		//Encoder e = new Encoder("/home/onur/git/Images", "compressed.ser", 2);
		//Decoder d = new Decoder("compressed.ser");
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
		
		int argsLength = args.length;
		String EXTENSION = ".ser";
		int EXTENSIONLength = EXTENSION.length();
		boolean isStoreBin = false; 
		int binArgIndex = argsLength-2;
		int index =0;

		if (argsLength > 0) {

			Commands cmd = Commands.valueOf(args[0]);
			
			switch (cmd) {
			case gui:
				//@SuppressWarnings("unused")
				GUI gui = new GUI();
	 			System.out.println("GUI OPEN");
				break;

			case encode:
				if(args[0].equals("encode") && argsLength == 1)
				{
					System.out.println("To use, please enter the following format:");
					System.out.println("encode [file1] [file2]... --output [outputFile.ser]");
					System.out.println("Option:");
					System.out.println("--bin : Store arbituary binary files. Put before --output argument");
					System.out.println("Example:");
					System.out.println("encode [file1] [file2]... --bin [bin1] [bin2]... --output [outputFile.ser]");
				}
				else if (args[1].equals("--bin") || argsLength < 4 || !"--output".equals(args[argsLength - 2])){ // if "--output" flag not found, exit encoding
					System.out.println("Command format invalid. No '--output' flag found or not enough arguments entered.");
					System.out.println("Encoding failed. Exiting program...");
				}
				else {
					for(int jj = 1; jj< argsLength-2; jj++)
					{
						if("--bin".equals(args[jj]))
						{
							isStoreBin = true;
							binArgIndex = jj;
							break;
						}
					}
				
					// check if GUI gui = new GUI();output file has correct filename extension
					String outputFilename = args[argsLength - 1];
					int outputFileLength = outputFilename.length();
					String thisFileExtension = null;
					Boolean isExtensionExist = false;
					for(int ii = outputFilename.length()-1; ii >=0; ii--)
					{
						//If a period doesn't exist in filename ie no extension
						if('.' == outputFilename.charAt(ii) && ii != outputFilename.length()-1)
						{
							isExtensionExist = true;
							thisFileExtension = new String (outputFilename.substring(ii, outputFileLength));
							break;
						}
					}
					//thisFileExtension = new String (outputFilename.substring(outputFileLength - EXTENSIONLength, outputFileLength));
					
					
					
					if (thisFileExtension != null  && EXTENSION.equals(thisFileExtension) && outputFileLength > EXTENSIONLength && isExtensionExist){

						// pass all filenames to encode function
						String[] imageFiles;
						String[] binFiles;
						GUI mygui = null;
						//Ask if binary argument was stated and if any binary files specified
						if (!isStoreBin || (argsLength-2 - binArgIndex) <= 1)
						{
							imageFiles = new String [argsLength - 3];
	
							for (int ii = 1; ii < argsLength - 2; ii++){
								if(!"--bin".equals(args[ii])){
									imageFiles[ii - 1] = args[ii];
									System.out.println("IMAGES:" +args[ii]);
								}

							}
							System.out.println(imageFiles[0]);
							System.out.println("Encoding started...\n");
							binFiles = null;
							Encoder e = new Encoder(imageFiles, binFiles, outputFilename, 1, mygui);
							//Encoder e = new Encoder(imageFiles, outputFilename);
							System.out.println("NO BINARY FILES");
							System.out.println("Encoding completed...\n");
						}
						else if(isStoreBin)
						{
							imageFiles = new String [binArgIndex-1];
							binFiles = new String [argsLength-2 - binArgIndex -1];
							//Loop through image files
							for(int jj = 1; jj < binArgIndex; jj++)
							{
								imageFiles[jj- 1] = args[jj];
								System.out.println("IMAGES:" + args[jj]);

							}
							//Loop through binary files
							for(int ii = binArgIndex+1; ii  < argsLength-2; ii++)
							{
								binFiles[index++] = args[ii];
								System.out.println("BINARY:" +args[ii]);

							}
							System.out.println("Encoding started...\n");
							System.out.println("BINARY FILES EXIST");
							
							if(binFiles.length == 0)
								binFiles = null;
							Encoder e = new Encoder(imageFiles, binFiles, outputFilename, 1, mygui);
							//Encoder e = new Encoder(imageFiles,binFiles, outputFilename,1);
							System.out.println("Encoding completed...\n");

						}
					}
				
					else {
						System.out.println("Output file must be specified with extension " + EXTENSION + " Please try again. Exiting program...");
					}
					
				}
				break;

			case view:
				if(args[0].equals("view") && argsLength == 1)
				{
					System.out.println("To use, please enter the following format:");
					System.out.println("view [outputFile.ser]");

				}
			    else if (argsLength != 2 || args[1].length() <= EXTENSIONLength) {
					System.out.println("Incorrect view command format. Exiting program...");
				}
				else {
					String outputFilename = args[1];
					int outputFileLength = outputFilename.length();

					String thisFileExtension = null;
					boolean isExtensionExist = false;
					//thisFileExtension = new String (outputFilename.substring(outputFileLength - EXTENSIONLength, outputFileLength));
					for(int ii = outputFilename.length()-1; ii >=0; ii--)
					{
						//If a period doesn't exist in filename ie no extension
						if('.' == outputFilename.charAt(ii) && ii != outputFilename.length()-1)
						{
							isExtensionExist = true;
							thisFileExtension = new String (outputFilename.substring(ii, outputFileLength));
							break;
						}
					}
					
					if (thisFileExtension != null && EXTENSION.equals(thisFileExtension)){
						System.out.println("Viewer launching...\n");
						GUI mygui = new GUI(outputFilename);
						
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
		
	}
	
		
	public enum Commands{
		gui,
		encode,
		view
	}

	
} 
