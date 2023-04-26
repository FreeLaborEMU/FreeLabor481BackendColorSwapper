package com.example.demo;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
@Repository
public class ColorConversionDAO {

	public void convert() {
		try {


            //Creat firebase instance
			InputStream serviceAccount = new FileInputStream("./colorswapper-firebase.json");
			GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(credentials)
					.build();

			boolean hasApp = false;
			List<FirebaseApp> firebaseApps = FirebaseApp.getApps();
			for (
					FirebaseApp app : firebaseApps) {
				if (app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)) {
					hasApp = true;
					break;
				}
			}
			if (!hasApp) {
				FirebaseApp.initializeApp(options);
			}

			// Get the color pallet
			ColorConversionDAO color =new ColorConversionDAO();
			ImgCollector firefiles = new ImgCollector();
			firefiles.setCredentials(credentials);

			// Get images
			String url;
			url = firefiles.Download();

			firefiles.DownloadV2();
			System.out.print(url);
			ImgManager manager = new ImgManager();

			HttpURLConnection connect = null;
			connect = (HttpURLConnection) new

					URL(url).

					openConnection();
			connect.connect();


			BufferedImage oringal = ImageIO.read(connect.getInputStream());
			manager.setOriginalImg(oringal);
			Color[][] palleteFromOriginal = manager.getColorArray(manager.getOriginalImg());
			// add a color[] that just removes the dupes for of the  Original Color Array
			//so Prof can see the array of original colors in img and the new colors side by side
			Color[] palleteFromWebsite = color.retrieveLocalPalette();
			Color[][] palleteForClone = manager.makeNewColorArrayLocations(palleteFromWebsite, palleteFromOriginal);
			BufferedImage clone = ImgManager.clone(manager.getOriginalImg());
			BufferedImage newImg = manager.makeNewImg(clone, palleteForClone);

			System.out.println(Integer.toBinaryString(newImg.getRGB(0, 0)));
			System.out.println(newImg.getColorModel());

			// Send to firebase
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(newImg, "jpeg", os);
			InputStream ok = new ByteArrayInputStream(os.toByteArray());



			firefiles.Upload(ok);

			






		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}


	}

	public static void displayImage(final BufferedImage image) {
		Resource resource = new ClassPathResource("images/");
		String filename = "convertedImage.jpg";
		BufferedImage bi = image;
		try {
			File outputFile = new File(resource.getFile() + "/" + filename);
			ImageIO.write(bi, "jpg", outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Color[] convertedImageColors() {
		ImgManager manager = new ImgManager();
		Color[][] palleteFromOriginal = manager.getColorArray(manager.getOriginalImg());
		Color[] palleteFromWebsite =retrieveLocalPalette();
		Color[][] palleteForClone = manager.makeNewColorArrayLocations(palleteFromWebsite, palleteFromOriginal);
		BufferedImage clone = ImgManager.clone(manager.getOriginalImg());
		BufferedImage newImg = manager.makeNewImg(clone, palleteForClone);
		
		return ImgManager.getColorPalletForUser(palleteForClone);
	}
	
	public Color[] originalImageColors() {
		ImgManager manager = new ImgManager();
		Color[][] palleteFromOriginal = manager.getColorArray(manager.getOriginalImg());
		return ImgManager.getColorPalletForUser(palleteFromOriginal);
	}

	private ArrayList<RGBValues> rgbValues;

//    public static void main(String[] args) throws IOException {
//        RGBValuesIO test = new RGBValuesIO();
//        System.out.println(test.storeRGBValues());
//    }

	public ArrayList<RGBValues> storeRGBValues() throws IOException {
		ArrayList<RGBValues> rgbValues = new ArrayList<>();
		Path pathToFile = Paths.get("All_Paint_Mixes.dat");

		try (BufferedReader br = Files.newBufferedReader(pathToFile)) {
			int linesRead = 0;
			String loopLine = "";
			while ((loopLine = br.readLine()) != null) {
				String[] attributes = loopLine.split(",");
				if (linesRead == 0 || linesRead == 1) {
					linesRead++;
					continue;
				} else {

					attributes = loopLine.split(",");
					System.out.println(attributes);

					//if ((attributes.length > 1) || (attributes.length < 5)) {
					rgbValues.add(createRGB(attributes));
					//}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return rgbValues;
	}

	private static RGBValues createRGB(String[] value) {
		int id = Integer.parseInt(value[0]);
		String colorName = value[1];
		int redValue = Integer.parseInt(value[2]);
		int greenValue = Integer.parseInt(value[3]);
		int blueValue = Integer.parseInt(value[4]);
		String pieceName = value[5];

		return new RGBValues(id, colorName, redValue, greenValue, blueValue, pieceName);
	}

	public ArrayList<RGBValues> getRgbValues() {
		return rgbValues;
	}

	public void setRgbValues(ArrayList<RGBValues> rgbValues) {
		this.rgbValues = rgbValues;
	}

	public Color[] retrieveLocalPalette() {
		Color[] list = new Color[0];
		try {
			ArrayList<RGBValues> rgbValues = storeRGBValues();
			list = new Color[rgbValues.size()];
			for(int i=0; i< rgbValues.size(); i++){
				RGBValues color = rgbValues.get(i);
				Color realColor = new Color(color.getRedValue(), color.getGreenValue(), color.getBlueValue());
				list[i] = realColor;
			}
			return list;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}