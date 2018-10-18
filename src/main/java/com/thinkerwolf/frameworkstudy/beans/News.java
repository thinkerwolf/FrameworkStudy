package com.thinkerwolf.frameworkstudy.beans;

public class News {

	private String title;

	private String subTitle;

	private String content;

	public News(String title, String subTitle, String content) {
		this.title = title;
		this.subTitle = subTitle;
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("\r\n===========");
		sb.append("\r\n");
		sb.append(title);
		sb.append("\r\n\t" + subTitle);
		sb.append("\r\n" + content);
		return sb.toString();
	}
	
}
