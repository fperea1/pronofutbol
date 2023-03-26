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
import com.fcpm.pronofutbol.dtos.EquipoDTO;
import com.fcpm.pronofutbol.dtos.LigaDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.enums.ResultadoEnum;
import com.fcpm.pronofutbol.service.interfaces.EquipoService;
import com.fcpm.pronofutbol.service.interfaces.LigaService;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
class EquipoServiceTest {

	@Autowired
	private EquipoService service;

	@Autowired
	private LigaService ligaService;

	@Test
	@Order(1)
	void testSaveOk() {
		EquipoDTO dto = getDto();
		service.crear(dto);
		dto = service.getById(1);
		assertNotNull(dto);
	}

	@Test
	@Order(2)
	void testFindByFilter() {
		String filtro = "{\"first\":0,\"rows\":10,\"sortOrder\":1,\"filters\":{},\"globalFilter\":null}";
		ResultTableDTO result = service.findByFilter(filtro, false);
		assertTrue((result == null) || (result.getList().size() >= 1));
	}

	@Test
	@Order(3)
	void testSaveKoNombreNull() {
		EquipoDTO dto = getDto();
		dto.setNombre(null);
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.crear(dto));
		List<String> messages = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
				.collect(Collectors.toList());
		assertNotNull(messages);
		assertEquals(Constantes.VALIDATION_NOMBRE_OBLIGATORIO, messages.get(0));
	}

	@Test
	@Order(4)
	void testSaveKoNombreNoUnique() {
		EquipoDTO dto = getDto();
		assertThrows(DataIntegrityViolationException.class, () -> service.crear(dto));
	}

	private EquipoDTO getDto() {
		EquipoDTO dto = new EquipoDTO();
		dto.setNombre("Nombre test");
		dto.setEmpatadosLocal(0);
		dto.setEmpatadosVisitante(1);
		dto.setGanadosVisitante(2);
		dto.setGanadosLocal(2);
		dto.setPerdidosLocal(1);
		dto.setPerdidosVisitante(4);
		dto.setPuntos(23);
		dto.setResPenultimoPartido(ResultadoEnum.DOS);
		dto.setResUltimoPartido(ResultadoEnum.DOS);
		LigaDTO liga = ligaService.getById(1);
		dto.setLiga(liga);
		return dto;
	}
}
