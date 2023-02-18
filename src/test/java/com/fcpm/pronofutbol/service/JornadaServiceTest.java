package com.fcpm.pronofutbol.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.fcpm.pronofutbol.constant.Constantes;
import com.fcpm.pronofutbol.dtos.JornadaDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.service.interfaces.JornadaService;

import jakarta.validation.ConstraintViolationException;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class JornadaServiceTest {

	@Autowired
	private JornadaService service;

	private String filtro = "{\"first\":0,\"rows\":10,\"sortOrder\":1,\"filters\":{},\"globalFilter\":null}";

	@Test
	@Order(1) 
	void testSaveOk() {
		JornadaDTO jornada = new JornadaDTO();
		jornada.setNombre("Nombre de pruebas");
		service.save(jornada);
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
		assertThrows(ConstraintViolationException.class, () -> service.save(jornada), Constantes.VALIDATION_NOMBRE_OBLIGATORIO);
	}
	
	@Test
	@Order(4)
	void testSaveNombreSizeMayorKo() {
		JornadaDTO jornada = new JornadaDTO();
		jornada.setNombre("Liga de pruebas 12345678901234567890123456789012345678901234567890");
		assertThrows(ConstraintViolationException.class, () -> service.save(jornada), Constantes.VALIDATION_NOMBRE_JORNADA_SIZE);
	}
	
	@Test
	@Order(5)
	void testSaveNombreSizeMenorKo() {
		JornadaDTO jornada = new JornadaDTO();
		jornada.setNombre("");
		assertThrows(ConstraintViolationException.class, () -> service.save(jornada), Constantes.VALIDATION_NOMBRE_JORNADA_SIZE);
	}
	
	@Test
	@Order(6)
	void testSaveKoNombreNoUnique() {
		JornadaDTO jornada = new JornadaDTO();
		jornada.setNombre("Nombre de pruebas");
		assertThrows(DataIntegrityViolationException.class, () -> service.save(jornada) );
	}
	
}
