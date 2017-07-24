package com.example.demo;

import com.example.demo.controller.HelloController;
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

    public void addOnePERSON() throws Exception {
        Person person = new Person("huhu", 1);
        this.mockMvc.perform(post("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isCreated());
    }


    @Test

    public void putOnePERSON() throws Exception {

        mockMvc.perform(put("/person/9")
                .contentType(MediaType.APPLICATION_JSON)
                .param("name", "mahong")
                .param("age", "20")
                .param("id", "1")
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andExpect(jsonPath("$.name", is("mahong")))
                .andExpect(jsonPath("$.age", is(20)));
    }

    @Test
    @Transactional
    public void deletePerson() throws Exception {

        Person person = personRepository.findAll().get(0);
        mockMvc.perform(delete("/person/" + person.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void fisttest() {
        HelloController helloController = new HelloController();
        assertEquals("Hello World", helloController.index());
    }

    @Test
    public void sayHello() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Hello")));
    }


    @Test
    public void contextLoads() {
        assertEquals(6, 3 + 3);
    }
}
