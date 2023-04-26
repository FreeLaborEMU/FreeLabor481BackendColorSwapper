package com.example.demo;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;






import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
@RestController
@RequestMapping(path = "/main")
public class FreeLabor481BackendColorSwapperApplication {


	public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
		SpringApplication.run(FreeLabor481BackendColorSwapperApplication.class, args);
			convert();
	}

	// Create a firebase instance and get files from fireebase

	@GetMapping(path = "/convert")
	static String convert() throws IOException, ExecutionException, InterruptedException {

		// Use a service account	localhost:8080/colorPalette/upload
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


		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(newImg, "jpeg", os);
		InputStream ok = new ByteArrayInputStream(os.toByteArray());


		firefiles.Upload(ok);

		return "OK";
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
