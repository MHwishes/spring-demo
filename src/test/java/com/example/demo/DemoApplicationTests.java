package com.example.demo;

import com.example.demo.controller.HelloController;
import com.example.demo.controller.PersonController;
import com.example.demo.dao.PersonRepository;
import com.example.demo.entity.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.dao.PersonRepository;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

//	@Autowired
	@Mock
	private PersonRepository personRepository;

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setUp(){
		initMocks(this);
		PersonController personController = new PersonController();
		personController.setRepo(personRepository);
		Person mockUser = new Person();
		mockUser.setName("john");
		mockUser.setAge(12);
		when(personRepository.save(mockUser)).thenReturn(mockUser);

//		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		mockMvc = MockMvcBuilders.standaloneSetup(personController).build();


	}

	@Test
	public void simpleTest() throws Exception {
		Person mockUser = new Person();
		mockUser.setName("john");
		mockUser.setAge(12);
		Person person = personRepository.save(mockUser);
		assertEquals("john", mockUser.getName());

	}


	@Test
	public void urltest()throws  Exception{

		Person user = new Person("yuuuuy",8);
		List<Person> userList = new LinkedList<Person>();
		userList.add(user);

		when(personRepository.findAll()).thenReturn(userList);

//		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		assertEquals(mockingDetails(personRepository).isMock(),true);


		mockMvc.perform(get("/person"))
				.andExpect(status().isOk())
		        .andExpect(jsonPath("$",hasSize(1)));

	}














	@Test
	public void findAll(){
		assertNotNull(personRepository.findAll());
	}

	@Test
	public void fisttest(){
		HelloController  helloController=new HelloController();
		assertEquals("Hello World",helloController.index());
	}

	@Test
	public void contextLoads() {
		assertEquals(6, 3+3);
	}

}
