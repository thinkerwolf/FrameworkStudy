package com.thinkerwolf.frameworkstudy.rmi;

import java.rmi.RemoteException;
import java.util.Map;

public class RemoteHelloWorldImpl implements RemoteHelloWorld {

	@Override
	public String sayHello() throws RemoteException {
		return "Hello world";
	}

	@Override
	public void opetate(Map<String, Object> map) throws RemoteException {
		map.put("serverAdd", "hahaha");
	}

}
