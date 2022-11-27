package com.fcpm.pronofutbol.services;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.service.interfaces.RolService;

@SpringBootTest
class RolServiceTest {
	
	@Autowired
	private RolService service;

	@Test
	void test() {
		ResultTableDTO result = service.findAll();
		assertTrue(result == null || result.getList().size() >= 2);
	}

}
