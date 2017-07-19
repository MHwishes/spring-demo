package com.example.demo;

import com.example.demo.controller.HelloController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

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
