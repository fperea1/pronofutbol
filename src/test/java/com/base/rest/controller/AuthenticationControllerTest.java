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
import org.springframework.security.core.AuthenticationException;
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
	public void testGetToken() throws Exception {

		mockMvc
		    .perform(post("/token/generate-token")
		    .param("username", "administrador")
		    .param("password", "Administrador01"))
		    //.andDo(print())
		    .andExpect(status().isOk())
		    .andExpect(content().string(notNullValue()));
	}
	
	@Test
	@Order(2) 
	public void testLoginErroneo() throws Exception {

		mockMvc
		    .perform(post("/token/generate-token")
		    .param("username", "username")
		    .param("password", "badPassword"))
		    //.andDo(print())
		    .andExpect(status().isBadRequest())
		    .andExpect(result -> assertTrue(result.getResolvedException() instanceof AuthenticationException))
		    .andExpect(content().string(containsString("Datos de login erroneos")));
	}
	
	@Test
	@Order(3) 
	public void testLoginMetodoNoSoportado() throws Exception {

		mockMvc
		    .perform(get("/token/generate-token")
		    .param("username", "username")
		    .param("password", "badPassword"))
		    //.andDo(print())
		    .andExpect(status().isBadRequest())
		    .andExpect(result -> assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
		    .andExpect(content().string(containsString("Método no soportado")));

	}
	
	@Test
	@Order(4) 
	public void testLoginNoUsername() throws Exception {

		mockMvc
		    .perform(post("/token/generate-token")
		    .param("password", "password"))
		    //.andDo(print())
		    .andExpect(status().isBadRequest())
		    .andExpect(result -> assertTrue(result.getResolvedException() instanceof MissingServletRequestParameterException))
		    .andExpect(content().string(containsString("Faltan parámetros en la petición")));

	}
	
	@Test
	@Order(5) 
	public void testLoginNoPassword() throws Exception {

		mockMvc
		    .perform(post("/token/generate-token")
		    .param("username", "username"))
		    //.andDo(print())
		    .andExpect(status().isBadRequest())
		    .andExpect(result -> assertTrue(result.getResolvedException() instanceof MissingServletRequestParameterException))
		    .andExpect(content().string(containsString("Faltan parámetros en la petición")));

	}
	
	@Test
	@Order(6) 
	public void testLoginNoLoginNoPassword() throws Exception {

		mockMvc
		    .perform(post("/token/generate-token"))
		    //.andDo(print())
		    .andExpect(status().isBadRequest())
		    .andExpect(result -> assertTrue(result.getResolvedException() instanceof MissingServletRequestParameterException))
		    .andExpect(content().string(containsString("Faltan parámetros en la petición")));

	}


}
