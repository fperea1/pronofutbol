package com.fcpm.pronofutbol.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fcpm.pronofutbol.constant.Constantes;
import com.fcpm.pronofutbol.dtos.AutenticacionDTO;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class LogControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	private static String token;
	
	static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	@Test
	@Order(1) 
	void testGetToken() throws Exception {
		
		AutenticacionDTO a = getAutenticacion("administrador", "Administrador01");
		
		String requestJson = getJson(a);

		ResultActions response = mockMvc
		    .perform(post("/autenticacion/generate-token")
			.contentType(APPLICATION_JSON_UTF8)
			.content(requestJson))
		    .andExpect(status().isOk());
		
		token = response.andReturn().getResponse().getContentAsString();

		//System.out.println(response.andReturn().getResponse().getContentAsString());
	}
	
	@Test
	@Order(2) 
	void testFindAll() throws Exception {
		
		String filtro = "{\"first\":0,\"rows\":10,\"sortOrder\":1,\"filters\":{},\"globalFilter\":null}";
		mockMvc
		    .perform(get(Constantes.LOGS + Constantes.FIND_BY_FILTER)
		    .param("filtro", filtro)
		    .header("authorization", "Bearer " + token))
		    .andDo(print())
		    .andExpect(status().isOk())
		    //.andExpect(jsonPath("$.length()").value(1))
		    .andExpect(jsonPath("$.length()").isNotEmpty())
		    .andExpect(jsonPath("$.list[0].username").value("administrador"));
		    //.andExpect(jsonPath("$[?(@.username === 'administrador')]").exists());
		
		//System.out.println(response.andReturn().getResponse().getContentAsString());
	}
	
	@Test
	@Order(3) 
	void testFindLogout() throws Exception {
		
		String filtro = "{\"first\":0,\"rows\":10,\"sortOrder\":1,\"filters\":{\"username\":{\"value\":null,\"matchMode\":\"startsWith\"},\"entidad\":{\"value\":null,\"matchMode\":\"startsWith\"},\"accion\":{\"value\":\"logout\",\"matchMode\":\"startsWith\"},\"observaciones\":{\"value\":null,\"matchMode\":\"startsWith\"},\"fecha\":{\"value\":null,\"matchMode\":\"startsWith\"}},\"globalFilter\":null}";
		mockMvc
		    .perform(get(Constantes.LOGS + Constantes.FIND_BY_FILTER)
		    .param("filtro", filtro)
		    .header("authorization", "Bearer " + token))
		    .andDo(print())
		    .andExpect(status().isOk())
		    //.andExpect(jsonPath("$.length()").value(1))
		    .andExpect(jsonPath("$.length()").isNotEmpty())
		    .andExpect(jsonPath("$.totalRecords").value("0"));
		    //.andExpect(jsonPath("$[?(@.username === 'administrador')]").exists());
		
		//System.out.println(response.andReturn().getResponse().getContentAsString());
	}
	
	private AutenticacionDTO getAutenticacion(String username, String password) {
		AutenticacionDTO a = new AutenticacionDTO();
		a.setUsername(username);
		a.setPassword(password);
		return a;
	}

	private String getJson(AutenticacionDTO a) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson=ow.writeValueAsString(a);
		return requestJson;
	}

}
