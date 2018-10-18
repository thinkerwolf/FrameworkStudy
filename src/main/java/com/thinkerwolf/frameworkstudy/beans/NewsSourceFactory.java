package com.thinkerwolf.frameworkstudy.beans;

public class NewsSourceFactory {

	public static INewsSource getInstance(String name) {
		switch (name) {
		case "fox":
			return new FoxNewsSource();
		default:
			return null;
		}
	}
	
	public INewsSource getNewInstance(String name) {
		switch (name) {
		case "fox":
			return new FoxNewsSource();
		default:
			return null;
		}
	}
	
	
}	
