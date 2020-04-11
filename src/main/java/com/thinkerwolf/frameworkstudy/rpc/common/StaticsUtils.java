package com.thinkerwolf.frameworkstudy.rpc.common;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StaticsUtils {
	
	AtomicInteger ai = new AtomicInteger();
	
	private static final Log logger = LogFactory.getLog(StaticsUtils.class);
	
	
	public void addNum() {
		int num = ai.incrementAndGet();
		//System.out.println("Statics num#" + num);
		if (logger.isDebugEnabled()) {
			logger.debug("static num # " + num);
		}
	}
	
	
	
}
