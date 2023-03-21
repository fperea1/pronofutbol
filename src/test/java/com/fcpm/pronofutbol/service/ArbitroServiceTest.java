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
import com.fcpm.pronofutbol.dtos.ArbitroDTO;
import com.fcpm.pronofutbol.dtos.LigaDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.service.interfaces.ArbitroService;
import com.fcpm.pronofutbol.service.interfaces.LigaService;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
class ArbitroServiceTest {

	@Autowired
	private ArbitroService service;

	@Autowired
	private LigaService ligaService;

	@Test
	@Order(1)
	void testSaveOk() {
		ArbitroDTO arbitro = getDTO();
		service.crear(arbitro);
		arbitro = service.getById(1);
		assertNotNull(arbitro);
	}

	@Test
	@Order(2)
	void testFindByFilter() {
		String filtro = "{\"first\":0,\"rows\":10,\"sortOrder\":1,\"filters\":{},\"globalFilter\":null}";
		ResultTableDTO result = service.findByFilter(filtro, false);
		assertTrue((result != null) && (result.getList().size() >= 1));
	}

	@Test
	@Order(3)
	void testSaveKoGanadosLocalNull() {
		ArbitroDTO arbitro = getDTO();
		arbitro.setGanadosLocal(null);
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.crear(arbitro) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertNotNull(messages);
		assertEquals(Constantes.VALIDATION_GANADOS_LOCAL_OBLIGATORIO, messages.get(0));
	}

	@Test
	@Order(4)
	void testSaveKoLigaNull() {
		ArbitroDTO arbitro = getDTO();
		arbitro.setLiga(null);
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.crear(arbitro) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertNotNull(messages);
		assertEquals(Constantes.VALIDATION_LIGA_OBLIGATORIO, messages.get(0));
	}

	@Test
	@Order(5)
	void testSaveKoEmpatadosNull() {
		ArbitroDTO arbitro = getDTO();
		arbitro.setEmpatados(null);
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.crear(arbitro) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertNotNull(messages);
		assertEquals(Constantes.VALIDATION_EMPATADOS_OBLIGATORIO, messages.get(0));
	}

	@Test
	@Order(6)
	void testSaveKoGanadosVisitanteNull() {
		ArbitroDTO arbitro = getDTO();
		arbitro.setGanadosVisitante(null);
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.crear(arbitro) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertNotNull(messages);
		assertEquals(Constantes.VALIDATION_GANADOS_VISITANTE_OBLIGATORIO, messages.get(0));
	}

	@Test
	@Order(7)
	void testSaveKoNombreNull() {
		ArbitroDTO arbitro = getDTO();
		arbitro.setNombre(null);
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.crear(arbitro) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertNotNull(messages);
		assertEquals(Constantes.VALIDATION_NOMBRE_OBLIGATORIO, messages.get(0));
	}

	@Test
	@Order(8)
	void testSaveKoNombreNoUnique() {
		ArbitroDTO arbitro = getDTO();
		assertThrows(DataIntegrityViolationException.class, () -> service.crear(arbitro));
	}
	
	private ArbitroDTO getDTO() {
		ArbitroDTO arbitro = new ArbitroDTO();
		arbitro.setNombre("Nombre test");
		arbitro.setGanadosLocal(0);
		arbitro.setEmpatados(1);
		arbitro.setGanadosVisitante(2);
		LigaDTO liga = ligaService.getById(1);
		arbitro.setLiga(liga);
		return arbitro;
	}
}
