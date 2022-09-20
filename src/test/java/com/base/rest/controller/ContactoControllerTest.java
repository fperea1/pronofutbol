package com.base.rest.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.base.rest.constant.Constantes;
import com.base.rest.dtos.ContactoDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class ContactoControllerTest {
	
	@Autowired
    MessageSource messageSource;

	@Autowired
	private MockMvc mockMvc;
	
	static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	@Test
	@Order(1) 
	// Devuelve error porque no tengo puestos las credenciales correctas. En caso de tenerlas habría que cambiar el resultado.
	void testSend() throws Exception {
		
		ContactoDTO c = getContacto("Asunto", "Consulta");
		
		String requestJson = getJson(c);
		
		mockMvc
		    .perform(post("/contacto/envioConsulta")
			.contentType(APPLICATION_JSON_UTF8)
		    .content(requestJson))
		    .andDo(print())
		    .andExpect(status().isBadRequest())
		    .andExpect(content().string(containsString(messageSource.getMessage(Constantes.EXC_CREDENCIALES_ERRONEAS, null, LocaleContextHolder.getLocale()))));	
	}
	
	@Test
	@Order(2) 
	void testSendAsuntoNull() throws Exception {
		
		ContactoDTO c = getContacto(null, "Consulta");
		
		String requestJson = getJson(c);
		
		mockMvc
		    .perform(post("/contacto/envioConsulta")
			.contentType(APPLICATION_JSON_UTF8)
		    .content(requestJson))
		    .andDo(print())
		    .andExpect(status().isBadRequest())
		    .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
		    .andExpect(content().string(containsString(messageSource.getMessage(Constantes.VALIDATION_ASUNTO_OBLIGATORIO, null, LocaleContextHolder.getLocale()))));	
	}
	
	@Test
	@Order(3) 
	void testSendConsultaNull() throws Exception {
		
		ContactoDTO c = getContacto("Asunto", null);
		
		String requestJson = getJson(c);
		
		mockMvc
		    .perform(post("/contacto/envioConsulta")
			.contentType(APPLICATION_JSON_UTF8)
		    .content(requestJson))
		    .andDo(print())
		    .andExpect(status().isBadRequest())
		    .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
		    .andExpect(content().string(containsString(messageSource.getMessage(Constantes.VALIDATION_CONSULTA_OBLIGATORIO, null, LocaleContextHolder.getLocale()))));	
	}
	
	private ContactoDTO getContacto(String asunto, String consulta) {
		ContactoDTO u = new ContactoDTO();
		u.setAsunto(asunto);
		u.setConsulta(consulta);
		return u;
	}

	private String getJson(ContactoDTO c) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson=ow.writeValueAsString(c);
		return requestJson;
	}

}
