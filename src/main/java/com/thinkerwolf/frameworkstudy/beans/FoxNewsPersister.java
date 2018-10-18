package com.thinkerwolf.frameworkstudy.beans;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class FoxNewsPersister implements INewsPersister, BeanFactoryAware {

	private BeanFactory beanFactory;
	private static final AtomicInteger atomicInt = new AtomicInteger(0);
	
	@Override
	public News persistNews() {
		int x = atomicInt.getAndIncrement();
		return (News) beanFactory.getBean("news", new Object[] { "汪峰" + x, "上头条" + x, "我要上头条" + x });
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

}
