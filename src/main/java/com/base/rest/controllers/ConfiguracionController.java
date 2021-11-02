package com.base.rest.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.base.rest.dtos.Configuracion;
import com.base.rest.dtos.Log;
import com.base.rest.service.interfaces.ConfiguracionService;
import com.base.rest.service.interfaces.LogService;

@RestController
@RequestMapping("/configuracion")
public class ConfiguracionController extends BaseController {

	@Autowired
	private ConfiguracionService configuracionService;
	
	@Autowired
	private LogService logService;
	
	@GetMapping("/findAll")
	//@PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<Configuracion>> findAll() {
        return new ResponseEntity<List<Configuracion>>(configuracionService.findAll(), HttpStatus.OK);
    }
	
	@PostMapping("/save")
	//@PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> save(@RequestBody Configuracion configuracion) {
		configuracionService.save(configuracion);
		logService.save(new Log(getCurrentUserName(), "Configuracion", "Alta", "Configuracion: " + configuracion.getNombre(), new Date()));
        return new ResponseEntity<String>("Operación correcta", HttpStatus.OK);
    }
	
	@PutMapping("/update")
	//@PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> update(@RequestBody Configuracion configuracion) {
		configuracionService.update(configuracion);
		logService.save(new Log(getCurrentUserName(), "Configuracion", "Edición", "Configuracion: " + configuracion.getNombre(), new Date()));
        return new ResponseEntity<String>("Operación correcta", HttpStatus.OK);
    }
	
	@GetMapping("/find")
	//@PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Configuracion> findById(@RequestParam Integer id) {
		return new ResponseEntity<Configuracion>(configuracionService.findById(id), HttpStatus.OK);
    }
	
	@DeleteMapping("/delete")
	//@PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> deleteById(@RequestParam Integer id) {
		Configuracion configuracion = configuracionService.findById(id);
		configuracionService.deleteById(id);
		logService.save(new Log(getCurrentUserName(), "Configuracion", "Baja", "Configuracion: " + configuracion.getNombre(), new Date()));
		return new ResponseEntity<String>("Operación correcta", HttpStatus.OK);
    }
}
