package com.thinkerwolf.frameworkstudy.transaction;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class SpringTransactionStudy {

    public static void main(String[] args) throws Exception{
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) context.getBean("transactionManager");
        TransactionDefinition definition = new DefaultTransactionDefinition();
        DefaultTransactionStatus ts = (DefaultTransactionStatus) transactionManager.getTransaction(definition);
        ConnectionHolder conHolder = (ConnectionHolder) TransactionSynchronizationManager.getResource(transactionManager.getDataSource());
        Connection connection = conHolder.getConnection();
        PreparedStatement ps = connection.prepareStatement("INSERT INTO blog VALUES (4, 'Amazone', 'Kindle')");
        ps.executeUpdate();
        transactionManager.commit(ts);

    }
}
