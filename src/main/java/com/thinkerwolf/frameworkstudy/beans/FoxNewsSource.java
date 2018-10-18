package com.thinkerwolf.frameworkstudy.beans;

public class FoxNewsSource implements INewsSource {

	@Override
	public String obtain() {
		return "Fox news";
	}

}
