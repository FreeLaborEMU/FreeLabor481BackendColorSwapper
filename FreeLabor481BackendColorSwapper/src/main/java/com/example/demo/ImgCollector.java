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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RequestMapping(path = "/ImgCollector")
public class ImgCollector {

    private String Name;
    private String Url;
    private GoogleCredentials cred;
    public ImgCollector(GoogleCredentials credentials) {
        cred=credentials;
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

            if(blob[0].equals("false"))
            {
                System.out.println(blob[1] + " | " + blob[2]);
                Name=blob[2];
                Url= blob[1];
                Map<String, Object> data = new HashMap<>();
                data.put("check",true);
                data.put("orginal",Url);
                data.put("username",Name);
                // other fields can be added in a similar way
                ApiFuture<WriteResult> result = db.collection("users").document(Name).set(data);


                return blob[1];


            }


        }

        Name="done";
        return "done";

    }



    @GetMapping(path = "/downloadV2")
    public String DownloadV2() throws ExecutionException, InterruptedException {
        Storage storage = StorageOptions.newBuilder().setCredentials(cred).build().getService();
        BlobId blobId= BlobId.of("colorswapper-f6b50.appspot.com","images/");
        return storage.get(blobId).toString();


    }


    @GetMapping(path = "/upload")
    public void Upload(InputStream upload) throws ExecutionException, InterruptedException, IOException {
        Storage storage = StorageOptions.newBuilder().setCredentials(cred).build().getService();
        Firestore db = FirestoreClient.getFirestore();

        BlobId blobId=BlobId.of("colorswapper-f6b50.appspot.com","images/"+Name+"/Copy");
        BlobInfo blobInfo= BlobInfo.newBuilder(blobId).setContentType("image/png").build();
        Blob uploading =storage.create(blobInfo,upload);



    }




}