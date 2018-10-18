package com.thinkerwolf.frameworkstudy.autoconfig;

import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AopAutoConfiguration {

	@Bean
	JdkRegexpMethodPointcut jdkRegexpMethodPointcut() {
		JdkRegexpMethodPointcut pointcut = new JdkRegexpMethodPointcut();
		pointcut.setPatterns(".*set*.");
		return pointcut;
	}

}
