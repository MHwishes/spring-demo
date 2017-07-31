package com.example.demo;


import com.example.demo.dao.PersonRepository;
import com.example.demo.entity.Person;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class IntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    PersonRepository personRepository;

    @Before
    public void setUp() {
        Person person1 = new Person("huh", 7);
        Person person2 = new Person("mahong", 7);
        personRepository.save(person1);
        personRepository.save(person2);
    }

    @Test
    public void getOnePersonIfIdIsExist() throws Exception {

        Integer id = personRepository.findAll().get(0).getId();

        Person person = restTemplate
                .getForObject("http://127.0.0.1:" + port + "/springboot/person/" + id, Person.class);
        assertThat(person.getId(), is(id));
    }

    @Test
    public void getAllPerson() throws Exception {
        personRepository.deleteAll();
        final ResponseEntity<List> response = restTemplate
                .getForEntity("http://127.0.0.1:" + port + "/springboot/person", List.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }


    @Test
    @Rollback()
    public void PostPerson() throws Exception {

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final Person person = new Person("uuuuuuuuuuuuuuuuuuu", 45);
        final HttpEntity<Person> requestUpdate = new HttpEntity<>(person, headers);

        final ResponseEntity<Person> response = restTemplate
                .exchange("http://127.0.0.1:" + port + "/springboot/person", HttpMethod.POST, requestUpdate, Person.class);

        assertThat(person.getName(), is(response.getBody()
                .getName()));

        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
    }

    @Test
    public void UpdatePerson() throws Exception {

        Person person1 = personRepository.findAll().get(0);
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Integer id=person1.getId();
        final Person person = new Person("fhajfh", 47, id);
        final HttpEntity<Person> requestUpdate = new HttpEntity<>(person, headers);

        final ResponseEntity<Person> response = restTemplate
                .exchange("http://127.0.0.1:" + port + "/springboot/person/"+id, HttpMethod.PUT, requestUpdate, Person.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.NO_CONTENT));
    }

    @Test
    public void DeletePerson() throws Exception {
        Integer id = personRepository.findAll().get(0).getId();
        final ResponseEntity<Person> response = restTemplate
                .exchange("http://127.0.0.1:" + port + "/springboot/person/"+id, HttpMethod.DELETE, null, Person.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.NO_CONTENT));
    }


}
