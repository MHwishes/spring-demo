package com.example.demo.controller;

import com.example.demo.dao.PersonRepository;
import com.example.demo.entity.Person;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonControllerTest {
    @Mock
    private PersonRepository personRepository;

    private MockMvc mockMvc;

    private RequestBuilder request = null;

    @Autowired
    ObjectMapper objectMapper;

    @Before
    public void setUp() {
        initMocks(this);
//        PersonController personController = new PersonController();
//        personController.setRepo(personRepository);
        mockMvc = MockMvcBuilders.standaloneSetup(new PersonController(personRepository)).build();


    }

    @Test
    public void getAll() throws Exception {

        Person user = new Person("yuuuuy", 8);
        List<Person> userList = new LinkedList<Person>();
        userList.add(user);

        when(personRepository.findAll()).thenReturn(userList);
        mockMvc.perform(get("/person"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("yuuuuy")))
                .andExpect(jsonPath("$[0].age", is(8)));
    }

    @Test
    public void getOne() throws Exception {

        Person user = new Person("yuuuuy", 8, 7);
        Mockito.when(personRepository.findOne(7)).thenReturn(user);

        mockMvc.perform(get("/person/7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(7)))
                .andExpect(jsonPath("$.name", is("yuuuuy")))
                .andExpect(jsonPath("$.age", is(8)));
    }


//    @Test
//    public void addOnePERSON() throws Exception {
//        Person person = new Person("mahong", 1);
//
//        Mockito.when(personRepository.save(Matchers.any(Person.class))).thenReturn(person);
//
//        mockMvc.perform(post("/person")
//                .contentType(MediaType.APPLICATION_JSON)
//                .param("name","mahong")
//                .param("age","1")
//                .accept(MediaType.APPLICATION_JSON)) //执行请求
//                .andExpect(jsonPath("$.name", is("mahong")))
//                .andExpect(jsonPath("$.age", is(1)));
//    }



    @Test
    public void addOnePERSON() throws Exception {
        Person person = new Person("mahong", 1);

        Mockito.when(personRepository.save(Matchers.any(Person.class))).thenReturn(person);

        this.mockMvc.perform(post("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isCreated());
    }


    @Test
    public void putOnePERSON() throws Exception {
        Person user = new Person("zhang", 1,1);
        when(personRepository.save(user)).thenReturn(user);

        mockMvc.perform(put("/person/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("name","zhangpei")
                .param("age","20")
                .param("id","1")
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andExpect(status().isOk());
    }

    @Test
    public void deletePerson() throws Exception{

        Mockito.when(personRepository.exists(1)).thenReturn(true);
        doNothing().when(personRepository).delete(1);

        mockMvc.perform(delete("/person/1"))
                .andExpect(status().isOk());


    }

}
