package com.example.demo;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@RestController
@RequestMapping(path = "/colorConversion")
public class ColorConversionController {
	@Autowired
	private ColorConversionDAO colorConversionDAO;
	
    @GetMapping(path = "/convert")
    public void convert() {
    	this.colorConversionDAO.convert();
    }
    
    @GetMapping(path = "/convertedImageColors")
    public ArrayList<FrontendColor> convertedImageColors() {
    	Color[]  arrayColors = this.colorConversionDAO.convertedImageColors();
    	ArrayList<FrontendColor> colors = new ArrayList<FrontendColor>();
    	for(int i=0; i<100; i++) {
    		FrontendColor color = new FrontendColor();
    		color.name = "holder";
    		color.redValue = arrayColors[i].getRed();
    		color.greenValue = arrayColors[i].getGreen();
    		color.blueValue = arrayColors[i].getBlue();
    		
    		colors.add(color);
    	}
    	return colors;
    }
}