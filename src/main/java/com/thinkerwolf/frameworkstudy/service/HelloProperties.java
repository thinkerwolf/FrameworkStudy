package com.thinkerwolf.frameworkstudy.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("hello")
public class HelloProperties {
	/**
	 * prefix bla bla bla
	 */
	private String prefix;
	/**
	 * suffix bla bla bla
	 */
	private String suffix = "!";

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

}
