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
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.base.rest.entities.Rol;
import com.base.rest.entities.Usuario;
import com.base.rest.exceptions.EntityNoExistsException;
import com.base.rest.exceptions.PasswordLimitException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class UsuarioControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	private static String token;
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	@Test
	@Order(1) 
	public void testGetToken() throws Exception {

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
	public void testFindAll() throws Exception {
		
		mockMvc
		    .perform(get("/usuarios/findAll")
		    .header("Authorization", "Bearer " + token))
		    .andDo(print())
		    .andExpect(status().isOk())
		    .andExpect(jsonPath("$.length()").value(1))
		    .andExpect(jsonPath("$[?(@.email === 'administrador@ezentis.com')]").exists());
		
		//System.out.println(response.andReturn().getResponse().getContentAsString());
	}
	
	@Test
	@Order(3) 
	public void testSave() throws Exception {
		
		Usuario u = getUsuario("userTest", "nombre test", "passwordTest", "tes@ezentis");
		
		String requestJson = getJson(u);
		
		mockMvc
		    .perform(post("/usuarios/save")
		    .contentType(APPLICATION_JSON_UTF8)
		    .content(requestJson)
		    .header("Authorization", "Bearer " + token))
		    .andExpect(status().isOk())
		    .andExpect(content().string(containsString("Operación correcta")));	
	}
	
	@Test
	@Order(4) 
	public void testSaveKoUsernameNull() throws Exception {
		
		Usuario u = getUsuario(null, "nombre test", "passwordTest", "tes@ezentis");
		
		String requestJson = getJson(u);
		
		mockMvc
	    .perform(post("/usuarios/save")
	    .contentType(APPLICATION_JSON_UTF8)
	    .content(requestJson)
	    .header("Authorization", "Bearer " + token))
	    .andDo(print())
	    .andExpect(status().isConflict())
	    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ConstraintViolationException))
	    .andExpect(content().string(containsString("Username. Campo obligatorio")));	
	}
	
	@Test
	@Order(5) 
	public void testSaveKoUsernameMinSize() throws Exception {
		
		Usuario u = getUsuario("u", "nombre test", "passwordTest", "tes@ezentis");
		
		String requestJson = getJson(u);
		
		mockMvc
	    .perform(post("/usuarios/save")
	    .contentType(APPLICATION_JSON_UTF8)
	    .content(requestJson)
	    .header("Authorization", "Bearer " + token))
	    .andDo(print())
	    .andExpect(status().isConflict())
	    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ConstraintViolationException))
	    .andExpect(content().string(containsString("Username debe tener entre 5 y 50 caracteres")));	
	}
	
	@Test
	@Order(6) 
	public void testSaveKoUsernameMaxSize() throws Exception {
		
		Usuario u = getUsuario("abcdefghijkmnopqrstuvwxyz12345678901234567890123456", "nombre test", "passwordTest", "tes@ezentis");
		
		String requestJson = getJson(u);
		
		mockMvc
	    .perform(post("/usuarios/save")
	    .contentType(APPLICATION_JSON_UTF8)
	    .content(requestJson)
	    .header("Authorization", "Bearer " + token))
	    .andDo(print())
	    .andExpect(status().isConflict())
	    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ConstraintViolationException))
	    .andExpect(content().string(containsString("Username debe tener entre 5 y 50 caracteres")));	
	}
	
	@Test
	@Order(7) 
	public void testSaveKoPasswordNull() throws Exception {
		
		Usuario u = getUsuario("username", "nombre test", null, "tes@ezentis");
		
		String requestJson = getJson(u);
		
		mockMvc
	    .perform(post("/usuarios/save")
	    .contentType(APPLICATION_JSON_UTF8)
	    .content(requestJson)
	    .header("Authorization", "Bearer " + token))
	    .andDo(print())
	    .andExpect(status().isConflict())
	    .andExpect(result -> assertTrue(result.getResolvedException() instanceof PasswordLimitException))
	    .andExpect(content().string(containsString("Password debe tener entre 10 y 100 caracteres")));	
	}
	
	@Test
	@Order(8) 
	public void testSaveKoPasswordMinSize() throws Exception {
		
		Usuario u = getUsuario("username", "nombre test", "abc", "tes@ezentis");
		
		String requestJson = getJson(u);
		
		mockMvc
	    .perform(post("/usuarios/save")
	    .contentType(APPLICATION_JSON_UTF8)
	    .content(requestJson)
	    .header("Authorization", "Bearer " + token))
	    .andDo(print())
	    .andExpect(status().isConflict())
	    .andExpect(result -> assertTrue(result.getResolvedException() instanceof PasswordLimitException))
	    .andExpect(content().string(containsString("Password debe tener entre 10 y 100 caracteres")));	
	}
	
	@Test
	@Order(9) 
	public void testSaveKoPasswordMaxSize() throws Exception {
		
		Usuario u = getUsuario("username", "nombre test", 
				"01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890", "tes@ezentis");
		
		String requestJson = getJson(u);
		
		mockMvc
	    .perform(post("/usuarios/save")
	    .contentType(APPLICATION_JSON_UTF8)
	    .content(requestJson)
	    .header("Authorization", "Bearer " + token))
	    .andDo(print())
	    .andExpect(status().isConflict())
	    .andExpect(result -> assertTrue(result.getResolvedException() instanceof PasswordLimitException))
	    .andExpect(content().string(containsString("Password debe tener entre 10 y 100 caracteres")));	
	}
	
	@Test
	@Order(10) 
	public void testSaveKoNombreNull() throws Exception {
		
		Usuario u = getUsuario("username", null, "passwordTest", "tes@ezentis");
		
		String requestJson = getJson(u);
		
		mockMvc
	    .perform(post("/usuarios/save")
	    .contentType(APPLICATION_JSON_UTF8)
	    .content(requestJson)
	    .header("Authorization", "Bearer " + token))
	    .andDo(print())
	    .andExpect(status().isConflict())
	    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ConstraintViolationException))
	    .andExpect(content().string(containsString("Nombre. Campo obligatorio")));	
	}
	
	@Test
	@Order(11) 
	public void testSaveKoNombreMinSize() throws Exception {
		
		Usuario u = getUsuario("username", "", "passwordTest", "tes@ezentis");
		
		String requestJson = getJson(u);
		
		mockMvc
	    .perform(post("/usuarios/save")
	    .contentType(APPLICATION_JSON_UTF8)
	    .content(requestJson)
	    .header("Authorization", "Bearer " + token))
	    .andDo(print())
	    .andExpect(status().isConflict())
	    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ConstraintViolationException))
	    .andExpect(content().string(containsString("Nombre debe tener entre 1 y 50 caracteres")));	
	}
	
	@Test
	@Order(12) 
	public void testSaveKoNombreMaxSize() throws Exception {
		
		Usuario u = getUsuario("username", "012345678901234567890123456789012345678901234567890", "passwordTest", "tes@ezentis");
		
		String requestJson = getJson(u);
		
		mockMvc
	    .perform(post("/usuarios/save")
	    .contentType(APPLICATION_JSON_UTF8)
	    .content(requestJson)
	    .header("Authorization", "Bearer " + token))
	    .andDo(print())
	    .andExpect(status().isConflict())
	    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ConstraintViolationException))
	    .andExpect(content().string(containsString("Nombre debe tener entre 1 y 50 caracteres")));	
	}
	
	@Test
	@Order(13) 
	public void testSaveKoEmailNull() throws Exception {
		
		Usuario u = getUsuario("username", "nombre", "passwordTest", null);
		
		String requestJson = getJson(u);
		
		mockMvc
	    .perform(post("/usuarios/save")
	    .contentType(APPLICATION_JSON_UTF8)
	    .content(requestJson)
	    .header("Authorization", "Bearer " + token))
	    .andDo(print())
	    .andExpect(status().isConflict())
	    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ConstraintViolationException))
	    .andExpect(content().string(containsString("Email. Campo obligatorio")));	
	}
	
	@Test
	@Order(14) 
	public void testSaveKoEmailMalFormado() throws Exception {
		
		Usuario u = getUsuario("username", "nombre", "passwordTest", "email");
		
		String requestJson = getJson(u);
		
		mockMvc
	    .perform(post("/usuarios/save")
	    .contentType(APPLICATION_JSON_UTF8)
	    .content(requestJson)
	    .header("Authorization", "Bearer " + token))
	    .andDo(print())
	    .andExpect(status().isConflict())
	    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ConstraintViolationException))
	    .andExpect(content().string(containsString("Email mal formado")));	
	}
	
	@Test
	@Order(15) 
	public void testUpdateOk() throws Exception {
		
		ResultActions response = mockMvc
	    .perform(get("/usuarios/find")
	    .param("id", "2")
	    .header("Authorization", "Bearer " + token));
		
		Usuario u = getObjectFromJson(response.andReturn().getResponse().getContentAsString());
		u.setActivo(false);
		
		String requestJson = getJson(u);
		
		mockMvc
		    .perform(put("/usuarios/update")
		    .contentType(APPLICATION_JSON_UTF8)
		    .content(requestJson)
		    .header("Authorization", "Bearer " + token))
		    .andExpect(status().isOk())
		    .andExpect(content().string(containsString("Operación correcta")));	
	}
	
	@Test
	@Order(16) 
	public void testUpdateUserNoExists() throws Exception {
		
		Usuario u = getUsuario("userTestNoUpdate", "nombre test", null, "tes@ezentis");
		
		String requestJson = getJson(u);
		
		mockMvc
		    .perform(put("/usuarios/update")
		    .contentType(APPLICATION_JSON_UTF8)
		    .content(requestJson)
		    .header("Authorization", "Bearer " + token))
		    .andExpect(status().isConflict())
		    .andExpect(content().string(containsString("Activo. Campo obligatorio")))
		    .andExpect(content().string(containsString("Fecha de alta. Campo obligatorio")));	
	}
	

	@Test
	@Order(17) 
	public void testMetodoNoExiste() throws Exception {
		
		Usuario u = getUsuario("userTestNoUpdate", "nombre test", null, "tes@ezentis");
		
		String requestJson = getJson(u);
		
		mockMvc
		    .perform(put("/usuarios/noExisto")
		    .contentType(APPLICATION_JSON_UTF8)
		    .content(requestJson)
		    .header("Authorization", "Bearer " + token))
		    .andExpect(status().isNotFound());
	}
	

	@Test
	@Order(18) 
	public void testFindByIdOk() throws Exception {
		
		mockMvc
		    .perform(get("/usuarios/find")
		    .param("id", "1")
		    .header("Authorization", "Bearer " + token))
		    .andDo(print())
		    .andExpect(status().isOk())
		    .andExpect(jsonPath("$[?(@.email === 'administrador@ezentis.com')]").exists());
	}
	
	@Test
	@Order(19) 
	public void testFindByIdUserNoExists() throws Exception {
		
		mockMvc
	    .perform(get("/usuarios/find")
	    .param("id", "5")
	    .header("Authorization", "Bearer " + token))
	    .andExpect(status().isBadRequest())
	    .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNoExistsException))
	    .andExpect(content().string(containsString("No existe la entidad")));
	}
	
	@Test
	@Order(20) 
	public void testDeleteById() throws Exception {
		
		Usuario u = getUsuario("delete", "delete test", "passwordTest", "delete@ezentis");
		
		String requestJson = getJson(u);
		
		mockMvc
		    .perform(post("/usuarios/save")
		    .contentType(APPLICATION_JSON_UTF8)
		    .content(requestJson)
		    .header("Authorization", "Bearer " + token))
		    .andExpect(status().isOk())
		    .andExpect(content().string(containsString("Operación correcta")));	
		
		mockMvc
	    .perform(delete("/usuarios/delete")
	    .param("id", "3")
	    .header("Authorization", "Bearer " + token))
	    .andExpect(status().isOk());
	}
	
	@Test
	@Order(21) 
	public void testDeleteByIdUserNoExists() throws Exception {
		
		mockMvc
		    .perform(delete("/usuarios/delete")
		    .param("id", "30")
		    .header("Authorization", "Bearer " + token))
		    .andExpect(status().isBadRequest())
		    .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNoExistsException))
		    .andExpect(content().string(containsString("No existe la entidad")));
	}
	
	@Test
	@Order(22) 
	public void testDeactivateById() throws Exception {
		
		mockMvc
		    .perform(put("/usuarios/deactivate")
		    .param("id", "2")
		    .header("Authorization", "Bearer " + token))
		    .andExpect(status().isOk())
		    .andExpect(content().string(containsString("Operación correcta")));
	}
	
	@Test
	@Order(23) 
	public void testDeactivateByIdUserNoExists() throws Exception {
		
		mockMvc
		    .perform(put("/usuarios/deactivate")
		    .param("id", "20")
		    .header("Authorization", "Bearer " + token))
		    .andExpect(status().isBadRequest())
		    .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNoExistsException))
		    .andExpect(content().string(containsString("No existe la entidad")));
	}
	
	@Test
	@Order(24) 
	public void testActivateById() throws Exception {
		
		mockMvc
		    .perform(put("/usuarios/activate")
		    .param("id", "2")
		    .header("Authorization", "Bearer " + token))
		    .andExpect(status().isOk())
		    .andExpect(content().string(containsString("Operación correcta")));
	}
	
	@Test
	@Order(25) 
	public void testActivateByIdUserNoExists() throws Exception {
		
		mockMvc
		    .perform(put("/usuarios/activate")
		    .param("id", "20")
		    .header("Authorization", "Bearer " + token))
		    .andExpect(status().isBadRequest())
		    .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNoExistsException))
		    .andExpect(content().string(containsString("No existe la entidad")));
	}
	
	@Test
	@Order(26) 
	public void testPeticionSinToken() throws Exception {
		
		mockMvc
		    .perform(put("/usuarios/activate")
		    .param("id", "2"))
		    .andDo(print())
		    .andExpect(status().isUnauthorized())
		    .andExpect(status().reason("No autorizado"));
	}
	
	@Test
	@Order(27) 
	public void testCambioPasswordUserOk() throws Exception {
		
		mockMvc
	    .perform(put("/usuarios/cambioPassword")
	    .param("id", "2")
	    .param("oldPassword", "passwordTest")
	    .param("newPassword", "passwordTest2")
	    .header("Authorization", "Bearer " + token))
	    .andExpect(status().isOk())
	    .andExpect(content().string(containsString("Operación correcta")));	
	}
	
	@Test
	@Order(28) 
	public void testCambioPasswordUserKo() throws Exception {
		
		mockMvc
	    .perform(put("/usuarios/cambioPassword")
	    .param("id", "2")
	    .param("oldPassword", "erronea")
	    .param("newPassword", "passwordTest")
	    .header("Authorization", "Bearer " + token))
	    .andExpect(status().isBadRequest())
	    .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadCredentialsException))
	    .andExpect(content().string(containsString("Credenciales erroneas")));
	}
	
	@Test
	@Order(29) 
	public void testCambioPasswordAdminOk() throws Exception {
		
		mockMvc
	    .perform(put("/usuarios/cambioPasswordAdmin")
	    .param("id", "2")
	    .param("oldPassword", "passwordTest2")
	    .param("newPassword", "passwordTest")
	    .header("Authorization", "Bearer " + token))
	    .andExpect(status().isOk())
	    .andExpect(content().string(containsString("Operación correcta")));	
	}
	
	@Test
	@Order(30) 
	public void testCambioPasswordAdminKo() throws Exception {
		
		mockMvc
	    .perform(put("/usuarios/cambioPasswordAdmin")
	    .param("id", "2000")
	    .param("newPassword", "bbbb")
	    .header("Authorization", "Bearer " + token))
	    .andExpect(status().isBadRequest())
	    .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNoExistsException))
	    .andExpect(content().string(containsString("No existe la entidad")));
	}

	private Usuario getUsuario(String username, String nombre, String password, String email) {
		Usuario u = new Usuario();
		u.setUsername(username);
		u.setNombre(nombre);
		u.setPassword(password);
		u.setEmail(email);
		Rol rol = new Rol();
		rol.setId(1);
		Set<Rol> roles = new HashSet<Rol>();
		roles.add(rol);
		u.setRoles(roles);
		return u;
	}

	private String getJson(Usuario u) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson=ow.writeValueAsString(u);
		return requestJson;
	}
	
	private Usuario getObjectFromJson(String s) throws JsonMappingException, JsonProcessingException {
	    ObjectReader jsonObjectReader = new ObjectMapper().readerFor(Usuario.class);
	    Usuario u = jsonObjectReader.readValue(s);
	    return u;
	}
}
