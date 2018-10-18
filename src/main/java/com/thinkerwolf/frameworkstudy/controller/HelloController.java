package com.thinkerwolf.frameworkstudy.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {
	
	private static final String URL = "localhost:8080";
	
	@RequestMapping("/hello")
	@ResponseBody
	public List<String> hello(String name) {
		List<String> list = new ArrayList<>();
		list.add(name);
		list.add("22");
		return list;
	}
	
	
	
}
