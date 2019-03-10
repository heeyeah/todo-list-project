package com.todo.dto;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("todoComponent")
public class TodoComponentDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	private int id;
	private String todoContent;
	private String createDttm;
	private String modifyDttm;
	private boolean isFinished;
	
	public TodoComponentDto(int id, String todoContent, String createDttm, String modifyDttm, boolean isFinished) {
		this.id = id;
		this.todoContent = todoContent;
		this.createDttm = createDttm;
		this.modifyDttm = modifyDttm;
		this.isFinished = isFinished;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTodoContent() {
		return todoContent;
	}
	public void setTodoContent(String todoContent) {
		this.todoContent = todoContent;
	}
	public String getCreateDttm() {
		return createDttm;
	}
	public void setCreateDttm(String createDttm) {
		this.createDttm = createDttm;
	}
	public String getModifyDttm() {
		return modifyDttm;
	}
	public void setModifyDttm(String modifyDttm) {
		this.modifyDttm = modifyDttm;
	}
	public boolean isFinished() {
		return isFinished;
	}
	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}
}
