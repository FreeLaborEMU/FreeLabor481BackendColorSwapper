package com.example.demo;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

/*
 * Note to self
 * TO send the data to the front end dump everything in Color object class
 * then use the get methods to create a dummy object
 * this dummy object should have the paramaters
 * R G B just as values stored in the object
 * because the front end recives objects as generic objects with no accses to its methods but acceses to its data values
 */

public class ImgManager {

	public static void main(String[] args) {
		ImgManager manager = new ImgManager();
		Color[][] palleteFromOriginal = manager.getColorArray(manager.originalImg);
		// add a color[] that just removes the dupes for of the Original Color Array
		// so Prof can see the array of original colors in img and the new colors side
		// by side
		Color[] palleteFromWebsite = new Color[16];

		// update instead of 0-255 it wants 0-1 where 1=255
		// also it NEEDS to be spesifed that its a float for some reson
		//
		palleteFromWebsite[0] = new Color((float) 0.0, (float) 0.0, (float) 1.0);
		palleteFromWebsite[1] = new Color((float) 0.0, (float) 1.0, (float) 0.0);
		palleteFromWebsite[2] = new Color((float) 1.0, (float) 0.0, (float) 0.0);
		palleteFromWebsite[3] = new Color((float) 0.0, (float) 0.0, (float) 0.0);
		palleteFromWebsite[4] = new Color((float) 0.0, (float) 0.5, (float) 0.0);
		palleteFromWebsite[5] = new Color((float) 0.5, (float) 0.0, (float) 0.0);
		palleteFromWebsite[6] = new Color((float) 0.5, (float) 0.5, (float) 0.5);
		palleteFromWebsite[7] = new Color((float) 0.0, (float) 0.0, (float) 0.5);
		palleteFromWebsite[8] = new Color((float) 0.0, (float) 0.5, (float) 0.0);
		palleteFromWebsite[9] = new Color((float) 1.0, (float) 1.0, (float) 1.0);
		palleteFromWebsite[10] = new Color((float) 0.0, (float) 0.6, (float) 0.0);
		palleteFromWebsite[11] = new Color((float) 0.0, (float) 0.7, (float) 0.0);
		palleteFromWebsite[12] = new Color((float) 0.0, (float) 0.8, (float) 0.0);
		palleteFromWebsite[13] = new Color((float) 0.6, (float) 0.6, (float) 0.0);
		palleteFromWebsite[14] = new Color((float) 0.7, (float) 0.7, (float) 0.0);
		palleteFromWebsite[15] = new Color((float) 0.8, (float) 0.8, (float) 0.0);
		Color[][] palleteForClone = manager.makeNewColorArrayLocations(palleteFromWebsite, palleteFromOriginal);
		BufferedImage clone = clone(manager.originalImg);
		BufferedImage newImg = manager.makeNewImg(clone, palleteForClone);

		displayImage(clone);
		displayImage(newImg);
		displayImage(manager.originalImg);

		System.out.println(Integer.toBinaryString(newImg.getRGB(0, 0)));
		System.out.println(newImg.getColorModel());
	}

	// stole this from
	// http://www.java2s.com/example/java/2d-graphics/display-bufferedimage.html
	// in order to test displaying the img's
	public static void displayImage(final BufferedImage image) {
		displayImage("test", image);
	}

	public static void displayImage(final String windowTitle, final BufferedImage image) {
		new JFrame(windowTitle) {
			{
				final JLabel label = new JLabel("", new ImageIcon(image), 0);
				add(label);
				pack();
				setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				setVisible(true);
			}
		};
	}
	// end of stuff from java2s

	private BufferedImage originalImg; // this is the orignial img
	private BufferedImage newImg; // this is the new img
	private Color[] colorPallet;// this is brickmaniacs colorpallet

	// size of [][] should be set by the size of originalImg
	private Color[][] OriginalColorArrayLocations; // this is the array that hold the RGB # values for original photo
	private Color[][] newColorArrayLocations; // this is the array that hold the RGB # values for new photo

	// this class will handle everything related to altering the img

	// normal constructor will be called by website Connector after it gets the img
	public ImgManager(BufferedImage original, Color[] pallet) {

		colorPallet = pallet;
		originalImg = original;
		OriginalColorArrayLocations = getColorArray(originalImg);
		newColorArrayLocations = makeNewColorArrayLocations(colorPallet, OriginalColorArrayLocations);
		newImg = makeNewImg(originalImg, newColorArrayLocations);

	}

