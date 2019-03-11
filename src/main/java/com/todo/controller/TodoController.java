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

import com.todo.dto.TodoComponentDto;
import com.todo.service.TodoService;

@RestController
@RequestMapping("/todo")
public class TodoController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private TodoService todoService;

	/*
	 * 단건등록
	 *  - 등록할 때, 태그목록도 받아서 추가해줘야 함.
	 * 단건수정
	 * 단건조회
	 * 목록조회 (페이징)
	 * 완료처리
	 * 
	 */

	@GetMapping(value="/data")
	@Description("Get single todo data")
	@ResponseBody
	public ResponseEntity<TodoComponentDto> getTodoData(@RequestParam(required=false, value="id") String id) {
		
		TodoComponentDto todoData = todoService.getTodoData(id);
		
		HttpStatus httpStatus = (todoData == null) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
		
		return new ResponseEntity<TodoComponentDto>(todoData, httpStatus);
	}

	@GetMapping(value="/list")
	@Description("Get total todo list by paging")
	public ResponseEntity<TodoComponentDto> getTodoDataByPaging(@RequestParam int pageNum, @RequestParam int pageCount) {
		
		return null;
	}

	@PostMapping(value="/data")
	@Description("Add todo data")
	public ResponseEntity<TodoComponentDto> addTodoData(@RequestBody TodoComponentDto todoEntity) {
		
		return null;
	}
	
	@PatchMapping(value="/data")
	@Description("Modfiy todo data")
	public ResponseEntity<TodoComponentDto> modifyTodoData(@RequestBody TodoComponentDto todoEntity) {
		
		return null;
	}
	
	@PatchMapping(value = "/checkFinish")
	@Description("Modify todo data for isFinished field")
	public ResponseEntity<TodoComponentDto> modifyTodoDataForFinish(@RequestParam String id) {
		
		return null;
	}
}
