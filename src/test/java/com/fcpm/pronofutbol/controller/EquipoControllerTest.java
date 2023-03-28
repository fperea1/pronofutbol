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
import com.fcpm.pronofutbol.dtos.EquipoDTO;
import com.fcpm.pronofutbol.dtos.LigaDTO;
import com.fcpm.pronofutbol.enums.ResultadoEnum;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class EquipoControllerTest {

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

		//System.out.println(response.andReturn().getResponse().getContentAsString());
	}
	
	@Test
	@Order(2) 
	void testSave() throws Exception {
		
		EquipoDTO u = getDto(1, "nombre test");
		
		String requestJson = getJson(u);
		
		mockMvc
		    .perform(post(Constantes.EQUIPOS + Constantes.SAVE)
		    .contentType(APPLICATION_JSON_UTF8)
		    .content(requestJson)
		    .header("authorization", "Bearer " + token))
		    .andExpect(status().isOk());	
	}
	
	@Test
	@Order(3) 
	void testFindByFilter() throws Exception {
		
		String filtro = "{\"first\":0,\"rows\":10,\"sortOrder\":1,\"filters\":{},\"globalFilter\":null}";
		mockMvc
		    .perform(get(Constantes.EQUIPOS + Constantes.FIND_BY_FILTER)
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
	@Order(4) 
	void testGetById() throws Exception {
		
		ResultActions response = mockMvc
			    .perform(get(Constantes.EQUIPOS + Constantes.GET_BY_ID)
			    .param("id", "1")
			    .header("authorization", "Bearer " + token));
				
		EquipoDTO q = getObjectFromJson(response.andReturn().getResponse().getContentAsString());
		assertNotNull(q);
	}
	
	@Test
	@Order(5) 
	void testFindForSelect() throws Exception {
		
		mockMvc
		    .perform(get(Constantes.EQUIPOS + Constantes.FIND_FOR_SELECT)
		    .header("authorization", "Bearer " + token))
		    .andDo(print())
		    .andExpect(status().isOk())
		    .andExpect(jsonPath("$.length()").isNotEmpty())
		    .andExpect(jsonPath("$.[0].nombre").value("nombre test"));
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
	
	private EquipoDTO getDto(Integer numero, String nombre) {
		EquipoDTO dto = new EquipoDTO();
		dto.setNombre(nombre);
		dto.setEmpatadosLocal(0);
		dto.setEmpatadosVisitante(1);
		dto.setGanadosVisitante(2);
		dto.setGanadosLocal(2);
		dto.setPerdidosLocal(1);
		dto.setPerdidosVisitante(4);
		dto.setPuntos(23);
		dto.setResPenultimoPartido(ResultadoEnum.DOS);
		dto.setResUltimoPartido(ResultadoEnum.DOS);
		LigaDTO l = new LigaDTO();
		l.setId(numero);
		dto.setLiga(l);
		return dto;
	}

	private String getJson(EquipoDTO u) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson=ow.writeValueAsString(u);
	    return requestJson;
	}
	
	private EquipoDTO getObjectFromJson(String s) throws JsonMappingException, JsonProcessingException {
	    ObjectReader jsonObjectReader = new ObjectMapper().readerFor(EquipoDTO.class);
	    EquipoDTO a = jsonObjectReader.readValue(s);
	    return a;
	}
}