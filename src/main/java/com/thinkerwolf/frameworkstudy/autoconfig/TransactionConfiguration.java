package com.thinkerwolf.frameworkstudy.autoconfig;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.thinkerwolf.frameworkstudy.autoconfig.properties.JdbcProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.inject.Inject;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import java.util.Properties;

@Configuration
public class TransactionConfiguration {

    @Inject
    JdbcProps props;

    @Bean("ds1")
    public AtomikosDataSourceBean getDataSourceFirst(@Value("${jdbc.xaDataSource}") String xaDataSourceClassName) {
        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setUniqueResourceName("db1");
        atomikosDataSourceBean.setXaDataSourceClassName(xaDataSourceClassName);
        atomikosDataSourceBean.setMinPoolSize(2);
        atomikosDataSourceBean.setMaxPoolSize(4);
        Properties properties = new Properties();
        properties.put("URL",
                props.getUrl());
        properties.put("user", props.getUsername());
        properties.put("password", props.getPassword());
        atomikosDataSourceBean.setXaProperties(properties);
        return atomikosDataSourceBean;
    }

    @Bean("ds2")
    public AtomikosDataSourceBean getDataSourceSecond() {
        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setUniqueResourceName("db2");
        atomikosDataSourceBean.setXaDataSourceClassName(props.getXaDataSource());
        atomikosDataSourceBean.setMinPoolSize(2);
        atomikosDataSourceBean.setMaxPoolSize(4);
        Properties properties = new Properties();
        properties.put("URL", props.getUrl());
        properties.put("user", props.getUsername());
        properties.put("password", props.getPassword());
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
