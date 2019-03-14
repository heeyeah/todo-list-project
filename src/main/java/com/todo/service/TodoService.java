package com.todo.service;

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
import com.todo.config.redis.TodoRedisRepository;
import com.todo.dto.TodoComponentDto;
import com.todo.dto.TodoListDto;
import com.todo.dto.TodoResponseDto;
import com.todo.exception.BaseException;
import com.todo.exception.IllegalArgumentException;
import com.todo.exception.InternalServerException;
import com.todo.exception.NoContentException;
import com.todo.exception.RedisDatabaseException;
import com.todo.util.TimeUtils;

@Service
public class TodoService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private static AtomicInteger counter = new AtomicInteger();

	@Autowired
	private TodoRedisRepository todoRedisRepository;

	public TodoComponentDto getTodoData(String id) throws Exception {

		if (id == null)
			throw new IllegalArgumentException("ID가 null값 입니다.");

		Optional<TodoComponentDto> optionalResult = todoRedisRepository.findById(id);

		TodoComponentDto todoData = optionalResult.orElse(null);

		if (todoData == null)
			throw new NoContentException(String.format("%s로 등록된 할일정보가 존재하지 않습니다.", id));

		logger.info(" == Single Todo Data :D =================================================");
		logger.info(" == {}", todoData.toString());
		logger.info(" ======================================================================");

		return todoData;
	}

	public TodoListDto getTodoListByPaging(int pageNum, int pageCount) throws Exception {

		TodoListDto response = new TodoListDto();

		Iterator<TodoComponentDto> itr;

		try {
			itr = todoRedisRepository.findAll().iterator();
		} catch (Exception e) {
			throw new RedisDatabaseException(e.getMessage());
		}

		List<TodoComponentDto> list = new ArrayList<TodoComponentDto>();

		while (itr.hasNext()) { // itr.forEachRemaining(list::add);
			list.add(itr.next());
		}

		Collections.sort(list, (o1, o2) -> o2.getCreateDttm().compareTo(o1.getCreateDttm()));

		int totalRow = list.size();
		int totalPage = 0;

		if (totalRow % pageCount == 0) {
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

	public TodoResponseDto addTodoData(TodoComponentDto todoEntity) throws Exception {

		TodoResponseDto response = new TodoResponseDto();

		String now = TimeUtils.getNowDateTime();

		todoEntity.setId(counter.incrementAndGet());
		todoEntity.setFinished(false);
		todoEntity.setCreateDttm(now);
		todoEntity.setModifyDttm(now);

		try {
			todoRedisRepository.save(todoEntity);
		} catch (Exception e) {
			throw new RedisDatabaseException(e.getMessage());
		}

		response.setResponseCode(TodoResponse.SUCCESS);

		logger.info(" == ADD Todo Data :D ==================================================");
		logger.info(" == {}", todoEntity.toString());
		logger.info(" ======================================================================");

		return response;
	}

	public TodoResponseDto modifyTodoData(TodoComponentDto todoEntity) throws Exception {

		TodoResponseDto response = new TodoResponseDto();

		String now = TimeUtils.getNowDateTime();

		todoEntity.setModifyDttm(now);

		try {
			todoRedisRepository.save(todoEntity);
		} catch (Exception e) {
			throw new RedisDatabaseException(e.getMessage());
		}

		response.setResponseCode(TodoResponse.SUCCESS);

		logger.info(" == EDIT Todo Data :D =================================================");
		logger.info(" == {}", todoEntity.toString());
		logger.info(" ======================================================================");

		return response;
	}

	public TodoResponseDto checkFinishForTodo(int id) throws Exception {

		TodoResponseDto response = new TodoResponseDto();

		TodoComponentDto currentData = this.getTodoData(String.valueOf(id));

		if (currentData.isFinished()) { // uncheck
			updateCheckFinishField(currentData, false);
			logger.debug("== Change Finish to NOT Finish. ");
			response.setResponseCode(TodoResponse.SUCCESS);
			response.setResponseMessage("uncheck");
			return response;
		}

		Set<Integer> tagSet = currentData.getTagSet();

		if (tagSet.isEmpty()) {
			logger.debug("== Related tags don't exist! So just change finish field.");
			updateCheckFinishField(currentData, true);
			response.setResponseCode(TodoResponse.SUCCESS);
			response.setResponseMessage("check");
			return response;
		}

		Set<String> strTagSet = tagSet.stream().map(i -> String.valueOf(i)).collect(Collectors.toSet());

		Iterable<String> ids = () -> strTagSet.iterator();

		Iterator<TodoComponentDto> itr = todoRedisRepository.findAllById(ids).iterator();

		Set<Integer> incompleteTodoSet = new HashSet<Integer>();
		boolean isAbleToFinish = true;

		while (itr.hasNext()) {
			TodoComponentDto el = itr.next();
			if (!el.isFinished()) {
				isAbleToFinish = false;
				incompleteTodoSet.add(el.getId());
			}
		}

		if (isAbleToFinish) {
			logger.debug(" All related tag is finished!");
			updateCheckFinishField(currentData, true);
		} else {
			logger.debug(" you must finish list. [{}]", incompleteTodoSet);
			throw new InternalServerException("미완료인 태그가 존재합니다. => " + incompleteTodoSet);
		}

		response.setResponseCode(TodoResponse.SUCCESS);
		response.setResponseMessage("check");
		return response;
	}

	private void updateCheckFinishField(TodoComponentDto todoEntity, boolean flag) throws Exception {

		todoEntity.setFinished(flag);

		try {
			todoEntity = todoRedisRepository.save(todoEntity);
		} catch (Exception e) {
			throw new RedisDatabaseException(e.getMessage());
		}

		logger.info(" == Check Finish Flag for Todo Data :D ================================");
		logger.info(" == Flag [{}] {}", flag, todoEntity.toString());
		logger.info(" ======================================================================");
	}
}
