package com.todo.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.config.TodoResponse;
import com.todo.dto.TodoComponentDto;
import com.todo.dto.TodoListDto;
import com.todo.dto.TodoResponseDto;
import com.todo.redis.TodoRedisRepository;

@Service
public class TodoService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private final AtomicInteger counter = new AtomicInteger();
	
	@Autowired
    private TodoRedisRepository todoRedisRepository;
	
	public TodoComponentDto getTodoData(String id) {

		Optional<TodoComponentDto> optionalResult = todoRedisRepository.findById(id);

		TodoComponentDto todoData = optionalResult.orElse(null);
	
        return todoData;
	}
	
	public TodoResponseDto addTodoData(TodoComponentDto todoEntity) {
		
		TodoResponseDto response = new TodoResponseDto();
		
		String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));

		todoEntity.setId(counter.incrementAndGet());
		todoEntity.setFinished(false);
		todoEntity.setCreateDttm(now);
		todoEntity.setModifyDttm(now);
		
		todoRedisRepository.save(todoEntity);
		
		logger.info(" == ADD Todo Data :D ==================================================");
		logger.info(" == {}", todoEntity.toString());
		logger.info(" ======================================================================");
		
		response.setResponseCode(TodoResponse.SUCCESS);
		return response;
	}
	
	public TodoResponseDto modifyTodoData(TodoComponentDto todoEntity) {

		
		TodoResponseDto response = new TodoResponseDto();
		
		String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));

		todoEntity.setModifyDttm(now);

		todoRedisRepository.save(todoEntity);
		
		logger.info(" == EDIT Todo Data :D =================================================");
		logger.info(" == {}", todoEntity.toString());
		logger.info(" ======================================================================");
		
		response.setResponseCode(TodoResponse.SUCCESS);
		return response;
	}
	
	public TodoListDto getTodoListByPaging(int pageNum, int pageCount) {
		
		TodoListDto response = new TodoListDto();

		Iterator<TodoComponentDto> itr = todoRedisRepository.findAll().iterator();

		List<TodoComponentDto> list = new ArrayList<TodoComponentDto>();
		
		while(itr.hasNext()) {
			list.add(itr.next());
		}
		
		Collections.sort(list, (o1, o2) -> o2.getModifyDttm().compareTo(o1.getModifyDttm()));
		
		int totalRow = list.size();
		int totalPage = 0;
		
		if(totalRow % pageCount == 0) {
			totalPage = totalRow / pageCount;
		} else {
			totalPage = (totalRow / pageCount) + 1;
		}

		int fromIndex = (pageNum - 1) * pageCount;
		int toIndex = Math.min(fromIndex + pageCount, totalRow);

		response.setTotalRow(totalRow);
		response.setTotalPage(totalPage);
		response.setPageNum(pageNum);
		response.setPageCount(pageCount);
		response.setList(list.subList(fromIndex, toIndex));
		
		
		logger.info(" == LIST Todo Data :D =================================================");
		logger.info(" == {}", response.toString());
		logger.info(" ======================================================================");
		
		
		return response;
	}
	
	public TodoResponseDto modifyTodoDataForFinish(String id) {
		
		TodoResponseDto response = new TodoResponseDto();
		
		Set<Integer> tagSet = null;
		Optional<TodoComponentDto> todoData = todoRedisRepository.findById(id);
		TodoComponentDto currentData;
		
		if(todoData.isPresent()) {
			currentData = todoData.get();
		} else {
			response.setResponseCode(TodoResponse.FAIL);
			return response;
		}
		
		tagSet = currentData.getTagSet();
		
		if(tagSet.isEmpty()) {
			logger.info("related tags don't exist! ");
			updateFinishField(currentData);
			response.setResponseCode(TodoResponse.SUCCESS);
			return response;
		}
		
		Set<String> strSet = tagSet.stream().map(i -> String.valueOf(i)).collect(Collectors.toSet());
		
		Iterable<String> ids = () -> strSet.iterator();
		
		tagSet.iterator();

		Iterator<TodoComponentDto> itrr = todoRedisRepository.findAllById(ids).iterator();

		Set<Integer> yetFinish = new HashSet<Integer>();
		boolean isAbleToFinish = true;
		while(itrr.hasNext()) {
			TodoComponentDto el = itrr.next();
			if(!el.isFinished()) {
				isAbleToFinish = false;
				yetFinish.add(el.getId());
			}
		}
		
		if(isAbleToFinish) {
			updateFinishField(currentData);
		} else {
			logger.info(" you must finish list. [{}]", yetFinish);
		}
		
		TodoResponse code = (isAbleToFinish) ? TodoResponse.SUCCESS : TodoResponse.FAIL;
		response.setResponseCode(code);
		
		return response;
	}
	
	private TodoResponse updateFinishField(TodoComponentDto todoEntity) {

		todoEntity.setFinished(true);
		todoRedisRepository.save(todoEntity);
		logger.info(" !!! yeah, is finished!");
		
		return TodoResponse.SUCCESS;
	}
}
