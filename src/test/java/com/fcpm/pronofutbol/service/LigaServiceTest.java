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

import com.fcpm.pronofutbol.constant.Constantes;
import com.fcpm.pronofutbol.dtos.LigaDTO;
import com.fcpm.pronofutbol.dtos.PaisDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.service.interfaces.LigaService;
import com.fcpm.pronofutbol.service.interfaces.PaisService;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class LigaServiceTest {

	@Autowired
	private LigaService service;

	@Autowired
	private PaisService paisService;

	private String filtro = "{\"first\":0,\"rows\":10,\"sortOrder\":1,\"filters\":{},\"globalFilter\":null}";
	
	@Test
	@Order(1)
	void testSaveOk() {
		LigaDTO liga = new LigaDTO();
		PaisDTO pais = paisService.getById(1);
		liga.setNombre("Liga de pruebas");
		liga.setPais(pais);
		service.save(liga);
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
	void testSaveTodoNullKo() {
		LigaDTO liga = new LigaDTO();
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(liga) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertTrue(messages != null);
		
		//messages.forEach(m -> System.out.println(m));
	}
	
	@Test
	@Order(4)
	void testSavePaisNullKo() {
		LigaDTO liga = new LigaDTO();
		liga.setNombre("Liga de pruebas");
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(liga) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertTrue(messages != null);
		assertEquals(messages.get(0), Constantes.VALIDATION_PAIS_OBLIGATORIO);
	}
	
	@Test
	@Order(5)
	void testSaveNombreSizeMayorKo() {
		LigaDTO liga = new LigaDTO();
		liga.setNombre("Liga de pruebas 1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
		PaisDTO pais = paisService.getById(1);
		liga.setPais(pais);
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(liga) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertTrue(messages != null);
		assertEquals(messages.get(0), Constantes.VALIDATION_NOMBRE_LIGA_SIZE);
	}
	
	@Test
	@Order(6) 
	void testSaveNombreSizeMenorKo() {
		LigaDTO liga = new LigaDTO();
		liga.setNombre("");
		PaisDTO pais = paisService.getById(1);
		liga.setPais(pais);
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(liga) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertTrue(messages != null);
		assertEquals(messages.get(0), Constantes.VALIDATION_NOMBRE_LIGA_SIZE);
	}

	@Test
	@Order(7) 
	void testSaveNombreSizeNullKo() {
		LigaDTO liga = new LigaDTO();
		liga.setNombre(null);
		PaisDTO pais = paisService.getById(1);
		liga.setPais(pais);
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(liga) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertTrue(messages != null);
		assertEquals(messages.get(0), Constantes.VALIDATION_NOMBRE_OBLIGATORIO);
	}
	
	@Test
	@Order(8)
	void testSaveKoNombreNoUnique() {
		LigaDTO liga = new LigaDTO();
		PaisDTO pais = paisService.getById(1);
		liga.setNombre("Liga de pruebas");
		liga.setPais(pais);
		DataIntegrityViolationException ex = assertThrows(DataIntegrityViolationException.class, () -> service.save(liga) );
		assertNotNull(ex);
	}
}
