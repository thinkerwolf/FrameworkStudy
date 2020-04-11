package com.thinkerwolf.frameworkstudy.controller;

import org.springframework.stereotype.Controller;

import java.lang.annotation.*;

@Controller
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target({ElementType.TYPE})
public @interface Action {
}
