package com.base.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.rest.constant.Constantes;
import com.base.rest.dtos.BaseDTO;
import com.base.rest.dtos.ResultTableDTO;
import com.base.rest.dtos.RolDTO;
import com.base.rest.entities.BaseEntity;
import com.base.rest.entities.Rol;
import com.base.rest.service.interfaces.RolService;
import com.base.rest.utils.Converter;

@RestController
@RequestMapping(Constantes.ROLES)
public class RolController {

	@Autowired
	private RolService rolService;
	
	@GetMapping(Constantes.FIND_ALL)
    public ResponseEntity<ResultTableDTO> findAll() {
		
		Page<BaseEntity> page = rolService.findAll();
		List<BaseDTO> listado = new Converter<Rol, RolDTO>().
				convertList(page, Rol.class, RolDTO.class);
		
        return new ResponseEntity<>(new ResultTableDTO(listado, page.getTotalElements()), HttpStatus.OK);
    }

}
