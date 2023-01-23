package com.fcpm.pronofutbol.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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

import com.fcpm.pronofutbol.constant.Constantes;
import com.fcpm.pronofutbol.dtos.ConfiguracionDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.exceptions.ServiceException;
import com.fcpm.pronofutbol.service.interfaces.ConfiguracionService;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class ConfiguracionServiceTest {
	
	@Autowired
	private ConfiguracionService service;
	
	private String filtro = "{\"first\":0,\"rows\":10,\"sortOrder\":1,\"filters\":{},\"globalFilter\":null}";

	@Test
	@Order(1)
	void testSaveOk() {
		ConfiguracionDTO config = new ConfiguracionDTO();
		config.setNombre("Configuración");
		config.setValor("Valor");
		service.save(config);
		ResultTableDTO result = service.findByFilter(filtro, false);
		assertTrue(result == null || result.getList().size() >= 1);
	}
	
	@Test
	@Order(2)
	void test() {
		ResultTableDTO result = service.findByFilter(filtro, false);
		assertTrue(result == null || result.getList().size() >= 1);
	}
	
	@Test
	@Order(3)
	void testSaveNombreMinSizeKo() {
		ConfiguracionDTO config = new ConfiguracionDTO();
		config.setNombre("");
		config.setValor("Valor 2");
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(config) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertTrue(messages != null);
		assertEquals(messages.get(0), Constantes.VALIDATION_NOMBRE_CONFIG_SIZE);
	}
	
	@Test
	@Order(4)
	void testSaveNombreMaxSizeKo() {
		ConfiguracionDTO config = new ConfiguracionDTO();
		config.setNombre("Co01234567890123456789012345678901234567890123456789");
		config.setValor("Valor 2");
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(config) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertTrue(messages != null);
		assertEquals(messages.get(0), Constantes.VALIDATION_NOMBRE_CONFIG_SIZE);	
	}
	
	@Test
	@Order(5)
	void testSaveNombreNullKo() {
		ConfiguracionDTO config = new ConfiguracionDTO();
		config.setNombre(null);
		config.setValor("Valor 2");
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(config) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertTrue(messages != null);
		assertEquals(messages.get(0), Constantes.VALIDATION_NOMBRE_OBLIGATORIO);	
	}

	@Test
	@Order(6)
	void testSaveValorMinSizeKo() {
		ConfiguracionDTO config = new ConfiguracionDTO();
		config.setNombre("Configuración 2");
		config.setValor("");
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(config) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertTrue(messages != null);
		assertEquals(messages.get(0), Constantes.VALIDATION_VALOR_CONFIG_SIZE);
	}
	
	@Test
	@Order(7)
	void testSaveValorMaxSizeKo() {
		ConfiguracionDTO config = new ConfiguracionDTO();
		config.setNombre("Configuración 2");
		config.setValor("Ob0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"
				+ "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"
				+ "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"
				+ "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"
				+ "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789");
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(config) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertTrue(messages != null);
		assertEquals(messages.get(0), Constantes.VALIDATION_VALOR_CONFIG_SIZE);
	}
	
	@Test
	@Order(8)
	void testSaveValorNullKo() {
		ConfiguracionDTO config = new ConfiguracionDTO();
		config.setNombre("Configuración 2");
		config.setValor(null);
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(config) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertTrue(messages != null);
		assertEquals(messages.get(0), Constantes.VALIDATION_VALOR_OBLIGATORIO);	
	}
	
	@Test
	@Order(9)
	void testGetByNombreOk() {
		assertNotNull(service.getByNombre("Configuración"));
	}
	
	@Test
	@Order(10)
	void testGetByNombreKo() {
		assertNull(service.getByNombre("Configuración 2"));
	}
	
	@Test
	@Order(11)
	void testGetByIdOk() {
		assertNotNull(service.getById(1));
	}
	
	@Test
	@Order(12)
	void testGetByIdKo() {		
		ServiceException ex = assertThrows(ServiceException.class, () -> service.getById(100) );
		assertNotNull(ex);
		assertTrue(ex.getMessage().equals(Constantes.EXC_NO_EXISTE_ENTIDAD));
	}
	
	@Test
	@Order(13)
	void testDeleteByIdOk() {
		service.deleteById(1);
		ServiceException ex = assertThrows(ServiceException.class, () -> service.getById(1) );
		assertNotNull(ex);
		assertTrue(ex.getMessage().equals(Constantes.EXC_NO_EXISTE_ENTIDAD));
	}
	
	@Test
	@Order(14)
	void testDeleteByIdKo() {
		ServiceException ex = assertThrows(ServiceException.class, () -> service.deleteById(25) );
		assertNotNull(ex);
		assertTrue(ex.getMessage().equals(Constantes.EXC_NO_EXISTE_ENTIDAD));
	}
}
