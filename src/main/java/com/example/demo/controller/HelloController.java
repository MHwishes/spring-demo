package com.example.demo.controller;

import com.example.demo.entity.PersonProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Value("${content}")
    private String name;

//    @Autowired
//    private PersonProperties personProperties;

//    @RequestMapping(value = "/hello",method = RequestMethod.GET)
//    public String say(){
//        return name;
//    }

    @RequestMapping("/helloo")
    public String index() {
        return "Hello World";
    }

//    @RequestMapping(value = "/pppppp",method = RequestMethod.GET)
//    public String say2(){
//
//
//        return personProperties.getName()+personProperties.getAge();
//    }
}
