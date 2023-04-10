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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
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

public class fileCollector {
    private String Name;
    private String Url;
    private GoogleCredentials cred;
    public fileCollector(GoogleCredentials credentials) {
        cred=credentials;
    }

    @GetMapping(path = "/downloadV2")
    public void DownloadFile() throws ExecutionException, InterruptedException, IOException {
        Storage storage = StorageOptions.newBuilder().setCredentials(cred).build().getService();
        BlobId blobId= BlobId.of("colorswapper-f6b50.appspot.com","files/dogo");

        //Resource resource= new ClassPathResource("images");

        Blob blob= storage.get(blobId);
        blob.downloadTo(Path.of("C:\\Users\\MGRM\\Documents\\GitHub\\dat"));
    }

}
