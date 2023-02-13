import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;



/*
 * Note to self
 * TO send the data to the front end dump everything in Color object class
 * then use the get methods to create a dummy object
 * this dummy object should have the paramaters 
 * R G B just as values stored in the object
 * because the front end recives objects as generic objects with no accses to its methods but acceses to its data values
 */


public class ImgManager {

	private BufferedImage originalImg; //this is the orignial img
	private BufferedImage newImg; // this is the new img
	private int[] collorPallet;//this is brickmaniacs colorpallet
			
	//size of [][] should be set by the size of originalImg
	private Color[][] OriginalColorArrayLocations; // this is the array that hold the RGB # values for original photo
	private Color[][] newColorArrayLocations; // this is the array that hold the RGB # values for new photo
	
	
	//this class will handle everything related to altering the img
	
	//normal constructor will be called by website Connector after it gets the img
	public ImgManager( BufferedImage original, int[] collorPallet) {
		
		int[] pallet=collorPallet;
		BufferedImage originalImg= original;
		OriginalColorArrayLocations=getColorArray(originalImg);
		
	}
	//default construtor for testing I added the first low res photo I found on google
	//also shows example of how BufferdImage wants its information
	public ImgManager() {
		File file = new File("Doomguy.jpg");
		try {
			BufferedImage originalImg=ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	
	
	// this method gets the array of color objects that corespond to their location on the img
	// ex pixle 0,0 has color X Y Z
	private Color[][] getColorArray (BufferedImage img) {
		Color[][] returnArray= new Color [img.getHeight()][img.getWidth()];
		
		for (int i = 0; i < img.getHeight(); i++) {
	         for (int j = 0; j < img.getWidth(); j++) {
	         //get RGB value of pixle as 1 value
	         int RGBValue=img.getRGB(i,j);
	         //put it into a color object which then has methods to seperate the color
	         Color colorOfPixle = new Color(RGBValue, true);
	        	
	         returnArray[i][j]=colorOfPixle;
	         
	         }
	         }
		
		return returnArray;
	}
	
	
	
	

}
