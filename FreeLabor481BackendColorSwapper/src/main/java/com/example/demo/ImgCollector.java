package com.example.demo;

import com.google.api.core.ApiFuture;

import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.IdTokenCredentials;
import com.google.auth.oauth2.IdTokenProvider;
import com.google.cloud.firestore.*;
import com.google.cloud.storage.Blob;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.cloud.StorageClient;
import com.google.cloud.storage.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.*;
import jakarta.websocket.RemoteEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
@RestController
@RequestMapping(path = "/imgCollector")
public class ImgCollector {

    private String Name;
    private String Url;


    private GoogleCredentials cred;

  //  public ImgCollector(GoogleCredentials credentials) {
  //    cred=credentials;
    //}

    public void setCredentials(GoogleCredentials credentials) {
        cred = credentials;
    }


    @GetMapping(path = "/download")
    public String Download() throws ExecutionException, InterruptedException {
        String firestorage;
        Firestore db = FirestoreClient.getFirestore();
        Iterable<DocumentReference> has =db.collection("users").listDocuments();
        ApiFuture<DocumentSnapshot> future;
        DocumentSnapshot document;

        for(DocumentReference docstore: has)
        {


            future=docstore.get();
            document= future.get();
            firestorage =document.getData().values().toString();
            String[] blob=firestorage.split(",");
            blob[0]=blob[0].substring(1,blob[0].length());
            blob [1]=blob[1].trim();
            blob[1]=blob[1].substring(0,blob[1].length());
            blob[2]=blob[2].substring(0,blob[2].length()-1);
            blob [2]=blob[2].trim();


                System.out.println(blob[1] + " | " + blob[2]);
                Name=blob[2];
                Url= blob[1];


                return blob[1];


            }





        return "done";

    }



    @GetMapping(path = "/downloadV2")
    public void DownloadV2() throws ExecutionException, InterruptedException, IOException {
        Storage storage = StorageOptions.newBuilder().setCredentials(cred).build().getService();
        BlobId blobId= BlobId.of("colorswapper-f6b50.appspot.com","files/dogo");
        //  storage.downloadTo(blobId,);

        Resource resource= new ClassPathResource("images");
       // InputStream file= ;

       // Path check =();
        Blob blob= storage.get(blobId);
        blob.downloadTo(Path.of("C:\\FreeLabor481BackendColorSwapper\\FreeLabor481BackendColorSwapper\\dat"));
    //    System.out.print(check);

       //



    }


    @GetMapping(path = "/upload")
    public synchronized void Upload(InputStream upload) throws ExecutionException, InterruptedException, IOException {


            Storage storage = StorageOptions.newBuilder().setCredentials(getCred()).build().getService();
            Firestore db = FirestoreClient.getFirestore();

            BlobId blobId=BlobId.of("colorswapper-f6b50.appspot.com","images/"+"User"+"/Copy");
            BlobInfo blobInfo= BlobInfo.newBuilder(blobId).setContentType("image/png").build();

            Blob uploading =storage.create(blobInfo,upload);
            System.out.print("OK "+Url);



    }

    public GoogleCredentials getCred() {
        return cred;
    }




}