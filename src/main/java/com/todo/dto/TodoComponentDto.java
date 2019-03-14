package com.todo.dto;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@RedisHash("todoComponent")
public class TodoComponentDto {

	@Id
	private int id;

	private String todoContent;
	private String createDttm;
	private String modifyDttm;
	private boolean isFinished;
	private final Set<Integer> tagSet;
	
	public TodoComponentDto() {
		this.tagSet = new HashSet<Integer>();
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

	public Set<Integer> getTagSet() {
		return tagSet;
	}


	@Override
	public String toString() {
		return "TodoComponentDto [id=" + id + ", todoContent=" + todoContent + ", createDttm=" + createDttm
				+ ", modifyDttm=" + modifyDttm + ", isFinished=" + isFinished + ", tagSet=" + tagSet + "]";
	}

}
