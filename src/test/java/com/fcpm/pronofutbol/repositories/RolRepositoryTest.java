package com.fcpm.pronofutbol.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestPropertySource;

import com.fcmp.pronofutbol.entities.Rol;
import com.fcmp.pronofutbol.repositories.RolRepository;

@SpringBootTest
//@TestPropertySource(
//		  locations = "classpath:application.properties")
class RolRepositoryTest {
	
	@Autowired
	private RolRepository repository;	
	
	@BeforeAll
	static void setup() {
	    System.out.println("@BeforeAll - executes once before all test methods in this class");
	}

	@Test
	void test() {
		List<Rol> result = repository.findAll();
		assertTrue(result.size() >= 2);
	}

}
