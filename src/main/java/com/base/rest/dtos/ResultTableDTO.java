package com.base.rest.dtos;

import java.util.List;

public class ResultTableDTO {

	private List<BaseDTO> list;
	
	private Integer totalRecords;

	public ResultTableDTO(List<BaseDTO> list, Integer totalRecords) {
		super();
		this.list = list;
		this.totalRecords = totalRecords;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<BaseDTO> list) {
		this.list = list;
	}

	public Integer getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}
	
}
