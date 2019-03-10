package com.todo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
	@ResponseBody
	public TodoComponentDto getTodoData(@RequestParam(value="id") String id) {
		
		TodoComponentDto todoData = todoService.getTodoData(id);
		
		if(todoData == null) {
			// error
		}
		
		return todoData;
	}

}
