package com.thinkerwolf.frameworkstudy.controller;

import com.thinkerwolf.frameworkstudy.validation.Person;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Api(value = "hello")
@Action
public class HelloController {

    private static final String URL = "localhost:8080";

    @Resource
    Person person;

    @RequestMapping("/hello")
    @ResponseBody
    @ApiOperation(value = "说hello", httpMethod = "GET", notes = "Say hello", response = List.class)
    public List<String> hello(@ApiParam(required = true, value = "wukai", name="name") String name) {
        List<String> list = new ArrayList<>();
        list.add(name);
        list.add("22");

        System.err.println(person);

        return list;
    }


}
