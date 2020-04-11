package com.thinkerwolf.frameworkstudy.autoconfig;


import org.directwebremoting.servlet.DwrServlet;
import org.directwebremoting.spring.DwrSpringServlet;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Servlet;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MyWebConfiguration {

    //    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> configWebServer() {

        return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
            @Override
            public void customize(ConfigurableWebServerFactory factory) {

            }
        };

    }


    @Bean
    public ServletRegistrationBean dwrServlet() {
        DwrServlet servlet = new DwrServlet();
        ServletRegistrationBean<Servlet> bean = new ServletRegistrationBean<>(servlet, "/dwr/*");
        Map<String, String> initParams = new HashMap<>();
        initParams.put("debug", "true");
        initParams.put("classes", "com.thinkerwolf.frameworkstudy.dwr.RemoteFunctions," +
                "com.thinkerwolf.frameworkstudy.dwr.ChatManager,"+
                "com.thinkerwolf.frameworkstudy.dwr.User");
        initParams.put("activeReverseAjaxEnabled", "true");
        bean.setInitParameters(initParams);
        return bean;
    }

}
