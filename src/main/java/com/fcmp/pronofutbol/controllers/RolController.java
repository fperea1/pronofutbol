package com.fcmp.pronofutbol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fcmp.pronofutbol.constant.Constantes;
import com.fcmp.pronofutbol.dtos.ResultTableDTO;
import com.fcmp.pronofutbol.service.interfaces.RolService;

@RestController
@RequestMapping(Constantes.ROLES)
public class RolController {

	@Autowired
	private RolService rolService;
	
	@GetMapping(Constantes.FIND_ALL)
    public ResponseEntity<ResultTableDTO> findAll() {
		
		ResultTableDTO resultTable = rolService.findAll();
		
        return new ResponseEntity<>(resultTable, HttpStatus.OK);
    }

}
