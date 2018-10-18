package com.example.demo;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import com.thinkerwolf.frameworkstudy.autoconfig.MessageSourceAutoConfiguration;
import com.thinkerwolf.frameworkstudy.autoconfig.SendMailEventAutoConfig;
import com.thinkerwolf.frameworkstudy.event.SendMailService;
import com.thinkerwolf.frameworkstudy.service.HelloProperties;
import com.thinkerwolf.frameworkstudy.service.HelloService;
import com.thinkerwolf.frameworkstudy.service.HelloServiceImpl;

import io.netty.channel.Channel;

@SuppressWarnings("unused")
@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(UserAutoConfiguration.class, MessageSourceAutoConfiguration.class,
					// RpcAutoConfiguration.class,
					SendMailEventAutoConfig.class));

	@Test
	public void contextLoads() {
		contextRunner.run(context -> {
			String message1 = context.getMessage("message", null, "Default", java.util.Locale.CHINA);
			String message2 = context.getMessage("argument.required", new Object[] { "userDao" }, "Default",
					java.util.Locale.CHINA);
			System.out.println(message1);
			System.out.println(message2);
		});
	}

	@Test
	public void sayHello() {
		contextRunner.run(context -> {
			HelloService hs = context.getBean(HelloService.class);
			hs.sayHello("sss");
		});
	}

	@Test
	public void sendMailListener() {
		contextRunner.run(context -> {
			SendMailService sms = context.getBean(SendMailService.class);
			sms.sendMail("wukai@gmail.com");
		});
	}

	/*
	 * @Test public void resource() { contextRunner.run(context -> { Resource
	 * resource = context.getResource(
	 * "https://bkssl.bdimg.com/static/wiki-lemma/pkg/wiki-lemma_ade1573.js");
	 * URL url = resource.getURL(); InputStream is =
	 * url.openConnection().getInputStream(); List<String> strList =
	 * IOUtils.readLines(is, "UTF-8"); for (String str : strList) {
	 * System.out.println(str); } });
	 * 
	 * }
	 */

	@Test
	public void rpc() {
		contextRunner.run(context -> {
		
		});
	}

	@Configuration
	@EnableConfigurationProperties(HelloProperties.class)
	static class UserAutoConfiguration {

		@Bean
		public HelloService helloService() {
			return new HelloServiceImpl("Hello", "jude ***");
		}
	}

}
