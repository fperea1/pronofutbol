package com.fcpm.pronofutbol.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fcpm.pronofutbol.constant.Constantes;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.dtos.TipoSorteoDTO;
import com.fcpm.pronofutbol.service.interfaces.TipoSorteoService;

import jakarta.validation.ConstraintViolationException;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class TipoSorteoServiceTest {

	@Autowired
	private TipoSorteoService service;
	
	private String filtro = "{\"first\":0,\"rows\":10,\"sortOrder\":1,\"filters\":{},\"globalFilter\":null}";

	@Test
	@Order(1)
	void testSaveOk() {
		TipoSorteoDTO tipoSorteo = new TipoSorteoDTO();
		tipoSorteo.setNombre("Tipo sortedo de pruebas");
		tipoSorteo.setNumDobles(7);
		tipoSorteo.setNumTriples(4);
		service.save(tipoSorteo);
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
		TipoSorteoDTO tipoSorteo = new TipoSorteoDTO();
		 assertThrows(ConstraintViolationException.class, () -> service.save(tipoSorteo), Constantes.VALIDATION_NOMBRE_OBLIGATORIO);
	}
	
	@Test
	@Order(4)
	void testSaveNombreSizeMayorKo() {
		TipoSorteoDTO tipoSorteo = new TipoSorteoDTO();
		tipoSorteo.setNombre("Tipo sortedo de pruebas 12345678901234567890123456789012345678901234567890");
		tipoSorteo.setNumDobles(1);
		tipoSorteo.setNumTriples(1);
		assertThrows(ConstraintViolationException.class, () -> service.save(tipoSorteo), Constantes.VALIDATION_NOMBRE_TIPO_SORTEO_SIZE);
	}
	
	@Test
	@Order(5)
	void testSaveNombreSizeMenorKo() {
		TipoSorteoDTO tipoSorteo = new TipoSorteoDTO();
		tipoSorteo.setNombre("");
		tipoSorteo.setNumDobles(1);
		tipoSorteo.setNumTriples(1);
		assertThrows(ConstraintViolationException.class, () -> service.save(tipoSorteo), Constantes.VALIDATION_NOMBRE_TIPO_SORTEO_SIZE);
	}
	
	@Test
	@Order(6)
	void testSaveDoblesNullKo() {
		TipoSorteoDTO tipoSorteo = new TipoSorteoDTO();
		tipoSorteo.setNombre("Tipo sortedo de pruebas 2");
		tipoSorteo.setNumDobles(null);
		tipoSorteo.setNumTriples(1);
		assertThrows(ConstraintViolationException.class, () -> service.save(tipoSorteo), Constantes.VALIDATION_NUMERO_DOBLES_OBLIGATORIO);
	}
	
	@Test
	@Order(7)
	void testSaveTriplesNullKo() {
		TipoSorteoDTO tipoSorteo = new TipoSorteoDTO();
		tipoSorteo.setNombre("Tipo sortedo de pruebas 2");
		tipoSorteo.setNumDobles(1);
		tipoSorteo.setNumTriples(null);
		assertThrows(ConstraintViolationException.class, () -> service.save(tipoSorteo), Constantes.VALIDATION_NUMERO_TRIPLES_OBLIGATORIO);
	}
}
