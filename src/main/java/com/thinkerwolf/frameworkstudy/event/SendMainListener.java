package com.thinkerwolf.frameworkstudy.event;

import org.springframework.context.ApplicationListener;

/**
 * 
 */
public class SendMainListener implements ApplicationListener<SendMailEvent> {

	@Override
	public void onApplicationEvent(SendMailEvent event) {
		System.out.printf("listener address %s \n", event.getAddress());
	}

}
