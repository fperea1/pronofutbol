package com.fcpm.pronofutbol.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fcpm.pronofutbol.dtos.SelectDTO;
import com.fcpm.pronofutbol.service.interfaces.RolService;

@SpringBootTest
class RolServiceTest {
	
	@Autowired
	private RolService service;

	@Test
	void testFindAll() {
		List<SelectDTO> result = service.findForSelect();
		assertTrue(result == null || result.size() >= 2);
	}

}
