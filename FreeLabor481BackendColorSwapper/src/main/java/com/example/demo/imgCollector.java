package com.example.demo;

import com.google.api.core.ApiFuture;


import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RequestMapping(path = "/imgCollector")
public class imgCollector {

    private String url;




    @GetMapping(path = "/download")
    public String download() throws ExecutionException, InterruptedException {
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
             blob[0]=blob[0].substring(2,blob[0].length()-1);
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
    public void upload() throws ExecutionException, InterruptedException {

    }


}
