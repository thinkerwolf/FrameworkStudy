package com.thinkerwolf.frameworkstudy.autoconfig;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.web.client.RestTemplate;

import com.thinkerwolf.frameworkstudy.conditional.ConditionalOnValidHelloProperty;
import com.thinkerwolf.frameworkstudy.service.HelloProperties;
import com.thinkerwolf.frameworkstudy.service.HelloService;
import com.thinkerwolf.frameworkstudy.service.HelloServiceImpl;
import com.thinkerwolf.frameworkstudy.validation.PersionValidator;
import com.thinkerwolf.frameworkstudy.validation.Person;

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
