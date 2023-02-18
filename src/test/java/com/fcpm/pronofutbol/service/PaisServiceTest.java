/**
 * 
 */
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
import com.fcpm.pronofutbol.dtos.PaisDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.service.interfaces.PaisService;

import jakarta.validation.ConstraintViolationException;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class PaisServiceTest {

	@Autowired
	private PaisService service;

	private String filtro = "{\"first\":0,\"rows\":10,\"sortOrder\":1,\"filters\":{},\"globalFilter\":null}";

	@Test
	@Order(1) 
	void testSaveOk() {
		PaisDTO pais = new PaisDTO();
		pais.setNombre("Nombre de pruebas");
		service.save(pais);
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
		assertThrows(ConstraintViolationException.class, () -> service.save(pais), Constantes.VALIDATION_NOMBRE_OBLIGATORIO);
	}
	
	@Test
	@Order(4)
	void testSaveNombreSizeMayorKo() {
		PaisDTO pais = new PaisDTO();
		pais.setNombre("Nombre de pruebas 12345678901234567890123456789012345678901234567890");
		assertThrows(ConstraintViolationException.class, () -> service.save(pais), Constantes.VALIDATION_NOMBRE_PAIS_SIZE);
	}
	
	@Test
	@Order(5)
	void testSaveNombreSizeMenorKo() {
		PaisDTO pais = new PaisDTO();
		pais.setNombre("");
		assertThrows(ConstraintViolationException.class, () -> service.save(pais), Constantes.VALIDATION_NOMBRE_PAIS_SIZE);
	}
	
	@Test
	@Order(6)
	void testSaveKoNombreNoUnique() {
		PaisDTO pais = new PaisDTO();
		pais.setNombre("Nombre de pruebas");
		assertThrows(DataIntegrityViolationException.class, () -> service.save(pais) );
	}
	
}
