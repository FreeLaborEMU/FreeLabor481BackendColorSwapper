package com.example.demo;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
@Repository
public class ColorConversionDAO {

	public void convert() {
		ImgManager manager = new ImgManager();
		Color[][] palleteFromOriginal = manager.getColorArray(manager.getOriginalImg());
		Color[] palleteFromWebsite = new Color[16];
		palleteFromWebsite[0] = new Color((float) 0.0, (float) 0.0, (float) 1.0);
		palleteFromWebsite[1] = new Color((float) 0.0, (float) 1.0, (float) 0.0);
		palleteFromWebsite[2] = new Color((float) 1.0, (float) 0.0, (float) 0.0);
		palleteFromWebsite[3] = new Color((float) 0.0, (float) 0.0, (float) 0.0);
		palleteFromWebsite[4] = new Color((float) 0.0, (float) 0.5, (float) 0.0);
		palleteFromWebsite[5] = new Color((float) 0.5, (float) 0.0, (float) 0.0);
		palleteFromWebsite[6] = new Color((float) 0.5, (float) 0.5, (float) 0.5);
		palleteFromWebsite[7] = new Color((float) 0.0, (float) 0.0, (float) 0.5);
		palleteFromWebsite[8] = new Color((float) 0.0, (float) 0.5, (float) 0.0);
		palleteFromWebsite[9] = new Color((float) 1.0, (float) 1.0, (float) 1.0);
		palleteFromWebsite[10] = new Color((float) 0.0, (float) 0.6, (float) 0.0);
		palleteFromWebsite[11] = new Color((float) 0.0, (float) 0.7, (float) 0.0);
		palleteFromWebsite[12] = new Color((float) 0.0, (float) 0.8, (float) 0.0);
		palleteFromWebsite[13] = new Color((float) 0.6, (float) 0.6, (float) 0.0);
		palleteFromWebsite[14] = new Color((float) 0.7, (float) 0.7, (float) 0.0);
		palleteFromWebsite[15] = new Color((float) 0.8, (float) 0.8, (float) 0.0);
		Color[][] palleteForClone = manager.makeNewColorArrayLocations(palleteFromWebsite, palleteFromOriginal);
		BufferedImage clone = ImgManager.clone(manager.getOriginalImg());
		BufferedImage newImg = manager.makeNewImg(clone, palleteForClone);

		displayImage(newImg);

	}

	public static void displayImage(final BufferedImage image) {
		Resource resource = new ClassPathResource("images/");
		String filename = "convertedImage.jpg";
		BufferedImage bi = image;
		try {
			File outputFile = new File(resource.getFile() + "/" + filename);
			ImageIO.write(bi, "jpg", outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}