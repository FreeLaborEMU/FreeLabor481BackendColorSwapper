package com.example.demo;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;






import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class FreeLabor481BackendColorSwapperApplication {

	public static void main(String[] args) throws IOException {
		// Use a service account	localhost:8080/colorPalette/upload
		InputStream serviceAccount = new FileInputStream("./colorswapper-firebase.json");
		GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(credentials)
				.build();

		boolean hasApp = false;
		List<FirebaseApp> firebaseApps = FirebaseApp.getApps();
		for(FirebaseApp app : firebaseApps) {
			if(app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)){
				hasApp=true;
				break;
			}
		}
		if(!hasApp) {
			FirebaseApp.initializeApp(options);
		}
		SpringApplication.run(FreeLabor481BackendColorSwapperApplication.class, args);

		ImgCollector firefiles = new ImgCollector(credentials);
		Resource resource= new ClassPathResource("images/convertedImage.jpg");
		InputStream upload= resource.getInputStream();



		String url;

		try {

			url = firefiles.Download();

		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		System.out.print(url);
		ImgManager manager = new ImgManager();

			HttpURLConnection connect=null;
			connect=(HttpURLConnection) new URL(url).openConnection() ;
			connect.connect();


		BufferedImage oringal=ImageIO.read(connect.getInputStream());
		manager.setOriginalImg(oringal);
		Color[][] palleteFromOriginal = manager.getColorArray(manager.getOriginalImg());
		// add a color[] that just removes the dupes for of the  Original Color Array
		//so Prof can see the array of original colors in img and the new colors side by side
		Color[] palleteFromWebsite = new Color[16];

		//update instead of 0-255 it wants 0-1 where 1=255
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
		BufferedImage clone = ImgManager.clone(manager.getOriginalImg());
		BufferedImage newImg = manager.makeNewImg(clone, palleteForClone);

		System.out.println(Integer.toBinaryString(newImg.getRGB(0, 0)));
		System.out.println(newImg.getColorModel());


		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(newImg,"jpeg",os);
		InputStream ok= new ByteArrayInputStream(os.toByteArray());
		try {
			firefiles.Upload(ok);
		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		//try {

			//while (!bob.equals("done"))
		//	{
				//mo.DownloadV2();

					//bob = mo.Download();

					//	mo.Upload(upload);



			//		temp=bob;
			//	System.out.println(temp+" temp");

		//	}
//	} catch (ExecutionException e) {

	//		throw new RuntimeException(e);
	//	} catch (InterruptedException e) {
	//		throw new RuntimeException(e);
	//	}




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
}
