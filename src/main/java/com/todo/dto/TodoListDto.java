package com.todo.dto;

import java.util.List;

public class TodoListDto {

	private int pageNum;
	private int pageCount;
	private long totalRow;
	private int totalPage;

	private List<TodoComponentDto> list;

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public long getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(long totalRow) {
		this.totalRow = totalRow;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<TodoComponentDto> getList() {
		return list;
	}

	public void setList(List<TodoComponentDto> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "TodoListDto [pageNum=" + pageNum + ", pageCount=" + pageCount + ", totalRow=" + totalRow
				+ ", totalPage=" + totalPage + ", list=" + list + "]";
	}

}
