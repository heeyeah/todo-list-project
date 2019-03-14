package com.todo;

import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.todo.redis.TodoRedisRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisUnitTest {

    @Autowired
    private TodoRedisRepository pointRedisRepository;

    @After
    public void tearDown() throws Exception {
        pointRedisRepository.deleteAll();
    }

//    @Test
//    public void 기본_등록_조회기능() {
//        //given 
//        String id = "hee";
//        LocalDateTime refreshTime = LocalDateTime.now();
//        Point point = new Point("hee", 200l, LocalDateTime.now());
//
//        //when
//        pointRedisRepository.save(point);
//
//        //then
//        Point savedPoint = pointRedisRepository.findById(id).get();
//
//        System.out.println(savedPoint.getAmount());
//        System.out.println(savedPoint.getRefreshTime());
//        
//        assertThat(savedPoint.getAmount()).isEqualTo(300l);
////        assertThat(savedPoint.getRefreshTime()).isEqualTo(refreshTime);
//    }
}