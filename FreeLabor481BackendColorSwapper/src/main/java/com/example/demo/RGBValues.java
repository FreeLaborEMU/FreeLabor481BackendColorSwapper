package com.example.demo;

public class RGBValues {
	private int id;
	private String colorName;
	private int redValue;
	private int greenValue;
	private int blueValue;
	private String pieceName;
	
	RGBValues(){
		
	}
	
	RGBValues(int id, String colorName, int redValue, int greenValue, int blueValue, String pieceName) {
		this.setId(id);
		this.setColorName(colorName);
		this.setRedValue(redValue);
		this.setGreenValue(greenValue);
		this.setBlueValue(blueValue);
		this.setPieceName(pieceName);
	}

	public int getRedValue() {
		return redValue;
	}

	public void setRedValue(int redValue) {
		this.redValue = redValue;
	}

	public int getGreenValue() {
		return greenValue;
	}

	public void setGreenValue(int greenValue) {
		this.greenValue = greenValue;
	}

	public int getBlueValue() {
		return blueValue;
	}

	public void setBlueValue(int blueValue) {
		this.blueValue = blueValue;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public String getPieceName() {
		return pieceName;
	}

	public void setPieceName(String pieceName) {
		this.pieceName = pieceName;
	}
	
	
}
