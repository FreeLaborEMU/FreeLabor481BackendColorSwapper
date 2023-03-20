package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.api.core.ApiFuture;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
        try {
            ArrayList<RGBValues> rgbValues = storeRGBValues();

            for(int i=0; i < rgbValues.size(); i++) {
                DocumentReference docRef = db.collection("colorpalette").document(rgbValues.get(i).getColorName());
                // Add document data  with id "darkblue" using a hashmap
                Map<String, Object> data = new HashMap<>();
                data.put("red", rgbValues.get(i).getRedValue());
                data.put("green", rgbValues.get(i).getGreenValue());
                data.put("blue", rgbValues.get(i).getBlueValue());
                // other fields can be added in a similar way
                ApiFuture<WriteResult> result = docRef.set(data);
            }


        // ...
        // result.get() blocks on response
        return  "Successfully updated color palette";
        } catch (IOException e) {
            return "Failure to upload color palette";
        }
    }

    private ArrayList<RGBValues> rgbValues;

//    public static void main(String[] args) throws IOException {
//        RGBValuesIO test = new RGBValuesIO();
//        System.out.println(test.storeRGBValues());
//    }

    public ArrayList<RGBValues> storeRGBValues() throws IOException {
        ArrayList<RGBValues> rgbValues = new ArrayList<>();
        Path pathToFile = Paths.get("All_Paint_Mixes.dat");

        try (BufferedReader br = Files.newBufferedReader(pathToFile)) {
            int linesRead = 0;
            String loopLine = "";
            while ((loopLine = br.readLine()) != null) {
                String[] attributes = loopLine.split(",");
                if (linesRead == 0 || linesRead == 1) {
                    linesRead++;
                    continue;
                } else {

                    attributes = loopLine.split(",");
                    System.out.println(attributes);

                    //if ((attributes.length > 1) || (attributes.length < 5)) {
                    rgbValues.add(createRGB(attributes));
                    //}
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return rgbValues;
    }

    private static RGBValues createRGB(String[] value) {
        int id = Integer.parseInt(value[0]);
        String colorName = value[1];
        int redValue = Integer.parseInt(value[2]);
        int greenValue = Integer.parseInt(value[3]);
        int blueValue = Integer.parseInt(value[4]);
        String pieceName = value[5];

        return new RGBValues(id, colorName, redValue, greenValue, blueValue, pieceName);
    }

    public ArrayList<RGBValues> getRgbValues() {
        return rgbValues;
    }

    public void setRgbValues(ArrayList<RGBValues> rgbValues) {
        this.rgbValues = rgbValues;
    }

}
