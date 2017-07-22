package com.example.demo;

import com.example.demo.controller.HelloController;
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
public class DemoTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setUp() throws Exception{
//        mockMvc = MockMvcBuilders.standaloneSetup(new PersonController()).build();
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


    @Test
    public void getAllPerson() throws Exception {
        mockMvc.perform(get("/person"))
//                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getOnePerson() throws Exception {
        mockMvc.perform(get("/person/6"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("mahong")))
                .andExpect(jsonPath("$.age", is(45)));
    }


    @Test
    @Transactional
    public void addOnePERSON() throws Exception {

        mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .param("name","mahong6666")
                .param("age","20")
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andExpect(status().isOk());

    }


    @Test
    @Transactional
    public void putOnePERSON() throws Exception {

        mockMvc.perform(put("/person/9")
                .contentType(MediaType.APPLICATION_JSON)
                .param("name","mahong beautiful")
                .param("age","20")
                .param("id","1")
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void deletePerson() throws Exception{

        mockMvc.perform(delete("/person/7"))
                .andExpect(status().isOk());


    }

    @Test
    public void fisttest(){
        HelloController helloController=new HelloController();
        assertEquals("Hello World",helloController.index());
    }

    @Test
    public void sayHello() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Hello")));
    }


    @Test
    public void contextLoads() {
        assertEquals(6, 3+3);
    }
}
