package com.todo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todo.service.TestService;

@RestController
@RequestMapping("/test")
public class TestController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private TestService testService;
	
	@GetMapping(value = "path")
	public String getMethodName(@RequestParam String param) {
		
		logger.info("start controller~");
//		testService.test();
		
		logger.info("end controller~");
		return "Hello World!";
	}
}
