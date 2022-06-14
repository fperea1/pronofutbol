package com.base.rest.utils.bd;

public class SearchCriteriaColumn {

	private String nameColumn;
	
	private Object value;
	
	private String matchMode;

	public SearchCriteriaColumn() {
		super();
	}

	public SearchCriteriaColumn(String nameColumn, Object value, String matchMode) {
		this.nameColumn = nameColumn;
		this.value = value;
		this.matchMode = matchMode;
	}

	public String getNameColumn() {
		return nameColumn;
	}

	public void setNameColumn(String nameColumn) {
		this.nameColumn = nameColumn;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getMatchMode() {
		return matchMode;
	}

	public void setMatchMode(String matchMode) {
		this.matchMode = matchMode;
	}
	
}
