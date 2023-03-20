package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.api.core.ApiFuture;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import com.google.firebase.cloud.FirestoreClient;

@RestController
@RequestMapping(path = "/colorPalette")
public class ColorPaletteController {
    @GetMapping(path = "/upload")
    public String upload() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection("colorpalette").document("darkblue");
        // Add document data  with id "darkblue" using a hashmap
        Map<String, Object> data = new HashMap<>();
        data.put("red", 22);
        data.put("green", 83);
        data.put("blue", 126);
        // other fields can be added in a similar way
        ApiFuture<WriteResult> result = docRef.set(data);
        // ...
        // result.get() blocks on response
        return  "Update time : " + result.get().getUpdateTime();
    }
}
