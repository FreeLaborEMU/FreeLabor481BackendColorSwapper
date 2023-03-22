package com.example.demo;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

@Repository
public class OriginalImageColorDAO {

	public ArrayList<String> getOriginalImageColor() {
		ArrayList<String> returnItem = new ArrayList<String>();
		returnItem.add("Please make me functional");
		return returnItem;
	}
}
