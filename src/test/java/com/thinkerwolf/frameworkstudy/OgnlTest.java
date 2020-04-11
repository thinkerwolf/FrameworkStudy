package com.thinkerwolf.frameworkstudy;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import ognl.Ognl;
import ognl.OgnlException;

public class OgnlTest {

	@Test
	public void test1() throws OgnlException {
		User user = new User("root", "123");
		Address address = new Address(1, "安徽");
		user.setAddress(address);
		System.out.println(Ognl.getValue("username$ != null", user));
		Object obj = Ognl.getValue("username$", user);
		System.out.println(obj);
		System.out.println(Ognl.getValue("address.getName()", user));
	}

	@Test
	public void test2() throws OgnlException {
		Map<String, Object> map = new HashMap<>();
		map.put("username$", "root");
		System.out.println(Ognl.getValue("username$", map));
	}
	
	class User {
		private String username$;
		private String password;
		private Address address;

		public User(String username, String password) {
			this.username$ = username;
			this.password = password;
		}

		public void setAddress(Address address) {
			this.address = address;
		}

		public String getUsername$() {
			return username$;
		}

		public void setUsername$(String username) {
			this.username$ = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public Address getAddress() {
			return address;
		}

	}

	class Address {

		private String name;

		private int id;

		public Address(int id, String name) {
			this.id = id;
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

	}

}
