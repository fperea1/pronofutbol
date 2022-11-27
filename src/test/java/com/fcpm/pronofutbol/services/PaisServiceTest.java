/**
 * 
 */
package com.fcpm.pronofutbol.services;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.service.interfaces.PaisService;

/**
 * @author Francisco Carlos
 *
 */
public class PaisServiceTest {

	@MockBean
	private PaisService service;

	@Test
	void test() {
		ResultTableDTO result = service.findAll();
		assertTrue(result == null || result.getList().size() < 1);
	}
	
}
