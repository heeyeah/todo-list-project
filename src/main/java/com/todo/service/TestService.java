package com.todo.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.todo.dto.Point;
import com.todo.dto.TodoComponentDto;
import com.todo.redis.TodoRedisRepository;

@Service
public class TestService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
    private TodoRedisRepository pointRedisRepository;

//	public void test() {
//        //given 
//        String id = "hee";
//        Point point = new Point("hee", 200l, LocalDateTime.now());
//        Point point2 = new Point("hee2", 400l, LocalDateTime.now());
//
//        //when
//        pointRedisRepository.save(point);
//        pointRedisRepository.save(point2);
//
//        Iterable<Point> itr = pointRedisRepository.findAll();
//        Iterator<Point> list = itr.iterator();
//        
//        while(list.hasNext()) {
//        	Point next = list.next();
//        	logger.info("############## {}", next.toString());
//        }
//        
//        
//	}
	
	public void test_redis() {
	}
}
