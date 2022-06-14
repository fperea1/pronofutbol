package com.base.rest.utils.bd;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.base.rest.exceptions.JsonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class FiltrosUtils {
	
	private FiltrosUtils() {}
	
	public static FiltroTablasView getFiltroByString(String filtro) {
		ObjectMapper objectMapper = new ObjectMapper();
		FiltroTablasView filtroDTO;
		try {
			filtroDTO = objectMapper.readValue(filtro, FiltroTablasView.class);
		} catch (JsonProcessingException e) {
			throw new JsonException();
		}	
		return filtroDTO;
	}

	public static List<SearchCriteriaColumn> getFiltrosColumns(JsonNode filters) {
		
		List<SearchCriteriaColumn> list = new ArrayList<SearchCriteriaColumn>();
		Iterator<String> it = filters.fieldNames();
		while (it.hasNext()) {
			String column = it.next();
			SearchCriteriaColumn scc = new SearchCriteriaColumn();
			scc.setNameColumn(column);
			JsonNode contactNode = filters.path(column);
			if (!contactNode.path("value").asText().contentEquals("null")) {
	            scc.setValue(contactNode.path("value").asText());
	            scc.setMatchMode(contactNode.path("matchMode").asText());
	            list.add(scc);
			}
		}
 		return list;
	}
}
