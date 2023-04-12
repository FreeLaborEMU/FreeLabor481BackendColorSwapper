package com.example.demo;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;






import java.io.FileInputStream;
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

		ImgCollector mo = new ImgCollector(credentials);
		Resource resource= new ClassPathResource("images/convertedImage.jpg");
		InputStream upload= resource.getInputStream();
		String bob= "ma";
		String temp="";
		try {

			//while (!bob.equals("done"))
		//	{
				mo.DownloadV2();

					//bob = mo.Download();
					if (!bob.equals("done") && !bob.equals(temp)) {
					//	mo.Upload(upload);

					}

			//		temp=bob;
			//	System.out.println(temp+" temp");

		//	}
	} catch (ExecutionException e) {

			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}




	}

}
