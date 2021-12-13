package com.base.rest.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.rest.constant.Constantes;
import com.base.rest.dtos.LogDTO;
import com.base.rest.entities.Log;
import com.base.rest.service.interfaces.LogService;
import com.googlecode.jmapper.JMapper;

@RestController
@RequestMapping(Constantes.LOGS)
public class LogController extends BaseController {

	@Autowired
	private LogService logService;
	
	@GetMapping(Constantes.FIND_ALL)
    public ResponseEntity<List<LogDTO>> findAll() {        
        List<Log> logs = logService.findAll();
		JMapper<LogDTO, Log> mapper = new JMapper<>(LogDTO.class, Log.class);
		List<LogDTO> result = logs.stream().map(temp-> mapper.getDestination(temp))
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
