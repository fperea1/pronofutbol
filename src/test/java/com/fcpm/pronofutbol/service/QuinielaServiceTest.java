package com.fcpm.pronofutbol.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
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
import com.fcpm.pronofutbol.dtos.QuinielaDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.service.interfaces.LigaService;
import com.fcpm.pronofutbol.service.interfaces.QuinielaService;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class QuinielaServiceTest {

	@Autowired
	private QuinielaService service;

	@Autowired
	private LigaService ligaService;
	
	@Test
	@Order(1) 
	void testSaveOk() {
		QuinielaDTO quiniela = new QuinielaDTO();
		quiniela.setNumero(1);
		quiniela.setNombre("Primera Semana");
		quiniela.setActualizada(false);
		quiniela.setFecha(new Date());
		LigaDTO liga = ligaService.getById(1);
		quiniela.setLiga(liga);
		service.save(quiniela);
		quiniela = service.getByNumero(quiniela.getNumero());
		assertEquals(1, quiniela.getNumero());
		assertEquals(false, quiniela.getActualizada());
		assertNotNull(quiniela.getLiga());
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
	void testSaveKoFechaNull() {
		QuinielaDTO quiniela = new QuinielaDTO();
		quiniela.setNumero(2);
		quiniela.setNombre("Segunda Semana");
		quiniela.setActualizada(false);
		quiniela.setFecha(null);
		LigaDTO liga = ligaService.getById(1);
		quiniela.setLiga(liga);
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(quiniela) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertNotNull(messages);
		assertEquals(Constantes.VALIDATION_FECHA_OBLIGATORIO, messages.get(0));
	}
	
	@Test
	@Order(4)
	void testSaveKoLigaNull() {
		QuinielaDTO quiniela = new QuinielaDTO();
		quiniela.setNumero(2);
		quiniela.setNombre("Segunda Semana");
		quiniela.setActualizada(false);
		quiniela.setFecha(new Date());
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(quiniela) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertNotNull(messages);
		assertEquals(Constantes.VALIDATION_LIGA_OBLIGATORIO, messages.get(0));
	}
	
	@Test
	@Order(5)
	void testSaveKoNumeroNull() {
		QuinielaDTO quiniela = new QuinielaDTO();
		quiniela.setNumero(null);
		quiniela.setNombre("Segunda Semana");
		quiniela.setActualizada(false);
		quiniela.setFecha(new Date());
		LigaDTO liga = ligaService.getById(1);
		quiniela.setLiga(liga);
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(quiniela) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertNotNull(messages);
		assertEquals(Constantes.VALIDATION_NUMERO_OBLIGATORIO, messages.get(0));
	}
	
	@Test
	@Order(6)
	void testSaveKoActualizadaNull() {
		QuinielaDTO quiniela = new QuinielaDTO();
		quiniela.setNumero(2);
		quiniela.setNombre("Segunda Semana");
		quiniela.setActualizada(null);
		quiniela.setFecha(new Date());
		LigaDTO liga = ligaService.getById(1);
		quiniela.setLiga(liga);
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(quiniela) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertNotNull(messages);
		assertEquals(Constantes.VALIDATION_ACTUALIZADA_OBLIGATORIO, messages.get(0));
	}
	
	@Test
	@Order(7)
	void testSaveKoNombreNull() {
		QuinielaDTO quiniela = new QuinielaDTO();
		quiniela.setNumero(2);
		quiniela.setNombre(null);
		quiniela.setActualizada(false);
		quiniela.setFecha(new Date());
		LigaDTO liga = ligaService.getById(1);
		quiniela.setLiga(liga);
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(quiniela) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertNotNull(messages);
		assertEquals(Constantes.VALIDATION_NOMBRE_OBLIGATORIO, messages.get(0));
	}
	
	@Test
	@Order(8)
	void testSaveKoNumeroNoUnique() {
		QuinielaDTO quiniela = new QuinielaDTO();
		quiniela.setNumero(1);
		quiniela.setNombre("Segunda Semana");
		quiniela.setActualizada(false);
		quiniela.setFecha(new Date());
		LigaDTO liga = ligaService.getById(1);
		quiniela.setLiga(liga);
		DataIntegrityViolationException ex = assertThrows(DataIntegrityViolationException.class, () -> service.save(quiniela) );
		assertNotNull(ex);
	}
	
	@Test
	@Order(9)
	void testSaveKoNombreNoUnique() {
		QuinielaDTO quiniela = new QuinielaDTO();
		quiniela.setNumero(2);
		quiniela.setNombre("Primera Semana");
		quiniela.setActualizada(false);
		quiniela.setFecha(new Date());
		LigaDTO liga = ligaService.getById(1);
		quiniela.setLiga(liga);
		DataIntegrityViolationException ex = assertThrows(DataIntegrityViolationException.class, () -> service.save(quiniela) );
		assertNotNull(ex);
	}
}
