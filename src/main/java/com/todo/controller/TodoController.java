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

import com.todo.config.TodoResponse;
import com.todo.dto.TodoComponentDto;
import com.todo.dto.TodoListDto;
import com.todo.dto.TodoResponseDto;
import com.todo.exception.BaseException;
import com.todo.exception.NoContentException;
import com.todo.service.TodoService;

@RestController
@RequestMapping("/")
public class TodoController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private TodoService todoService;

	@GetMapping(value = "/todo/{id}")
	@Description("Get single todo data")
	public ResponseEntity<TodoComponentDto> getTodoData(@PathVariable String id) throws NoContentException {

		TodoComponentDto todoData = todoService.getTodoData(id);

		if(todoData == null) throw new NoContentException(String.format("%s로 등록된 할일정보가 없습니다.", id));

		return new ResponseEntity<TodoComponentDto>(todoData, HttpStatus.OK);
	}

	@GetMapping(value = "/todo")
	@Description("Get total todo list by paging")
	public ResponseEntity<TodoListDto> getTodoListByPaging(@RequestParam int pageNum, @RequestParam int pageCount) throws NoContentException {

		TodoListDto todoList = todoService.getTodoListByPaging(pageNum, pageCount);

		if (todoList == null) throw new NoContentException("할일정보가 존재하지 않습니다.");

		return new ResponseEntity<TodoListDto>(todoList, HttpStatus.OK);
	}

	@PostMapping(value = "/todo")
	@Description("Add todo data")
	public ResponseEntity<TodoResponseDto> addTodoData(@RequestBody TodoComponentDto todoEntity) throws BaseException {

		TodoResponseDto response = todoService.addTodoData(todoEntity);

		if (response.getResponseCode() == TodoResponse.FAIL) throw new BaseException("등록에 실패했습니다.");

		return new ResponseEntity<TodoResponseDto>(response, HttpStatus.OK);
	}

	@PatchMapping(value = "/todo")
	@Description("Modfiy todo data")
	public ResponseEntity<TodoResponseDto> modifyTodoData(@RequestBody TodoComponentDto todoEntity) throws BaseException {

		TodoResponseDto response = todoService.modifyTodoData(todoEntity);

		if (response.getResponseCode() == TodoResponse.FAIL) throw new BaseException(response.getResponseMessage());
		return new ResponseEntity<TodoResponseDto>(response, HttpStatus.OK);
	}

	@PatchMapping(value = "/check")
	@Description("Check finish field for todo")
	public ResponseEntity<TodoResponseDto> checkFinishForTodo(@RequestBody TodoComponentDto todoEntity) throws BaseException {

		TodoResponseDto response = todoService.checkFinishForTodo(todoEntity.getId());

		if (response.getResponseCode() == TodoResponse.FAIL) throw new BaseException(response.getResponseMessage());
		
		return new ResponseEntity<TodoResponseDto>(response, HttpStatus.OK);
	}

	@PatchMapping(value = "/uncheck")
	@Description("Uncheck finish field for todo")
	public ResponseEntity<TodoResponseDto> uncheckFinishForTodo(@RequestBody TodoComponentDto todoEntity) throws BaseException {

		TodoResponseDto response = todoService.uncheckFinishForTodo(todoEntity.getId());

		if (response.getResponseCode() == TodoResponse.FAIL) throw new BaseException(response.getResponseMessage());
		return new ResponseEntity<TodoResponseDto>(response, HttpStatus.OK);
	}
}
