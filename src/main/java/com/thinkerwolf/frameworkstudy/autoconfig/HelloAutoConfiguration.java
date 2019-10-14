package com.thinkerwolf.frameworkstudy.autoconfig;

import com.thinkerwolf.frameworkstudy.autoconfig.properties.HelloProperties;
import com.thinkerwolf.frameworkstudy.conditional.ConditionalOnValidHelloProperty;
import com.thinkerwolf.frameworkstudy.service.HelloService;
import com.thinkerwolf.frameworkstudy.service.HelloServiceImpl;
import com.thinkerwolf.frameworkstudy.validation.PersionValidator;
import com.thinkerwolf.frameworkstudy.validation.Person;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.Validator;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
@EnableConfigurationProperties(HelloProperties.class)
//@ConditionalOnClass(HelloService.class)
public class HelloAutoConfiguration {

    @Bean
    @ConditionalOnValidHelloProperty
    public HelloService helloService(HelloProperties helloProperties) {
        return new HelloServiceImpl(helloProperties.getPrefix(), helloProperties.getSuffix());
    }

    @Bean
    Validator getValidator() {
        return new PersionValidator();
    }

    @Bean("person")
    @Scope("prototype")
    Person getPerson() {
        Person p = new Person();
        p.setAge(120);
        p.setName("Tom");
        return p;
    }

    @PostConstruct
    public void init() {
        System.err.println("hello init");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("hello destroy");
    }

}
