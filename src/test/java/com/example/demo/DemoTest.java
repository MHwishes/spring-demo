package com.example.demo;

import com.example.demo.controller.HelloController;
import com.example.demo.dao.PersonRepository;
import com.example.demo.entity.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.concurrent.ThreadLocalRandom;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional
public class DemoTest {
    private MockMvc mockMvc;


    @Autowired
    private WebApplicationContext context;
    @Autowired
    PersonRepository personRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
//        mockMvc = MockMvcBuilders.standaloneSetup(new PersonController()).build();
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        Person person = new Person("mahong", 18);
        personRepository.save(person);
    }


    @Test
    public void getAllPerson() throws Exception {
        mockMvc.perform(get("/person"))
//                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getOnePerson() throws Exception {
        Person person = personRepository.findAll().get(0);
        mockMvc.perform(get("/person/" + person.getId()))
                .andExpect(status().isOk());

    }

    @Test
    public void getOnePersonIfIdNotFound() throws Exception {
        Integer id = ThreadLocalRandom.current().nextInt();
        if (personRepository.exists(id)) {
            personRepository.delete(id);
        }

        mockMvc.perform(get("/person/" + id))
                .andExpect(status().isNotFound());

    }

    @Test

    public void addOnePERSON() throws Exception {
        Person person = new Person("huhu", 1);
        this.mockMvc.perform(post("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isCreated());
    }


    @Test
    public void updatePersonIdExist() throws Exception {
        Person person = personRepository.findAll().get(0);
        Person newPerson = new Person("hu", 5, person.getId());

        mockMvc.perform(put("/person/" + person.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newPerson)))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.id", is(person.getId())))
                .andExpect(jsonPath("$.name", is("hu")))
                .andExpect(jsonPath("$.age", is(5)));
    }

    @Test
    public void updatePersonIdNotExist() throws Exception {
        Integer id = ThreadLocalRandom.current().nextInt();
        Person newPerson = new Person("hu", 5, id);
        if (personRepository.exists(id)) {
            personRepository.delete(id);
        }
        mockMvc.perform(put("/person/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newPerson)))
                .andExpect(status().isNotFound());

    }

    @Test
    public void deletedIfIdExist() throws Exception {

        Person person = personRepository.findAll().get(0);
        mockMvc.perform(delete("/person/" + person.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deletedIfIdNotExit() throws Exception {
        Integer id = ThreadLocalRandom.current().nextInt();
        if (personRepository.exists(id)) {
            personRepository.delete(id);
        }
        mockMvc.perform(delete("/person/1"))
                .andExpect(status().isNotFound());

    }
}
