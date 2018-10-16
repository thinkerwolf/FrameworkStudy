package com.example.demo.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
/**
 * 
 * @author wukai
 *
 */
public class SendMailService implements ApplicationEventPublisherAware {

	private ApplicationEventPublisher publisher;

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.publisher = applicationEventPublisher;
	}

	public void sendMail(String address) {
		SendMailEvent event = new SendMailEvent(this, address);
		publisher.publishEvent(event);
	}

}
