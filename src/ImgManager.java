import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;




public class ImgManager {

	private BufferedImage originalImg; //this is the orignial img
	private BufferedImage newImg; // this is the new img
	private int[] collorPallet;//this is brickmaniacs colorpallet
			
	//size of [][] should be set by the size of originalImg
	private int[][] OriginalColorArrayLocations; // this is the array that hold the RGB # values for original photo
	private int[][] newColorArrayLocations; // this is the array that hold the RGB # values for new photo
	
	
	//this class will handle everything related to altering the img
	
	//normal constructor will be called by website Connector after it gets the img
	public ImgManager( BufferedImage original, int[] collorPallet) {
		
		int[] pallet=collorPallet;
		BufferedImage originalImg= original;
		
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
	
	

}
