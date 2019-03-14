package com.todo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.todo.config.redis.TodoRedisRepository;
import com.todo.dto.TodoComponentDto;
import com.todo.util.TimeUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisUnitTest {

    @Autowired
    private TodoRedisRepository todoRedisRepository;

    @Before
    public void addDefaultTestData() {
    	
    }
    
    @After
    public void tearDown() throws Exception {
        todoRedisRepository.deleteAll();
    }
    
    @Test
    public void saveTodoData() {
    	
    }
    
    @Test
    public void findTodoDataById() {
    	
    }

    @Test
    public void findAllTodoData() {
    	
    }
    
    @Test
    public void testAddThenRetrieve() {
    	
    	TodoComponentDto todoDto = new TodoComponentDto();
    	
    	int id = 1;
    	String now = TimeUtils.getNowDateTime();
    	String todoContent = " test to get single todo data!";
    	todoDto.setId(id);
    	todoDto.setTodoContent(todoContent);
    	todoDto.setCreateDttm(now);
    	todoDto.setModifyDttm(now);
    	//when
    	todoRedisRepository.save(todoDto);
    	
    	//then
    	TodoComponentDto result = todoRedisRepository.findById(String.valueOf(id)).get();

    	assertThat(todoDto.getTodoContent()).isEqualTo(result.getTodoContent());
    }
  
}