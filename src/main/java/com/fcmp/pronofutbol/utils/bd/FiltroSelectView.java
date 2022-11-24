package com.fcmp.pronofutbol.utils.bd;

import com.fasterxml.jackson.databind.JsonNode;

public class FiltroSelectView {

	private JsonNode value;
	
	private String matchMode;

	public JsonNode getValue() {
		return value;
	}

	public void setValue(JsonNode value) {
		this.value = value;
	}

	public String getMatchMode() {
		return matchMode;
	}

	public void setMatchMode(String matchMode) {
		this.matchMode = matchMode;
	}
	
}
