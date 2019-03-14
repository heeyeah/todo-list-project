package com.todo.exception;

public class InternalServerException extends BaseException {

	private static final long serialVersionUID = 1L;

	public InternalServerException(String message) {
		super(message);
	}
}
