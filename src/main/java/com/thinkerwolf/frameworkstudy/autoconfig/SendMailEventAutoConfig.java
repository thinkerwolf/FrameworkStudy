package com.thinkerwolf.frameworkstudy.autoconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.thinkerwolf.frameworkstudy.event.SendMailService;
import com.thinkerwolf.frameworkstudy.event.SendMainListener;

@Configuration
public class SendMailEventAutoConfig {

	@Bean
	SendMailService sendMailService() {
		return new SendMailService();
	}

	@Bean
	SendMainListener sendMainListener() {
		return new SendMainListener();
	}

}
