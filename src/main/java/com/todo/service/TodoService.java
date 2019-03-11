package com.todo.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.dto.TodoComponentDto;
import com.todo.redis.TodoRedisRepository;

@Service
public class TodoService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
    private TodoRedisRepository todoRedisRepository;

	public TodoComponentDto getTodoData(String id) {

		Optional<TodoComponentDto> optionalResult = todoRedisRepository.findById(id);

		TodoComponentDto todoData = optionalResult.orElse(null);
	
        return todoData;
	}
}
