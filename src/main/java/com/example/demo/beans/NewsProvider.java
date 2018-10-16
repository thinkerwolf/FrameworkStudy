package com.example.demo.beans;

public class NewsProvider {

	private INewsSource source;

	public NewsProvider() {
	}

	public NewsProvider(INewsSource source) {
		this.source = source;
	}

	public INewsSource getSource() {
		return source;
	}

	public void setSource(INewsSource source) {
		this.source = source;
	}

	public void send() {
		System.out.println(source.obtain());
	}

}
