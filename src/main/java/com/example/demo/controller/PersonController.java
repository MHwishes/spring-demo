package com.example.demo.controller;

import com.example.demo.dao.PersonRepository;
import com.example.demo.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class PersonController {
    @Autowired
    private  PersonRepository personRepository;

    public void setRepo(PersonRepository repo){
        personRepository =  repo;
    }

    @GetMapping(value = "/person")
    private List<Person> getAll() {
        System.out.println(personRepository.findAll());
        return personRepository.findAll();
    }

    @GetMapping(value = "/person/{id}")
    private Person getOne( @PathVariable("id") Integer id) {
        return  personRepository.findOne(id);
    }

    @PostMapping(value="/person")
    private Person save(@ModelAttribute Person  person){
//        Person person=new Person();
//
//        String name=(String) man.get("name");
//        Integer age=(Integer) man.get("age");
//
//        person.setName(name);
//        person.setAge(age);

//        System.out.print(person);
//        System.out.print("yyyyyyyyyyyyyyyyyyyyyyyyyyyy");
//        System.out.println(personRepository.save(person).getName());
        return  personRepository.save(person);


    }

    @PutMapping(value = "/person/{id}")
    private Person update(@PathVariable("id") Integer id,@ModelAttribute Person person){
//        Person person=new Person();
//
//        String name=(String) man.get("name");
//        Integer age=(Integer) man.get("age");
//
//        person.setName(name);
//        person.setAge(age);
//        person.setId(id);
        return  personRepository.save(person);

    }

    @DeleteMapping(value = "/person/{id}")
    private String delete(@PathVariable("id") Integer id){
        personRepository.delete(id);
        return "success";
    }
}