package com.thinkerwolf.frameworkstudy.rmi;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RmiServer {
	public static void main(String[] args) throws RemoteException {
		RemoteHelloWorld rhw = new RemoteHelloWorldImpl();
		Remote stub = UnicastRemoteObject.exportObject(rhw, 9999);
		LocateRegistry.createRegistry(1099);
		Registry registry = LocateRegistry.getRegistry();
		try {
			registry.bind("helloworld", stub);
		} catch (AlreadyBoundException e) {
			e.printStackTrace();
		}
		
	}
}
