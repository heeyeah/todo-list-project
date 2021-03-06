package com.todo.dto;

import com.todo.config.TodoResponse;

public class TodoResponseDto {
	
	private TodoResponse responseCode;
	private String responseMessage;
	
	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public TodoResponse getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(TodoResponse responseCode) {
		this.responseCode = responseCode;
	}
}
