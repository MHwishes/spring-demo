package com.example.demo;

import com.example.demo.controller.HelloController;
import com.example.demo.dao.PersonRepository;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UnitTests {

//    @Autowired
//    private PersonRepository personRepository;
//
//    @Test
//    public void findAll(){
//        assertNotNull(personRepository.findAll());
//    }

    @Test
    public void fisttest(){
        HelloController helloController=new HelloController();
        assertEquals("Hello World",helloController.index());
    }

    @Test
    public void contextLoads() {
        assertEquals(6, 3+3);
    }
}
