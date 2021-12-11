package com.base.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.rest.entities.Log;
import com.base.rest.service.interfaces.LogService;

@RestController
@RequestMapping("/logs")
public class LogController extends BaseController {

	@Autowired
	private LogService logService;
	
	@GetMapping("/findAll")
	//@PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<Log>> findAll() {
        return new ResponseEntity<List<Log>>(logService.findAll(), HttpStatus.OK);
    }
}
