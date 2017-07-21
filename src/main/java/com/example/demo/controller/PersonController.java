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
    public List<Person> getAll() {

        return personRepository.findAll();
    }

    @GetMapping(value = "/person/{id}")
    public Person getOne( @PathVariable("id") Integer id) {
        return  personRepository.findOne(id);
    }

    @PostMapping(value="/person")
    public Person save(@ModelAttribute Person  person){
        return  personRepository.save(person);
    }

    @PutMapping(value = "/person/{id}")
    public Person update(@PathVariable("id") Integer id,@ModelAttribute Person person){

        return  personRepository.save(person);

    }

    @DeleteMapping(value = "/person/{id}")
    public String delete(@PathVariable("id") Integer id){
        personRepository.delete(id);
        return "success";
    }
}