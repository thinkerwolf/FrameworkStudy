package com.thinkerwolf.frameworkstudy.jndi;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;

public class JndiStudy {

	public static void main(String[] args) throws NamingException {
		//InitialContext ctx = new InitialContext();
		InitialContext ctx = new InitialLdapContext();
		
		ctx.bind("www.baidu.com", "172.45.32.54");
		ctx.lookup("www.baidu.com");
	}

}
