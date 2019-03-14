package com.todo.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.todo.config.TodoResponse;
import com.todo.dto.TodoResponseDto;

@ControllerAdvice
public class GlobalExceptionHandling {

	private Logger logger;

	public GlobalExceptionHandling() {
		logger = LoggerFactory.getLogger(getClass());
	}

	@ExceptionHandler(value = { BaseException.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	protected ResponseEntity<?> handleBaseException(Exception ex) {
		logger.error(" baseExceptionHandler. {}", ex.getMessage());
		return errorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = { NoContentException.class })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ResponseBody
	protected ResponseEntity<?> handleNoContentException(Exception ex) {
		logger.error(" noContentExceptionHandler. {}", ex.getMessage());
		return errorResponse(ex, HttpStatus.NO_CONTENT);
	}

	protected ResponseEntity<TodoResponseDto> errorResponse(Throwable throwable, HttpStatus status) {
		TodoResponseDto res = new TodoResponseDto();
		res.setResponseCode(TodoResponse.FAIL);
		res.setResponseMessage(throwable.getMessage());
		return new ResponseEntity<TodoResponseDto>(res, status);
	}

	
}
