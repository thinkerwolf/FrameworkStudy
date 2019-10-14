package com.thinkerwolf.frameworkstudy.controller;

import com.thinkerwolf.frameworkstudy.validation.Person;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
public class HelloController {

    private static final String URL = "localhost:8080";

    @Resource
    Person person;

    @RequestMapping("/hello")
    @ResponseBody
    public List<String> hello(String name) {
        List<String> list = new ArrayList<>();
        list.add(name);
        list.add("22");

        System.err.println(person);

        return list;
    }


}
