package com.todo.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	// @ExceptionHandler(value = { BaseException.class })
	// @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	// @ResponseBody
	// public ResponseEntity<?> handleBaseException(Exception ex) {
	// logger.error(" handleBaseException. {}", ex.getMessage());
	// return errorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
	// }

	@ExceptionHandler(value = { NoContentException.class })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ResponseBody
	public ResponseEntity<?> handleNoContentException(Exception ex) {
		logger.error(" handleNoContentException. {}", ex.getMessage());
		return errorResponse(ex, HttpStatus.NO_CONTENT);
	}

	@ExceptionHandler(value = { InternalServerException.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ResponseEntity<?> handleInternalServerException(Exception ex) {
		logger.error(" handleInternalServerException. {}", ex.getMessage());
		return errorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = { NotFoundException.class })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ResponseEntity<?> handleNotFoundException(Exception ex) {
		logger.error(" handleNotFoundException. {}", ex.getMessage());
		return errorResponse(ex, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = { RedisDatabaseException.class })
	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	@ResponseBody
	public ResponseEntity<?> handleServiceUnavailableException(Exception ex) {
		logger.error(" handleServiceUnavailableException. {}", ex.getMessage());
		return errorResponse(ex, HttpStatus.SERVICE_UNAVAILABLE);
	}

	public ResponseEntity<TodoResponseDto> errorResponse(Throwable throwable, HttpStatus status) {
		TodoResponseDto res = new TodoResponseDto();
		res.setResponseCode(TodoResponse.FAIL);
		res.setResponseMessage(throwable.getMessage());
		return new ResponseEntity<TodoResponseDto>(res, status);
	}
}
