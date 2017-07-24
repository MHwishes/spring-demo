package com.example.demo.controller;

import com.example.demo.dao.PersonRepository;
import com.example.demo.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//TODO:注意返回值为httpcode
@RestController
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    public PersonController(PersonRepository personRepo) {
        this.personRepository = personRepo;
    }


    @GetMapping(value = "/person")
    public  List<Person> getAllPersons(){


        return personRepository.findAll();
    }


    @GetMapping(value = "/person/{id}")
    public Person getOne( @PathVariable("id") Integer id) {
        return  personRepository.findOne(id);
    }


//    @PostMapping(value="/person")
//    public Person save(Person  person){
//        return  personRepository.save(person);
//    }

    @PostMapping(value="/person")
    ResponseEntity<?> save(@RequestBody Person  person){
        return  new ResponseEntity<> (personRepository.save(person), HttpStatus.CREATED);
    }

    @PutMapping(value = "/person/{id}")
    //TODO:考虑id不存在的情况
    public Person update(@PathVariable("id") Integer id,@ModelAttribute Person person){

        return  personRepository.save(person);

    }

    @DeleteMapping(value = "/person/{id}")
    public String delete(@PathVariable("id") Integer id){

        //TODO:考虑id不存在的情况
        personRepository.delete(id);
        return "success";
    }

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String say(){
        return "Hello";
    }
}