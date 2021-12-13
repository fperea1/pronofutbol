package com.base.rest.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import javax.validation.ConstraintViolationException;

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

import com.base.rest.dtos.ConfiguracionDTO;
import com.base.rest.exceptions.EntityNoExistsException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class ConfiguracionControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	private static String token;
	
	static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	@Test
	@Order(1) 
	void testGetToken() throws Exception {

		ResultActions response = mockMvc
		    .perform(post("/token/generate-token")
		    .param("username", "administrador")
		    .param("password", "Administrador01"))
		    .andExpect(status().isOk());
		
		token = response.andReturn().getResponse().getContentAsString();

		//System.out.println(response.andReturn().getResponse().getContentAsString());
	}
	
	@Test
	@Order(2) 
	void testSave() throws Exception {
		
		ConfiguracionDTO c = getConfiguracion("Prueba", "1234");
		
		String requestJson = getJson(c);
		
		mockMvc
		    .perform(post("/configuracion/save")
		    .contentType(APPLICATION_JSON_UTF8)
		    .content(requestJson)
		    .header("Authorization", "Bearer " + token))
		    .andExpect(status().isOk())
		    .andExpect(content().string(containsString("Operación correcta")));	
	}
	
	@Test
	@Order(3) 
	void testFindAll() throws Exception {
		
		mockMvc
		    .perform(get("/configuracion/findAll")
		    .header("Authorization", "Bearer " + token))
		    .andDo(print())
		    .andExpect(status().isOk())
		    .andExpect(jsonPath("$.length()").value(1))
		    .andExpect(jsonPath("$[?(@.nombre === 'Prueba')]").exists());
		
		//System.out.println(response.andReturn().getResponse().getContentAsString());
	}
	
	@Test
	@Order(4) 
	void testSaveKoNombreNull() throws Exception {
		
		ConfiguracionDTO c = getConfiguracion(null, "1234");
		
		String requestJson = getJson(c);
		
		mockMvc
	    .perform(post("/configuracion/save")
	    .contentType(APPLICATION_JSON_UTF8)
	    .content(requestJson)
	    .header("Authorization", "Bearer " + token))
	    .andDo(print())
	    .andExpect(status().isConflict())
	    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ConstraintViolationException))
	    .andExpect(content().string(containsString("Nombre. Campo obligatorio")));	
	}
	
	@Test
	@Order(5) 
	void testSaveKoValorNull() throws Exception {
		
		ConfiguracionDTO c = getConfiguracion("Prueba", null);
		
		String requestJson = getJson(c);
		
		mockMvc
	    .perform(post("/configuracion/save")
	    .contentType(APPLICATION_JSON_UTF8)
	    .content(requestJson)
	    .header("Authorization", "Bearer " + token))
	    .andDo(print())
	    .andExpect(status().isConflict())
	    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ConstraintViolationException))
	    .andExpect(content().string(containsString("Valor. Campo obligatorio")));	
	}
	
	@Test
	@Order(6) 
	void testSaveKoNombreMinSize() throws Exception {
		
		ConfiguracionDTO c = getConfiguracion("", "1234");
		
		String requestJson = getJson(c);
		
		mockMvc
	    .perform(post("/configuracion/save")
	    .contentType(APPLICATION_JSON_UTF8)
	    .content(requestJson)
	    .header("Authorization", "Bearer " + token))
	    .andDo(print())
	    .andExpect(status().isConflict())
	    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ConstraintViolationException))
	    .andExpect(content().string(containsString("Nombre debe tener entre 1 y 50 caracteres")));	
	}
	
	@Test
	@Order(7) 
	void testSaveKoNombreMaxSize() throws Exception {
		
		ConfiguracionDTO c = getConfiguracion("Nombre01234567890123456789012345678901234567890123456789", "1234");
		
		String requestJson = getJson(c);
		
		mockMvc
	    .perform(post("/configuracion/save")
	    .contentType(APPLICATION_JSON_UTF8)
	    .content(requestJson)
	    .header("Authorization", "Bearer " + token))
	    .andDo(print())
	    .andExpect(status().isConflict())
	    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ConstraintViolationException))
	    .andExpect(content().string(containsString("Nombre debe tener entre 1 y 50 caracteres")));	
	}
	
	@Test
	@Order(8) 
	void testSaveKoValorMinSize() throws Exception {
		
		ConfiguracionDTO c = getConfiguracion("Prueba", "");
		
		String requestJson = getJson(c);
		
		mockMvc
	    .perform(post("/configuracion/save")
	    .contentType(APPLICATION_JSON_UTF8)
	    .content(requestJson)
	    .header("Authorization", "Bearer " + token))
	    .andDo(print())
	    .andExpect(status().isConflict())
	    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ConstraintViolationException))
	    .andExpect(content().string(containsString("Valor debe tener entre 1 y 500 caracteres")));	
	}
	
	@Test
	@Order(9) 
	void testUpdateOk() throws Exception {
		
		ResultActions response = mockMvc
	    .perform(get("/configuracion/find")
	    .param("id", "1")
	    .header("Authorization", "Bearer " + token));
		
		ConfiguracionDTO c = getObjectFromJson(response.andReturn().getResponse().getContentAsString());
		c.setValor("4567");
		
		String requestJson = getJson(c);
		
		mockMvc
		    .perform(put("/configuracion/update")
		    .contentType(APPLICATION_JSON_UTF8)
		    .content(requestJson)
		    .header("Authorization", "Bearer " + token))
		    .andExpect(status().isOk())
		    .andExpect(content().string(containsString("Operación correcta")));	
	}
	
	@Test
	@Order(10) 
	void testDeleteById() throws Exception {
		
		ConfiguracionDTO c = getConfiguracion("Borrar", "1234");
		
		String requestJson = getJson(c);
		
		mockMvc
		    .perform(post("/configuracion/save")
		    .contentType(APPLICATION_JSON_UTF8)
		    .content(requestJson)
		    .header("Authorization", "Bearer " + token))
		    .andExpect(status().isOk())
		    .andExpect(content().string(containsString("Operación correcta")));	
		
		mockMvc
	    .perform(delete("/configuracion/delete")
	    .param("id", "2")
	    .header("Authorization", "Bearer " + token))
	    .andExpect(status().isOk());
	}
	
	@Test
	@Order(11) 
	void testDeleteByIdUserNoExists() throws Exception {
		
		mockMvc
		    .perform(delete("/configuracion/delete")
		    .param("id", "30")
		    .header("Authorization", "Bearer " + token))
		    .andExpect(status().isBadRequest())
		    .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNoExistsException))
		    .andExpect(content().string(containsString("No existe la entidad")));
	}

	private ConfiguracionDTO getConfiguracion(String nombre, String valor) {
		ConfiguracionDTO c = new ConfiguracionDTO();
		c.setNombre(nombre);
		c.setValor(valor);
		return c;
	}

	private String getJson(ConfiguracionDTO c) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson=ow.writeValueAsString(c);
		return requestJson;
	}
	
	private ConfiguracionDTO getObjectFromJson(String s) throws JsonMappingException, JsonProcessingException {
	    ObjectReader jsonObjectReader = new ObjectMapper().readerFor(ConfiguracionDTO.class);
	    ConfiguracionDTO c = jsonObjectReader.readValue(s);
	    return c;
	}

}
