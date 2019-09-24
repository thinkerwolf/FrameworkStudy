package com.thinkerwolf.frameworkstudy.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface RemoteHelloWorld extends Remote {
	String sayHello() throws RemoteException;
	
	void opetate(Map<String, Object> map) throws RemoteException;
	
}
