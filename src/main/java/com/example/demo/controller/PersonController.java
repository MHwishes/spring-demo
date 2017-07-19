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

    @GetMapping(value = "/person")
    private List<Person> personList() {
        return personRepository.findAll();
    }

    @GetMapping(value = "/person/{id}")
    private Person getOnePerson( @PathVariable("id") Integer id) {
        return  personRepository.findOne(id);
    }

    @PostMapping(value="/person")
    private Person save(@RequestBody Map man){
        Person person=new Person();

        String name=(String) man.get("name");
        Integer age=(Integer) man.get("age");

        person.setName(name);
        person.setAge(age);
        return personRepository.save(person);
    }

    @PutMapping(value = "/person/{id}")
    private Person update(@PathVariable("id") Integer id,@RequestBody Map man){
        Person person=new Person();

        String name=(String) man.get("name");
        Integer age=(Integer) man.get("age");

        person.setName(name);
        person.setAge(age);
        person.setId(id);
        return  personRepository.save(person);

    }

    @DeleteMapping(value = "/person/{id}")
    private void delete(@PathVariable("id") Integer id){
        personRepository.delete(id);
    }
}