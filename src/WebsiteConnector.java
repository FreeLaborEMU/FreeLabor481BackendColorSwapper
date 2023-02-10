import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public class WebsiteConnector {

	
	private BufferedImage originalImg; //this is the orignial img
	private BufferedImage newImg; // this is the new img
	private int[] collorPallet;//this is brickmaniacs colorpallet
	
	//this class should handle all I/O opperations with the website/firebase
	public WebsiteConnector() {
		
		
		originalImg=getImg();
		collorPallet=getColorPallet();
		newImg=ImgManager(originalImg, collorPallet);
		
	}
	
	//this should get the img from the website and return it
	private BufferedImage getImg() {
		BufferedImage img =null;
		
		return img;
	}
	// gets the color pallet from firebae
	private int[] getColorPallet() {
		int[] pallet =null;
		
		return pallet;
	}
	
	//this should send the img returned by imgManager might instead be of type void IDK how the new code will work
	private BufferedImage sendImg() {
		BufferedImage img =null;
		
		return img;
	}
	
	
	
	

}
