package com.todo.redis;

import org.springframework.data.repository.CrudRepository;

import com.todo.dto.TodoComponentDto;

public interface TodoRedisRepository extends CrudRepository<TodoComponentDto, String> {
}
