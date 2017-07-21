package com.example.demo;

import com.example.demo.controller.HelloController;
import com.example.demo.controller.PersonController;
import com.example.demo.dao.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class IT {

    @Mock
    private PersonRepository personRepository;
    private MockMvc mockMvc;


    @Before
    public void setUp() {
        initMocks(this);
        PersonController personController = new PersonController();
        personController.setRepo(personRepository);
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
    }


    @Test
    public void getAll() throws Exception {

        mockMvc.perform(get("/person")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getOne() throws Exception {
        mockMvc.perform(get("/person/7"))
                .andExpect(status().isOk());

    }


    @Test
    public void addOnePERSON() throws Exception {

        mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .param("name","zhangpei")
                .param("age","20")
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andExpect(status().isOk());
    }


    @Test
    public void putOnePERSON() throws Exception {

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

        mockMvc.perform(delete("/person/1"))
                .andExpect(status().isOk());


    }


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
