package com.thinkerwolf.frameworkstudy.beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectFactory;

public class MockNewsPersister implements INewsPersister, InitializingBean, BeanFactoryAware {

	private ObjectFactory<News> newsFactory;
	
	private BeanFactory beanFactory;
	
	@Override
	public News persistNews() {
		News news = newsFactory.getObject();
		return news;
	}

	public void setNewsFactory(ObjectFactory<News> newsFactory) {
		this.newsFactory = newsFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void afterPropertiesSet() throws Exception {
		newsFactory = (ObjectFactory<News>) beanFactory.getBean("newsFactory");
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}
	
	
}
