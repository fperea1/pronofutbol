package com.base.rest.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class AuthenticationControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@Order(1) 
	void testGetToken() throws Exception {

		mockMvc
		    .perform(post("/autenticacion/generate-token")
		    .param("username", "administrador")
		    .param("password", "Administrador01"))
		    //.andDo(print())
		    .andExpect(status().isOk())
		    .andExpect(content().string(notNullValue()));
	}
	
	@Test
	@Order(2) 
	void testLoginErroneo() throws Exception {

		mockMvc
		    .perform(post("/autenticacion/generate-token")
		    .param("username", "username")
		    .param("password", "badPassword"))
		    //.andDo(print())
		    .andExpect(status().isBadRequest())
		    .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadCredentialsException))
		    .andExpect(content().string(containsString("Credenciales erroneas")));
	}
	
	@Test
	@Order(3) 
	void testLoginMetodoNoSoportado() throws Exception {

		mockMvc
		    .perform(get("/autenticacion/generate-token")
		    .param("username", "username")
		    .param("password", "badPassword"))
		    //.andDo(print())
		    .andExpect(status().isBadRequest())
		    .andExpect(result -> assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
		    .andExpect(content().string(containsString("Método no soportado")));

	}
	
	@Test
	@Order(4) 
	void testLoginNoUsername() throws Exception {

		mockMvc
		    .perform(post("/autenticacion/generate-token")
		    .param("password", "password"))
		    //.andDo(print())
		    .andExpect(status().isBadRequest())
		    .andExpect(result -> assertTrue(result.getResolvedException() instanceof MissingServletRequestParameterException))
		    .andExpect(content().string(containsString("Faltan parámetros en la petición")));

	}
	
	@Test
	@Order(5) 
	void testLoginNoPassword() throws Exception {

		mockMvc
		    .perform(post("/autenticacion/generate-token")
		    .param("username", "username"))
		    //.andDo(print())
		    .andExpect(status().isBadRequest())
		    .andExpect(result -> assertTrue(result.getResolvedException() instanceof MissingServletRequestParameterException))
		    .andExpect(content().string(containsString("Faltan parámetros en la petición")));

	}
	
	@Test
	@Order(6) 
	void testLoginNoLoginNoPassword() throws Exception {

		mockMvc
		    .perform(post("/autenticacion/generate-token"))
		    //.andDo(print())
		    .andExpect(status().isBadRequest())
		    .andExpect(result -> assertTrue(result.getResolvedException() instanceof MissingServletRequestParameterException))
		    .andExpect(content().string(containsString("Faltan parámetros en la petición")));

	}


}
