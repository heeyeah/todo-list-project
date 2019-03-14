package com.todo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todo.dto.TodoComponentDto;
import com.todo.dto.TodoListDto;
import com.todo.dto.TodoResponseDto;
import com.todo.service.TodoService;

@RestController
@RequestMapping("/todo")
public class TodoController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private TodoService todoService;

	@GetMapping(value = "/{id}")
	@Description("Get single todo data")
	public ResponseEntity<TodoComponentDto> getTodoData(@PathVariable String id) throws Exception {

		logger.info("== GET[/todo/id] getTodoData START ===============");
		
		TodoComponentDto todoData = todoService.getTodoData(id);

		return new ResponseEntity<TodoComponentDto>(todoData, HttpStatus.OK);
	}

	@GetMapping(value = "/")
	@Description("Get total todo list by paging")
	public ResponseEntity<TodoListDto> getTodoListByPaging(@RequestParam int pageNum, @RequestParam int pageCount)
			throws Exception {

		logger.info("== GET[/todo] getTodoListByPaging START ===============");
		
		TodoListDto todoList = todoService.getTodoListByPaging(pageNum, pageCount);

		return new ResponseEntity<TodoListDto>(todoList, HttpStatus.OK);
	}

	@PostMapping(value = "/")
	@Description("Add todo data")
	public ResponseEntity<TodoResponseDto> addTodoData(@RequestBody TodoComponentDto todoEntity) throws Exception {
		
		logger.info("== POST[/todo] addTodoData START ===============");
		
		TodoResponseDto response = todoService.addTodoData(todoEntity);

		return new ResponseEntity<TodoResponseDto>(response, HttpStatus.OK);
	}

	@PatchMapping(value = "/")
	@Description("Modfiy todo data")
	public ResponseEntity<TodoResponseDto> modifyTodoData(@RequestBody TodoComponentDto todoEntity) throws Exception {

		logger.info("== PATCH[/todo] modifyTodoData START ===============");
		
		TodoResponseDto response = todoService.modifyTodoData(todoEntity);

		return new ResponseEntity<TodoResponseDto>(response, HttpStatus.OK);
	}

	@PatchMapping(value = "/toggle")
	@Description("Check finish field for todo")
	public ResponseEntity<TodoResponseDto> checkFinishForTodo(@RequestBody TodoComponentDto todoEntity)
			throws Exception {

		logger.info("== PATCH[/todo/toggle] checkFinishForTodo START ===============");

		TodoResponseDto response = todoService.checkFinishForTodo(todoEntity.getId());

		return new ResponseEntity<TodoResponseDto>(response, HttpStatus.OK);
	}
}
