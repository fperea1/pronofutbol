package com.base.rest.utils.bd;

import com.fasterxml.jackson.databind.JsonNode;

public class FiltroTablasView {
	
	private int first;
	
	private int rows;
	
	private String sortField;
	
	private int sortOrder;
	
	private JsonNode filters;
	
	private String globalFilter;
	
	private String multiSortMeta;

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public JsonNode getFilters() {
		return filters;
	}

	public void setFilters(JsonNode filters) {
		this.filters = filters;
	}

	public String getGlobalFilter() {
		return globalFilter;
	}

	public void setGlobalFilter(String globalFilter) {
		this.globalFilter = globalFilter;
	}

	public String getMultiSortMeta() {
		return multiSortMeta;
	}

	public void setMultiSortMeta(String multiSortMeta) {
		this.multiSortMeta = multiSortMeta;
	}

}
