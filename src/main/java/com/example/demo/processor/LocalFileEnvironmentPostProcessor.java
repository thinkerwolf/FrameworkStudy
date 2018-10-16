package com.example.demo.processor;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.FileSystemResource;

/**
 * 在启动App前将用户自定义的环境加载到Spring的Environment中
 * @author wukai
 *
 */
public class LocalFileEnvironmentPostProcessor implements EnvironmentPostProcessor {

	public static final String LOCATIOIN = ".dubbo/settings";
	private final PropertiesPropertySourceLoader loader = new PropertiesPropertySourceLoader();

	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		File file = new File(System.getProperty("user.home"), LOCATIOIN);
		if (file.exists()) {
			MutablePropertySources propertySources = environment.getPropertySources();
			List<PropertySource<?>> propertySourceList = loadPropertySource(file);
			for (PropertySource<?> ps : propertySourceList) {
				if (!propertySources.contains(ps.getName())) {
					propertySources.addLast(ps);
				}
			}
		}
	}

	private List<PropertySource<?>> loadPropertySource(File file) {
		FileSystemResource resource = new FileSystemResource(file);
		try {
			return loader.load("hello-properties", resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
