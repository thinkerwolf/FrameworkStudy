package com.thinkerwolf.frameworkstudy.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;

public class RmiClient {

	public static void main(String[] args) {
		try {
			Registry registry = LocateRegistry.getRegistry("localhost", 1099);
			RemoteHelloWorld hello = (RemoteHelloWorld) registry.lookup("helloworld");
			String ret = hello.sayHello();
			System.out.println(ret);
			
			Map<String, Object> map = new HashMap<>();
			hello.opetate(map);
			System.out.println(map);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

	}

}
