package com.fcpm.pronofutbol.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fcmp.pronofutbol.constant.Constantes;
import com.fcmp.pronofutbol.dtos.AutenticacionDTO;
import com.fcmp.pronofutbol.utils.I18nUtils;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class AuthenticationControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	@Test
	@Order(1) 
	void testGetToken() throws Exception {
		
		AutenticacionDTO a = getAutenticacion("administrador", "Administrador01");
		
		String requestJson = getJson(a);

		mockMvc
		    .perform(post("/autenticacion/generate-token")
			.contentType(APPLICATION_JSON_UTF8)
		    .content(requestJson))
		    //.andDo(print())
		    .andExpect(status().isOk())
		    .andExpect(content().string(notNullValue()));
	}
	
	@Test
	@Order(2) 
	void testLoginErroneo() throws Exception {
		
		AutenticacionDTO a = getAutenticacion("username", "badPassword");
		
		String requestJson = getJson(a);

		mockMvc
		    .perform(post("/autenticacion/generate-token")
			.contentType(APPLICATION_JSON_UTF8)
			.content(requestJson))
		    //.andDo(print())
		    .andExpect(status().isBadRequest())
		    .andExpect(result -> assertTrue(result.getResolvedException() instanceof AuthenticationException))
		    .andExpect(content().string(containsString(I18nUtils.getMensaje(Constantes.EXC_AUTH_ERRONEA))));
	}
	
	@Test
	@Order(3) 
	void testLoginMetodoNoSoportado() throws Exception {
		
		AutenticacionDTO a = getAutenticacion("username", "badPassword");
		
		String requestJson = getJson(a);

		mockMvc
		    .perform(get("/autenticacion/generate-token")
		    .contentType(APPLICATION_JSON_UTF8)
			.content(requestJson))
		    //.andDo(print())
		    .andExpect(status().isBadRequest())
		    .andExpect(result -> assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
		    .andExpect(content().string(containsString(I18nUtils.getMensaje(Constantes.EXC_METODO_NO_SOPORTADO))));

	}
	
	@Test
	@Order(4) 
	void testLoginNoUsername() throws Exception {
		
		AutenticacionDTO a = getAutenticacion(null, "password");
		
		String requestJson = getJson(a);

		mockMvc
		    .perform(post("/autenticacion/generate-token")
			.contentType(APPLICATION_JSON_UTF8)
			.content(requestJson))
		    //.andDo(print())
		    .andExpect(status().isBadRequest())
		    .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
		    .andExpect(content().string(containsString(I18nUtils.getMensaje(Constantes.VALIDATION_USERNAME_OBLIGATORIO))));

	}
	
	@Test
	@Order(5) 
	void testLoginNoPassword() throws Exception {
		
		AutenticacionDTO a = getAutenticacion("username", null);
		
		String requestJson = getJson(a);

		mockMvc
		    .perform(post("/autenticacion/generate-token")
			.contentType(APPLICATION_JSON_UTF8)
			.content(requestJson))
		    //.andDo(print())
		    .andExpect(status().isBadRequest())
		    .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
		    .andExpect(content().string(containsString(I18nUtils.getMensaje(Constantes.VALIDATION_PASSWORD_OBLIGATORIO))));
		
	}
	
	@Test
	@Order(6) 
	void testLoginNoLoginNoPassword() throws Exception {
		
		AutenticacionDTO a = getAutenticacion(null, null);
		
		String requestJson = getJson(a);

		mockMvc
		    .perform(post("/autenticacion/generate-token")
			.contentType(APPLICATION_JSON_UTF8)
			.content(requestJson))
		    //.andDo(print())
		    .andExpect(status().isBadRequest())
		    .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
		    .andExpect(content().string(containsString(I18nUtils.getMensaje(Constantes.VALIDATION_USERNAME_OBLIGATORIO))))
		    .andExpect(content().string(containsString(I18nUtils.getMensaje(Constantes.VALIDATION_PASSWORD_OBLIGATORIO))));

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
