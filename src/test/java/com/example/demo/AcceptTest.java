package com.example.demo;

import com.example.demo.dao.PersonRepository;
import com.example.demo.entity.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional
public class AcceptTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;
    @Autowired
    PersonRepository personRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        personRepository.deleteAll();
        Person person1 = new Person("mahong1", 18);
        Person person2 = new Person("mahong2", 19);
        personRepository.save(person1);
        personRepository.save(person2);
    }


    @Test
    public void acception() throws Exception {
        mockMvc.perform(get("/person"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("mahong1")))
                .andExpect(jsonPath("$[0].age", is(18)))
                .andExpect(jsonPath("$[1].name", is("mahong2")))
                .andExpect(jsonPath("$[1].age", is(19)));

        Person person = personRepository.findAll().get(0);
        mockMvc.perform(get("/person/" + person.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("mahong1")))
                .andExpect(jsonPath("$.age", is(18)));


        mockMvc.perform(post("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Person("huhu", 1))))
                .andExpect(status().isCreated());


        mockMvc.perform(put("/person/" + person.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Person("mahong", 20, person.getId()))))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.name", is("mahong")))
                .andExpect(jsonPath("$.age", is(20)));


        mockMvc.perform(delete("/person/" + person.getId()))
                .andExpect(status().isNoContent());
    }


}
