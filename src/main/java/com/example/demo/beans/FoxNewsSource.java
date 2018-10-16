package com.example.demo.beans;

public class FoxNewsSource implements INewsSource {

	@Override
	public String obtain() {
		return "Fox news";
	}

}
