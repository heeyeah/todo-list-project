package com.todo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.todo.controller.TodoController;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerUnitTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	TodoController todoController;
	
	@Before
	public void setup() {
		mvc = MockMvcBuilders.standaloneSetup(todoController).build();
	}
	
	@Test
	public void getTodoData() throws Exception {

		mvc.perform(get("/todo/1"))
		.andExpect(status().isOk())
		.andDo(print());
	}
	
	
}
