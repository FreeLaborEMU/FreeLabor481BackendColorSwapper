package com.example.demo;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;


import java.io.FileInputStream;
import java.util.List;

@SpringBootApplication
public class FreeLabor481BackendColorSwapperApplication {

	public static void main(String[] args) throws IOException {
		// Use a service account
		InputStream serviceAccount = new FileInputStream("./colorswapper-firebase.json");
		GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(credentials)
				.build();

		boolean hasApp = false;
		List<FirebaseApp> firebaseApps = FirebaseApp.getApps();
		for(FirebaseApp app : firebaseApps){
			if(app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)){
				hasApp=true;
				break;
			}
		}
		if(!hasApp){
			FirebaseApp.initializeApp(options);
		}
		SpringApplication.run(FreeLabor481BackendColorSwapperApplication.class, args);
	}

}
