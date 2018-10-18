package com.thinkerwolf.frameworkstudy.beans;

public class NewsPersisterFactory {

	public static INewsPersister getInstance(String name) {
		switch (name) {
		case "fox":
			return new FoxNewsPersister();
		case "mock":
			return new MockNewsPersister();
		default:
			return null;
		}
	}

}
