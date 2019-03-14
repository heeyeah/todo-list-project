package com.todo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.After;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.todo.config.TodoResponse;
import com.todo.config.redis.TodoRedisRepository;
import com.todo.dto.TodoComponentDto;
import com.todo.dto.TodoListDto;
import com.todo.dto.TodoResponseDto;
import com.todo.exception.InternalServerException;
import com.todo.exception.NoContentException;
import com.todo.service.TodoService;
import com.todo.util.TimeUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServiceUnitTest {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private TodoService todoService;
	
	private static TodoComponentDto todoEntity;
	
	private final String contentTemplate = "[%02d] This is test Content!";
	private static AtomicInteger contentCount = new AtomicInteger();
	
	@Test
	public void _1_addTodoData() throws Exception {
		todoEntity = this.setDefaultTodoData();
		todoService.addTodoData(todoEntity);
	}

	@Test
	public void _2_getTodoData() throws Exception {

		// then retrieve
		TodoComponentDto retrieveEntity = todoService.getTodoData(String.valueOf(todoEntity.getId()));

		assertThat(todoEntity.getId()).isEqualTo(retrieveEntity.getId());
		assertThat(todoEntity.getTodoContent()).isEqualTo(retrieveEntity.getTodoContent());
	}
	
	@Test
	public void _3_modifyTodoData() throws Exception {
	
		// for tag
		todoService.addTodoData(this.setDefaultTodoData());
		
		todoEntity.setTodoContent(String.format(contentTemplate, contentCount.incrementAndGet() ));
		todoEntity.getTagSet().add(2);
		
		todoService.modifyTodoData(todoEntity);
	}

	@Test
	public void _4_getTodoListByPaging() throws Exception {
		TodoListDto list = todoService.getTodoListByPaging(1, 5);
	}

	@Test(expected=InternalServerException.class)
	public void _5_checkFinishForTodo() throws Exception {
		TodoResponseDto resEntity = todoService.checkFinishForTodo(1);
		assertThat(resEntity.getResponseCode()).isEqualTo(TodoResponse.SUCCESS);
	}
	
	@Test
	public void _6_checkFinishForTodo() throws Exception {
		TodoResponseDto resEntity = todoService.checkFinishForTodo(2);
		assertThat(resEntity.getResponseCode()).isEqualTo(TodoResponse.SUCCESS);
	}
	
	@Test
	public void _7_checkFinishForTodo() throws Exception {
		TodoResponseDto resEntity = todoService.checkFinishForTodo(1);
		assertThat(resEntity.getResponseCode()).isEqualTo(TodoResponse.SUCCESS);
	}
	

	@Test(expected=NoContentException.class)
	public void getTodoDataForException() throws Exception {
		todoService.getTodoData(String.valueOf(contentCount.get() + 100));
	}

	private TodoComponentDto setDefaultTodoData() {
		
		String now = TimeUtils.getNowDateTime();
		String todoContent = String.format(contentTemplate, contentCount.incrementAndGet());
		
		TodoComponentDto todoDto = new TodoComponentDto();
		todoDto.setTodoContent(todoContent);
		todoDto.setCreateDttm(now);
		todoDto.setModifyDttm(now);
		
		logger.info("######## MAKE DATA {}", todoDto.toString());
		
		return todoDto;
	}
}
