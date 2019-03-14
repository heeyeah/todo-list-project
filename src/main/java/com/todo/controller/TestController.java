package com.todo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todo.exception.BaseException;

@RestController
public class TestController {

	@GetMapping(value="/ex")
	public void executeException() throws BaseException {
		throw new BaseException("태그 중 완료되지 않은 할 일이 남아있습니다.");
	}
}
