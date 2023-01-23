package com.fcpm.pronofutbol.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fcpm.pronofutbol.constant.Constantes;
import com.fcpm.pronofutbol.dtos.AutenticacionDTO;
import com.fcpm.pronofutbol.dtos.PaisDTO;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class PaisControllerTest {
	
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
		    .perform(post(Constantes.AUTENTICATION + Constantes.GENERAR_TOKEN)
			.contentType(APPLICATION_JSON_UTF8)
			.content(requestJson))
		    .andExpect(status().isOk());
		
		token = response.andReturn().getResponse().getContentAsString();
	}
	
	@Test
	@Order(2) 
	void testFindForSelect() throws Exception {
		
		mockMvc
		    .perform(get(Constantes.PAISES + Constantes.FIND_FOR_SELECT)
		    .header("authorization", "Bearer " + token))
		    .andDo(print())
		    .andExpect(status().isOk())
		    .andExpect(jsonPath("$.length()").isNotEmpty())
		    .andExpect(jsonPath("$.[0].nombre").value("ALEMANIA"));
	}
	
	@Test
	@Order(3) 
	void testSave() throws Exception {
		
		PaisDTO u = getDTO("nombre test");
		
		String requestJson = getJson(u);
		
		mockMvc
		    .perform(post(Constantes.PAISES + Constantes.SAVE)
		    .contentType(APPLICATION_JSON_UTF8)
		    .content(requestJson)
		    .header("authorization", "Bearer " + token))
		    .andExpect(status().isOk());	
	}
	
	@Test
	@Order(4) 
	void testFindByFilter() throws Exception {
		
		String filtro = "{\"first\":0,\"rows\":10,\"sortOrder\":1,\"filters\":{},\"globalFilter\":null}";
		mockMvc
		    .perform(get(Constantes.PAISES + Constantes.FIND_BY_FILTER)
		    .param("filtro", filtro)
		    .header("authorization", "Bearer " + token))
		    .andDo(print())
		    .andExpect(status().isOk())
		    //.andExpect(jsonPath("$.length()").value(1))
		    .andExpect(jsonPath("$.length()").isNotEmpty())
		    .andExpect(jsonPath("$.list[0].nombre").value("nombre test"));
		
		//System.out.println(response.andReturn().getResponse().getContentAsString());
	}
	
	@Test
	@Order(5) 
	void testGetById() throws Exception {
		
		ResultActions response = mockMvc
			    .perform(get(Constantes.PAISES + Constantes.GET_BY_ID)
			    .param("id", "1")
			    .header("authorization", "Bearer " + token));
				
		PaisDTO q = getObjectFromJson(response.andReturn().getResponse().getContentAsString());
		assertNotNull(q);
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
	
	private PaisDTO getDTO(String nombre) {
		PaisDTO pais = new PaisDTO();
		pais.setNombre(nombre);
		return pais;
	}

	private String getJson(PaisDTO u) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson=ow.writeValueAsString(u);
	    return requestJson;
	}
	
	private PaisDTO getObjectFromJson(String s) throws JsonMappingException, JsonProcessingException {
	    ObjectReader jsonObjectReader = new ObjectMapper().readerFor(PaisDTO.class);
	    PaisDTO q = jsonObjectReader.readValue(s);
	    return q;
	}

}
