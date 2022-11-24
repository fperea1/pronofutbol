package com.fcmp.pronofutbol.utils.bd;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fcmp.pronofutbol.exceptions.JsonException;

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
	
	public static FiltroSelectView getFiltroSelectByString(String filtro) {
		ObjectMapper objectMapper = new ObjectMapper();
		FiltroSelectView filtroDTO;
		try {
			filtroDTO = objectMapper.readValue(filtro, FiltroSelectView.class);
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
			JsonNode node = filters.path(column);
			if (!node.path("value").asText().contentEquals("null")) {
	            scc.setValue(node.path("value").asText());
	            scc.setMatchMode(node.path("matchMode").asText());
	            list.add(scc);
			}
		}
 		return list;
	}
	
	public static List<SearchCriteriaColumn> getFiltrosSelect(JsonNode filters) {
		
		List<SearchCriteriaColumn> list = new ArrayList<SearchCriteriaColumn>();
		Iterator<String> it = filters.fieldNames();
		while (it.hasNext()) {
			String column = it.next();
			JsonNode node = filters.path(column);
			if (!node.path("value").asText().contentEquals("null") && node.path("matchMode").asText().equals("select") ) {
				for (String s: node.findValuesAsText("nombre")) {
					SearchCriteriaColumn scc = new SearchCriteriaColumn();
					scc.setValue(s);
			        list.add(scc);
				}
			}
		}
 		return list;
	}
}
