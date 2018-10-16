package com.example.demo.autoconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.event.SendMailService;
import com.example.demo.event.SendMainListener;

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
