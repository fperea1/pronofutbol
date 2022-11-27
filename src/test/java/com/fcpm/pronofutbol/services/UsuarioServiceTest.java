package com.fcpm.pronofutbol.services;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.service.interfaces.UsuarioService;

@ExtendWith(SpringExtension.class)
@Configuration
public class UsuarioServiceTest {
	
	@MockBean
	private UsuarioService service;

	@Test
	void test() {
		ResultTableDTO result = service.findByFilter(null, false);
		assertTrue(result == null || result.getList().size() < 1);
	}

}
