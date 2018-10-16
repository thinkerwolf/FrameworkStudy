package com.example.demo.autoconfig;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.web.client.RestTemplate;

import com.example.demo.conditional.ConditionalOnValidHelloProperty;
import com.example.demo.service.HelloProperties;
import com.example.demo.service.HelloService;
import com.example.demo.service.HelloServiceImpl;
import com.example.demo.validation.PersionValidator;
import com.example.demo.validation.Person;

@Configuration
@EnableConfigurationProperties(HelloProperties.class)
//@ConditionalOnClass(HelloService.class)
public class HelloAutoConfiguration {
	
	@Bean
	@ConditionalOnValidHelloProperty
	public HelloService helloService(HelloProperties helloProperties) {
		return new HelloServiceImpl(helloProperties.getPrefix(), helloProperties.getSuffix());
	}
	
	@Bean
	Validator getValidator() {
		return new PersionValidator();
	}
	
	@Bean
	Person getPerson() {
		Person p = new Person();
		p.setAge(120);
		p.setName("Tom");
		return p;
	}
	
	
}
