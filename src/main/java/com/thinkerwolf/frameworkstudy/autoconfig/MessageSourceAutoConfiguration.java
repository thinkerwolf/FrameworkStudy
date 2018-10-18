package com.thinkerwolf.frameworkstudy.autoconfig;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MessageSourceAutoConfiguration {
	
	@Bean("messageSource")
	public MessageSource messageSource() {
		ResourceBundleMessageSource rbms = new ResourceBundleMessageSource();
		rbms.setBasenames("format", "windows", "arguments");
		return rbms;
	}
	
}
