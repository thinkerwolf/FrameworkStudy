package com.thinkerwolf.frameworkstudy.beans;

import java.beans.PropertyEditor;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;

public class BeanFactoryStudy {

	public enum BuildType {
		CODE, PROPERITIES, XML, ANNOTATION
	}

	public static void main(String[] args) {
		DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
		build(factory, BuildType.XML);
		
		
		Map<Class<?>, Class<? extends PropertyEditor>> map = new HashMap<>();
		map.put(Date.class, CustomDatePropertyEditor.class);
		CustomEditorConfigurer editorConfigurer = new CustomEditorConfigurer();
		editorConfigurer.setCustomEditors(map);
		editorConfigurer.postProcessBeanFactory(factory);
		
		//factory.addBeanPostProcessor(beanPostProcessor);
		
		NewsProvider newsProvider = (NewsProvider) factory.getBean("provider");
		newsProvider.send();
		
		INewsPersister newsPersister = (INewsPersister) factory.getBean("newsPersister");
		System.out.println(newsPersister.persistNews());
		System.out.println(newsPersister.persistNews());
		Runtime.getRuntime();
	}

	static void build(BeanDefinitionRegistry registry, BuildType type) {
		switch (type) {
		case CODE:
			buildByCode(registry);
			break;
		case PROPERITIES:
			buildByProperities(registry);
			break;
		case XML:
			buildByXML(registry);
			break;
		case ANNOTATION:
			
			break;
		default:
			buildByXML(registry);
			break;
		}
	}

	static void buildByCode(BeanDefinitionRegistry registry) {
		AbstractBeanDefinition providor = new RootBeanDefinition(NewsProvider.class);
		AbstractBeanDefinition foxSource = new RootBeanDefinition(FoxNewsSource.class);

		registry.registerBeanDefinition("provider", providor);
		registry.registerBeanDefinition("foxSource", foxSource);

		ConstructorArgumentValues cav = new ConstructorArgumentValues();
		cav.addIndexedArgumentValue(0, foxSource);
		providor.setConstructorArgumentValues(cav);

		MutablePropertyValues mpv = new MutablePropertyValues();
		mpv.addPropertyValue("source", foxSource);
		providor.setPropertyValues(mpv);
	}

	static void buildByProperities(BeanDefinitionRegistry registry) {
		BeanDefinitionReader reader = new PropertiesBeanDefinitionReader(registry);
		reader.loadBeanDefinitions(new ClassPathResource("com/example/demo/beans/fox.properities"));
	}

	static void buildByXML(BeanDefinitionRegistry registry) {
		BeanDefinitionReader reader = new XmlBeanDefinitionReader(registry);
		reader.loadBeanDefinitions(new ClassPathResource("com/example/demo/beans/fox.xml"));
	}
}
