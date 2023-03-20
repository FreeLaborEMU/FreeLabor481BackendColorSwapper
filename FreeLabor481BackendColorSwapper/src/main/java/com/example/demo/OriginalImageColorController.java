package com.example.demo;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/originalImageColor")
public class OriginalImageColorController {
	@Autowired
	private OriginalImageColorDAO originalImageColorDAO;
	
//	@GetMapping(path = "/")
//	public ArrayList<String> getOriginalImageColor() {
//		return originalImageColorDAO.getOriginalImageColor();
//	}
	@GetMapping(path = "/")
	public String getOriginalImageColor() {
		return "Hello";
	}
}
