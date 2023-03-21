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
import com.fcpm.pronofutbol.dtos.JornadaDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.service.interfaces.JornadaService;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
class JornadaServiceTest {

	@Autowired
	private JornadaService service;

	private String filtro = "{\"first\":0,\"rows\":10,\"sortOrder\":1,\"filters\":{},\"globalFilter\":null}";

	@Test
	@Order(1) 
	void testSaveOk() {
		JornadaDTO jornada = new JornadaDTO();
		jornada.setNombre("Nombre de pruebas");
		service.crear(jornada);
		ResultTableDTO result = service.findByFilter(filtro, false);
		assertTrue(result == null || result.getList().size() >= 1);
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
		JornadaDTO jornada = new JornadaDTO();
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.crear(jornada) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertNotNull(messages);
		assertEquals(Constantes.VALIDATION_NOMBRE_OBLIGATORIO, messages.get(0));
	}
	
	@Test
	@Order(4)
	void testSaveNombreSizeMayorKo() {
		JornadaDTO jornada = new JornadaDTO();
		jornada.setNombre("Liga de pruebas 12345678901234567890123456789012345678901234567890");
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.crear(jornada) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertNotNull(messages);
		assertEquals(Constantes.VALIDATION_NOMBRE_JORNADA_SIZE, messages.get(0));
		
		//messages.forEach(m -> System.out.println(m));
	}
	
	@Test
	@Order(5)
	void testSaveNombreSizeMenorKo() {
		JornadaDTO jornada = new JornadaDTO();
		jornada.setNombre("");
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.crear(jornada) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertNotNull(messages);
		assertEquals(Constantes.VALIDATION_NOMBRE_JORNADA_SIZE, messages.get(0));
	}
	
	@Test
	@Order(6)
	void testSaveKoNombreNoUnique() {
		JornadaDTO jornada = new JornadaDTO();
		jornada.setNombre("Nombre de pruebas");
		assertThrows(DataIntegrityViolationException.class, () -> service.crear(jornada) );
	}
	
}
