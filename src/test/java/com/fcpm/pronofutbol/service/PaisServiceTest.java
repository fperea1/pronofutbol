/**
 * 
 */
package com.fcpm.pronofutbol.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import com.fcpm.pronofutbol.constant.Constantes;
import com.fcpm.pronofutbol.dtos.PaisDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.service.interfaces.PaisService;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
class PaisServiceTest {

	@Autowired
	private PaisService service;

	private String filtro = "{\"first\":0,\"rows\":10,\"sortOrder\":1,\"filters\":{},\"globalFilter\":null}";

	@Test
	@Order(1) 
	void testSaveOk() {
		PaisDTO pais = new PaisDTO();
		pais.setNombre("Nombre de pruebas");
		service.crear(pais);
		ResultTableDTO result = service.findByFilter(filtro, false);
		assertTrue(result == null || result.getList().size() >= 2);
	}
	
	@Test
	@Order(2) 
	void testFindByFilter() {
		ResultTableDTO result = service.findByFilter(filtro, false);
		assertTrue(result == null || result.getList().size() >= 1);
	}
	
	@Test
	@Order(3)
	void testSaveNullKo() {
		PaisDTO pais = new PaisDTO();
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.crear(pais) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertNotNull(messages);
		assertEquals(Constantes.VALIDATION_NOMBRE_OBLIGATORIO, messages.get(0));
	}
	
	@Test
	@Order(4)
	void testSaveNombreSizeMayorKo() {
		PaisDTO pais = new PaisDTO();
		pais.setNombre("Nombre de pruebas 12345678901234567890123456789012345678901234567890");
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.crear(pais) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertNotNull(messages);
		assertEquals(Constantes.VALIDATION_NOMBRE_PAIS_SIZE, messages.get(0));
		
		//messages.forEach(m -> System.out.println(m));
	}
	
	@Test
	@Order(5)
	void testSaveNombreSizeMenorKo() {
		PaisDTO pais = new PaisDTO();
		pais.setNombre("");
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.crear(pais) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertNotNull(messages);
		assertEquals(Constantes.VALIDATION_NOMBRE_PAIS_SIZE, messages.get(0));
	}
	
	@Test
	@Order(6)
	void testSaveKoNombreNoUnique() {
		PaisDTO pais = new PaisDTO();
		pais.setNombre("Nombre de pruebas");
		assertThrows(DataIntegrityViolationException.class, () -> service.crear(pais) );
	}
	
}
