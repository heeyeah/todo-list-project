package com.todo;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.todo.exception.BaseException;
import com.todo.service.TodoService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ExceptionTest {

	
	@Autowired
	private TodoService todoService;
	
	@Test
	public void throwBaseException() {

//		when(todoService.throwBaseException()).thenThrow(BaseException.class);
		
//		assertThatThrownBy(() -> todoService.throwBaseException())
//		.isInstanceOf(BaseException.class)
//		.hasMessage("hello");
	}
	
}
