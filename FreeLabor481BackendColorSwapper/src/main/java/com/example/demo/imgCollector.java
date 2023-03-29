package com.example.demo;

import com.google.api.core.ApiFuture;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.cloud.StorageClient;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.storage.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.*;
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

@RequestMapping(path = "/imgCollector")
public class imgCollector {

    private String url;




    @GetMapping(path = "/download")
    public String Download() throws ExecutionException, InterruptedException {
        String firestorage;
        Firestore db = FirestoreClient.getFirestore();
        Iterable<DocumentReference> has =db.collection("userstest").listDocuments();
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
             blob[1]=blob[1].substring(0,blob[1].length()-1);

             if(blob[1].equals("false"))
             {
                 System.out.println(blob[0] + "(" + blob[1]);
                 url=blob[0];
                 return blob[0];

             }


        }

                url="done";
                return "done";

    }


 


    @GetMapping(path = "/upload")
    public void Upload(GoogleCredentials credentials, InputStream upload) throws ExecutionException, InterruptedException, IOException {
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        BlobId blobId=BlobId.of("colorswapper-f6b50.appspot.com","text.txt");
        BlobInfo blobInfo= BlobInfo.newBuilder(blobId).setContentType("text/plain").build();
        Blob uploading =storage.create(blobInfo,upload);
    }




}
