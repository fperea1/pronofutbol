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
import com.fcpm.pronofutbol.dtos.ArbitroDTO;
import com.fcpm.pronofutbol.dtos.LigaDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.service.interfaces.ArbitroService;
import com.fcpm.pronofutbol.service.interfaces.LigaService;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class ArbitroServiceTest {

	@Autowired
	private ArbitroService service;

	@Autowired
	private LigaService ligaService;
	
	@Test
	@Order(1) 
	void testSaveOk() {
		ArbitroDTO arbitro = new ArbitroDTO();
		arbitro.setNombre("Nombre test");
		arbitro.setGanadosLocal(0);
		arbitro.setEmpatados(1);
		arbitro.setGanadosVisitante(2);
		LigaDTO liga = ligaService.getById(1);
		arbitro.setLiga(liga);
		service.save(arbitro);
		arbitro = service.getById(1);
		assertNotNull(arbitro);
	}

	@Test
	@Order(2)
	void testFindByFilter() {
		String filtro = "{\"first\":0,\"rows\":10,\"sortOrder\":1,\"filters\":{},\"globalFilter\":null}";
		ResultTableDTO result = service.findByFilter(filtro, false);
		assertTrue(result == null || result.getList().size() >= 1);
	}
	
	@Test
	@Order(3)
	void testSaveKoGanadosLocalNull() {
		ArbitroDTO arbitro = new ArbitroDTO();
		arbitro.setNombre("Nombre test");
		arbitro.setGanadosLocal(null);
		arbitro.setEmpatados(1);
		arbitro.setGanadosVisitante(2);
		LigaDTO liga = ligaService.getById(1);
		arbitro.setLiga(liga);
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(arbitro) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertTrue(messages != null);
		assertEquals(Constantes.VALIDATION_GANADOS_LOCAL_OBLIGATORIO, messages.get(0));
	}
	
	@Test
	@Order(4)
	void testSaveKoLigaNull() {
		ArbitroDTO arbitro = new ArbitroDTO();
		arbitro.setNombre("Nombre test");
		arbitro.setGanadosLocal(0);
		arbitro.setEmpatados(1);
		arbitro.setGanadosVisitante(2);
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(arbitro) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertTrue(messages != null);
		assertEquals(Constantes.VALIDATION_LIGA_OBLIGATORIO, messages.get(0));
	}
	
	@Test
	@Order(5)
	void testSaveKoEmpatadosNull() {
		ArbitroDTO arbitro = new ArbitroDTO();
		arbitro.setNombre("Nombre test");
		arbitro.setGanadosLocal(0);
		arbitro.setEmpatados(null);
		arbitro.setGanadosVisitante(2);
		LigaDTO liga = ligaService.getById(1);
		arbitro.setLiga(liga);
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(arbitro) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertTrue(messages != null);
		assertEquals(Constantes.VALIDATION_EMPATADOS_OBLIGATORIO, messages.get(0));
	}
	
	@Test
	@Order(6)
	void testSaveKoGanadosVisitanteNull() {
		ArbitroDTO arbitro = new ArbitroDTO();
		arbitro.setNombre("Nombre test");
		arbitro.setGanadosLocal(0);
		arbitro.setEmpatados(1);
		arbitro.setGanadosVisitante(null);
		LigaDTO liga = ligaService.getById(1);
		arbitro.setLiga(liga);
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(arbitro) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertTrue(messages != null);
		assertEquals(Constantes.VALIDATION_GANADOS_VISITANTE_OBLIGATORIO, messages.get(0));
	}
	
	@Test
	@Order(7)
	void testSaveKoNombreNull() {
		ArbitroDTO arbitro = new ArbitroDTO();
		arbitro.setNombre(null);
		arbitro.setGanadosLocal(0);
		arbitro.setEmpatados(1);
		arbitro.setGanadosVisitante(2);
		LigaDTO liga = ligaService.getById(1);
		arbitro.setLiga(liga);
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(arbitro) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertTrue(messages != null);
		assertEquals(Constantes.VALIDATION_NOMBRE_OBLIGATORIO, messages.get(0));
	}
	
	@Test
	@Order(8)
	void testSaveKoNombreNoUnique() {
		ArbitroDTO arbitro = new ArbitroDTO();
		arbitro.setNombre("Nombre test");
		arbitro.setGanadosLocal(0);
		arbitro.setEmpatados(1);
		arbitro.setGanadosVisitante(2);
		LigaDTO liga = ligaService.getById(1);
		arbitro.setLiga(liga);
		DataIntegrityViolationException ex = assertThrows(DataIntegrityViolationException.class, () -> service.save(arbitro) );
		assertNotNull(ex);
	}
}

