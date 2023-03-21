package com.fcpm.pronofutbol.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fcpm.pronofutbol.constant.Constantes;
import com.fcpm.pronofutbol.dtos.SelectDTO;
import com.fcpm.pronofutbol.service.interfaces.RolService;

@RestController
@RequestMapping(Constantes.ROLES)
public class RolController {

	@Autowired
	private RolService service;
	
	@GetMapping(Constantes.FIND_FOR_SELECT)
    public ResponseEntity<List<SelectDTO>> findForSelect() {
		
        return new ResponseEntity<>(service.findForSelect(), HttpStatus.OK);
    }

}
