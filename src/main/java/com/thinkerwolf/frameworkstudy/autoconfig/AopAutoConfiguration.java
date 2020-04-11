package com.thinkerwolf.frameworkstudy.autoconfig;

import com.thinkerwolf.frameworkstudy.controller.Action;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class AopAutoConfiguration implements ApplicationContextAware, InitializingBean {

	ApplicationContext applicationContext;

	@Bean
	JdkRegexpMethodPointcut jdkRegexpMethodPointcut() {
		JdkRegexpMethodPointcut pointcut = new JdkRegexpMethodPointcut();
		pointcut.setPatterns(".*set*.");
		return pointcut;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Map<String, Object> map = applicationContext.getBeansWithAnnotation(Action.class);
		System.out.println(map);
	}
}
