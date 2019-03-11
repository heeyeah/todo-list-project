package com.todo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.todo.config.TodoResponse;
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

	@GetMapping(value="/data")
	@Description("Get single todo data")
	public ResponseEntity<TodoComponentDto> getTodoData(@RequestParam(required=false, value="id") String id) {
		
		TodoComponentDto todoData = todoService.getTodoData(id);
		
		HttpStatus httpStatus = (todoData == null) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
		
		return new ResponseEntity<TodoComponentDto>(todoData, httpStatus);
	}

	@GetMapping(value="/list")
	@Description("Get total todo list by paging")
	public ResponseEntity<TodoListDto> getTodoListByPaging(@RequestParam int pageNum, @RequestParam int pageCount) {
	
		TodoListDto todoList = todoService.getTodoListByPaging(pageNum, pageCount);
	
		HttpStatus httpStatus = (todoList == null) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
		
		return new ResponseEntity<TodoListDto>(todoList, httpStatus);
	}

	@PostMapping(value="/data")
	@Description("Add todo data")
	public ResponseEntity<TodoResponseDto> addTodoData(@RequestBody TodoComponentDto todoEntity) {
	
		TodoResponseDto response = todoService.addTodoData(todoEntity);
		
		HttpStatus httpStatus = (response.getResponseCode() == TodoResponse.SUCCESS) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
	
		return new ResponseEntity<TodoResponseDto>(response, httpStatus);
	}
	
	@PatchMapping(value="/data")
	@Description("Modfiy todo data")
	public ResponseEntity<TodoResponseDto> modifyTodoData(@RequestBody TodoComponentDto todoEntity) {
		
		TodoResponseDto response = todoService.modifyTodoData(todoEntity);
		
		HttpStatus httpStatus = (response.getResponseCode() == TodoResponse.SUCCESS) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
		
		return new ResponseEntity<TodoResponseDto>(response, httpStatus);
	}
	
	@PatchMapping(value = "/finish")
	@Description("Modify todo data for isFinished field")
	public ResponseEntity<TodoResponseDto> modifyTodoDataForFinish(@RequestParam String id) {
		
		TodoResponseDto response = todoService.modifyTodoDataForFinish(id);
		
		HttpStatus httpStatus = (response.getResponseCode() == TodoResponse.SUCCESS) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
		
		return new ResponseEntity<TodoResponseDto>(response, httpStatus);
	}
}
