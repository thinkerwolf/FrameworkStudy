package com.thinkerwolf.frameworkstudy.autoconfig;

import java.util.Properties;

import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.jta.JtaTransactionManager;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;

@Configuration
public class TransactionConfiguration {

	@Bean("ds1")
	public AtomikosDataSourceBean getDataSourceFirst() {
		AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
		atomikosDataSourceBean.setUniqueResourceName("db1");
		atomikosDataSourceBean.setXaDataSourceClassName("com.mysql.cj.jdbc.MysqlXADataSource");
        atomikosDataSourceBean.setMinPoolSize(2);
        atomikosDataSourceBean.setMaxPoolSize(4);
        Properties properties = new Properties();
		properties.put("URL",
				"jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC");
		properties.put("user", "root");
        properties.put("password", "1234");
        atomikosDataSourceBean.setXaProperties(properties);
		return atomikosDataSourceBean;
	}

	@Bean("ds2")
	public AtomikosDataSourceBean getDataSourceSecond() {
		AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
		atomikosDataSourceBean.setUniqueResourceName("db2");
		atomikosDataSourceBean.setXaDataSourceClassName("com.mysql.cj.jdbc.MysqlXADataSource");
        atomikosDataSourceBean.setMinPoolSize(2);
        atomikosDataSourceBean.setMaxPoolSize(4);
        Properties properties = new Properties();
		properties.put("URL",
				"jdbc:mysql://localhost:3306/test1?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC");
		properties.put("user", "root");
        properties.put("password", "1234");
        atomikosDataSourceBean.setXaProperties(properties);
		return atomikosDataSourceBean;
	}

	@Bean("userTm")
	public TransactionManager getUserTransactionManager() {
		UserTransactionManager userTransactionManager = new UserTransactionManager();
		return userTransactionManager;
	}

	@Bean("userT")
	public UserTransaction getUserTransaction() throws SystemException {
		UserTransactionImp userTransaction = new UserTransactionImp();
		userTransaction.setTransactionTimeout(300);
		return userTransaction;
	}

	@Bean("jtaTm")
	public JtaTransactionManager getSpringTransactionManager(
			@Qualifier("userTm") TransactionManager transactionManager) {
		JtaTransactionManager jtaTransactionManager = new JtaTransactionManager();
		jtaTransactionManager.setTransactionManager(transactionManager);
		return jtaTransactionManager;
	}

}
