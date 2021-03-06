package com.thinkerwolf.frameworkstudy.mybatis.domain;

public class Blog extends BlogKey {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column blog.title
	 * @mbg.generated  Tue Oct 16 15:53:40 CST 2018
	 */
	private String title;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column blog.content
	 * @mbg.generated  Tue Oct 16 15:53:40 CST 2018
	 */
	private String content;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column blog.title
	 * @return  the value of blog.title
	 * @mbg.generated  Tue Oct 16 15:53:40 CST 2018
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column blog.title
	 * @param title  the value for blog.title
	 * @mbg.generated  Tue Oct 16 15:53:40 CST 2018
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column blog.content
	 * @return  the value of blog.content
	 * @mbg.generated  Tue Oct 16 15:53:40 CST 2018
	 */
	public String getContent() {
		return content;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column blog.content
	 * @param content  the value for blog.content
	 * @mbg.generated  Tue Oct 16 15:53:40 CST 2018
	 */
	public void setContent(String content) {
		this.content = content;
	}
}