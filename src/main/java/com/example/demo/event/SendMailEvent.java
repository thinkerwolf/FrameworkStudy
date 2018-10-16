package com.example.demo.event;

import org.springframework.context.ApplicationEvent;

public class SendMailEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String address;
	
	public SendMailEvent(Object source, String address) {
		super(source);
		this.address = address;
	}

	public String getAddress() {
		return address;
	}
	
	
	
	
}
