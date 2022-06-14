package com.base.rest.dtos;

import java.util.List;

public class ResultTableDTO {

	private List<BaseDTO> list;
	
	private Long totalRecords;

	public ResultTableDTO(List<BaseDTO> list, Long totalRecords) {
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

	public Long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}
	
}