	// default construtor for testing I added the first low res photo I found on
	// google
	// also shows example of how BufferdImage wants its information
	public ImgManager() {
		Resource resource = new ClassPathResource("images/Doomguy.jpg");
		try {
			File file = resource.getFile();
			originalImg = ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// this method gets the array of color objects that corespond to their location
	// on the img
	// ex pixle 0,0 has color X Y Z
	public Color[][] getColorArray(BufferedImage img) {
		Color[][] returnArray = new Color[img.getHeight()][img.getWidth()];

		// System.out.println(returnArray.length);
		// System.out.println(returnArray[0].length);
		// System.out.println(img.getHeight());

		for (int i = 0; i < img.getHeight(); i++) {
			for (int j = 0; j < img.getWidth(); j++) {
				// get RGB value of pixle as 1 value
				int RGBValue = img.getRGB(j, i);
				// put it into a color object which then has methods to seperate the color
				Color colorOfPixle = new Color(RGBValue, true);

				returnArray[i][j] = colorOfPixle;

			}
		}

		return returnArray;
	}

	// this method will get the color Array for the new img to be aplied to a clone
	// of the original img makeing the new img
	public Color[][] makeNewColorArrayLocations(Color[] pallet, Color[][] originalImgArray) {
		// make new array same size as old one
		Color[][] newImgArray = originalImgArray;

		// loop for originalImgArray
		for (int i = 0; i < originalImgArray[0].length; i++) {
			for (int j = 0; j < originalImgArray.length; j++) {

				// infinitly large distance reset before going into pallet array
				double leastDistance = 1000000000;
				int leastDistanceLocation = -1;

				// loop for for pallet
				for (int z = 0; z < pallet.length; z++) {

					if (leastDistance > getColorDistance(pallet[z], originalImgArray[j][i])) {
						leastDistance = getColorDistance(pallet[z], originalImgArray[j][i]);
						leastDistanceLocation = z;
					}

				}
				// at end of looping pallet put the found least distance color in the new arrray
				newImgArray[j][i] = pallet[leastDistanceLocation];
			}
		}

		return newImgArray;
	}

	// this is a helper method for makenewcolorarraylocations it just aplys the
	// formula and returns the distance given the two colors
	private double getColorDistance(Color imgColor, Color palletColor) {

		int imgRed = imgColor.getRed();
		int imgBlue = imgColor.getBlue();
		int imgGreen = imgColor.getGreen();

		int palRed = palletColor.getRed();
		int palBlue = palletColor.getBlue();
		int palGreen = palletColor.getGreen();

		double distance = -1;

		// big plug and chug formula
		distance = Math.sqrt(((palRed - imgRed) * (palRed - imgRed)) + ((palGreen - imgGreen) * (palGreen - imgGreen))
				+ ((palBlue - imgBlue) * (palBlue - imgBlue)));

		return distance;
	}

	// makes the new img given clone of origina img and the new collor pallet array
	// locations
	public BufferedImage makeNewImg(BufferedImage clone, Color[][] newPallet) {

		for (int i = 0; i < newPallet[0].length; i++) {
			for (int j = 0; j < newPallet.length; j++) {

				/*
				 * The TYPE_INT_ARGB represents Color as an int (4 bytes) with alpha channel in
				 * bits 24-31, red channels in 16-23, green in 8-15 and blue in 0-7.
				 *
				 */

				// what im doing here is turning the R G B values into binary Strings
				String red = Integer.toBinaryString(newPallet[j][i].getRed());
				String green = Integer.toBinaryString(newPallet[j][i].getGreen());
				String blue = Integer.toBinaryString(newPallet[j][i].getBlue());
				String alpha = Integer.toBinaryString(newPallet[j][i].getAlpha());

				// inputing as floats fixes the conversion problem so this code works

				// System.out.println(fillto8(blue)+fillto8(green)+fillto8(red));

				green = fillto8(green);
				blue = fillto8(blue);
				red = fillto8(red);
				// red="11111111";
				alpha = fillto8(alpha);

				String BinaryString = alpha + red + green + blue;

				int intBinaryString = getARGB(BinaryString);

				clone.setRGB(i, j, intBinaryString);

			}
		}

		return clone;
	}

	private String fillto8(String str) {

		int startL = str.length();

		for (int i = 0; i < 8 - startL; i++) {
			str = "0" + str;
		}

		return str;
	}

	// Source Siyuan Jiang
	// takes the initial String made by adding the indiviusal 8 bits together
	public static int getARGB(String argbStr) {
		int aRGB = 0;

		// edits the Int value directly useing Binary rather than standerd Int methods
		for (int i = 0; i < argbStr.length(); i++) {
			char bit = argbStr.charAt(i);
			int position = 31 - i;
			if (bit == '1')
				aRGB = setBit(aRGB, 1, position);
			else
				aRGB = setBit(aRGB, 0, position);
		}
		return aRGB;
	}

	// Source Siyuan Jiang
//helper for getARGB
	public static int setBit(int number, int bit, int position) {
		number |= bit << position; // position is zero based index
		return number;
	}

	// Source
	// https://bytenota.com/java-cloning-a-bufferedimage-object/
	public static BufferedImage clone(BufferedImage bufferImage) {
		ColorModel colorModel = bufferImage.getColorModel();
		WritableRaster raster = bufferImage.copyData(null);
		boolean isAlphaPremultiplied = colorModel.isAlphaPremultiplied();
		return new BufferedImage(colorModel, raster, isAlphaPremultiplied, null);
	}

	// this method takes all the colors from the img and removes duplicates
	// it uses an arrayList before returning a reguler array since from what I was
	// told simpler data types are easyer for the website to read
	public static Color[] getColorPalletForUser(Color[][] imgPallet) {
    	
    	ArrayList<Color> TempPalletForUser = new ArrayList<Color>();
    	
    	Color [] palletForUser;
    	
    	//fill out Array list with all the colors
    	for(int i=0; i<imgPallet.length; i++) {
        	for	(int j=0; j<imgPallet[0].length; j++) {
        		
        		TempPalletForUser.add(imgPallet[i][j]);	
        	}
        	}
    	
    	
    	//remove dupes, color has its own equals which should be abble to tell if colors are equal
    	for(int i=0; i<TempPalletForUser.size()-1; i++) {
    		
    		if(TempPalletForUser.get(i).equals(TempPalletForUser.get(i+1))){
    			TempPalletForUser.remove(i+1);
    			
    		}
    		
    	}
    	palletForUser=new Color [TempPalletForUser.size()];
    	// turn the array list into a normal array
    	for(int i=0; i<TempPalletForUser.size(); i++) {
    		palletForUser[i]=TempPalletForUser.get(i);	
    	}
    			
    	return palletForUser;
    }

	/**
	 * @return the originalImg
	 */
	public BufferedImage getOriginalImg() {
		return originalImg;
	}

	/**
	 * @param originalImg the originalImg to set
	 */
	public void setOriginalImg(BufferedImage originalImg) {
		this.originalImg = originalImg;
	}

	/**
	 * @return the newImg
	 */
	public BufferedImage getNewImg() {
		return newImg;
	}

	/**
	 * @param newImg the newImg to set
	 */
	public void setNewImg(BufferedImage newImg) {
		this.newImg = newImg;
	}

	/**
	 * @return the colorPallet
	 */
	public Color[] getColorPallet() {
		return colorPallet;
	}

	/**
	 * @param colorPallet the colorPallet to set
	 */
	public void setColorPallet(Color[] colorPallet) {
		this.colorPallet = colorPallet;
	}

	/**
	 * @return the originalColorArrayLocations
	 */
	public Color[][] getOriginalColorArrayLocations() {
		return OriginalColorArrayLocations;
	}

	/**
	 * @param originalColorArrayLocations the originalColorArrayLocations to set
	 */
	public void setOriginalColorArrayLocations(Color[][] originalColorArrayLocations) {
		OriginalColorArrayLocations = originalColorArrayLocations;
	}

	/**
	 * @return the newColorArrayLocations
	 */
	public Color[][] getNewColorArrayLocations() {
		return newColorArrayLocations;
	}

	/**
	 * @param newColorArrayLocations the newColorArrayLocations to set
	 */
	public void setNewColorArrayLocations(Color[][] newColorArrayLocations) {
		this.newColorArrayLocations = newColorArrayLocations;
	}

}
