package com.fcpm.pronofutbol.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fcpm.pronofutbol.constant.Constantes;
import com.fcpm.pronofutbol.dtos.BaseDTO;
import com.fcpm.pronofutbol.service.interfaces.TipoSorteoService;

@RestController
@RequestMapping(Constantes.TIPOS_SORTEO)
public class TipoSorteoController extends BaseController {

	@Autowired
	private TipoSorteoService service;
	
	@GetMapping(Constantes.FIND_ALL)
	public ResponseEntity<List<BaseDTO>> findForSelect() {
		
        return new ResponseEntity<>(service.findForSelect(), HttpStatus.OK);
    }

}
