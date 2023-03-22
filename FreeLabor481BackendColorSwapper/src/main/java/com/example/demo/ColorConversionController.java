package com.example.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

@RestController
@RequestMapping(path = "/colorConversion")
public class ColorConversionController {
	@Autowired
	private ColorConversionDAO colorConversionDAO;
	
    @GetMapping(path = "/convert")
    public void convert() {
    	this.colorConversionDAO.convert();
    }
}