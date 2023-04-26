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
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
@Repository
public class ColorConversionDAO {

	public void convert() {
		ImgManager manager = new ImgManager();
		ColorPaletteController controller = new ColorPaletteController();
		Color[][] palleteFromOriginal = manager.getColorArray(manager.getOriginalImg());
		Color[] palleteFromWebsite = controller.retrieveLocalPalette();

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

	public Color[] convertedImageColors() {
		ImgManager manager = new ImgManager();
		ColorPaletteController controller = new ColorPaletteController();
		Color[][] palleteFromOriginal = manager.getColorArray(manager.getOriginalImg());
		Color[] palleteFromWebsite = controller.retrieveLocalPalette();

		Color[][] palleteForClone = manager.makeNewColorArrayLocations(palleteFromWebsite, palleteFromOriginal);
		BufferedImage clone = ImgManager.clone(manager.getOriginalImg());
		BufferedImage newImg = manager.makeNewImg(clone, palleteForClone);
		
		return ImgManager.getColorPalletForUser(palleteForClone);
	}
	
	public Color[] originalImageColors() {
		ImgManager manager = new ImgManager();
		Color[][] palleteFromOriginal = manager.getColorArray(manager.getOriginalImg());
		return ImgManager.getColorPalletForUser(palleteFromOriginal);
	}
}