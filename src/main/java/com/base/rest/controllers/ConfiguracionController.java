package com.base.rest.controllers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

import com.base.rest.constant.Constantes;
import com.base.rest.dtos.ConfiguracionDTO;
import com.base.rest.entities.Configuracion;
import com.base.rest.entities.Log;
import com.base.rest.service.interfaces.ConfiguracionService;
import com.base.rest.service.interfaces.LogService;
import com.googlecode.jmapper.JMapper;

@RestController
@RequestMapping(Constantes.CONFIG)
public class ConfiguracionController extends BaseController {

	@Autowired
	private ConfiguracionService configuracionService;
	
	@Autowired
	private LogService logService;
	
	@GetMapping(Constantes.FIND_ALL)
    public ResponseEntity<List<ConfiguracionDTO>> findAll() {
		List<Configuracion> configuraciones = configuracionService.findAll();
		JMapper<ConfiguracionDTO, Configuracion> mapper = new JMapper<>(ConfiguracionDTO.class, Configuracion.class);
		List<ConfiguracionDTO> result = configuraciones.stream().map(temp-> mapper.getDestination(temp))
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
	
	@PostMapping(Constantes.SAVE)
    public ResponseEntity<String> save(@RequestBody ConfiguracionDTO configuracion) {
		JMapper<Configuracion, ConfiguracionDTO> mapper = new JMapper<>(Configuracion.class, ConfiguracionDTO.class);
		configuracionService.save(mapper.getDestination(configuracion));
		logService.save(new Log(getCurrentUserName(), Constantes.CONFIGURACION, Constantes.ALTA, Constantes.CONFIGURACION + Constantes.SEPARADOR_DOS_PUNTOS + configuracion.getNombre(), new Date()));
        return new ResponseEntity<>(Constantes.OPERACION_CORRECTA, HttpStatus.OK);
    }
	
	@PutMapping(Constantes.UPDATE)
    public ResponseEntity<String> update(@RequestBody ConfiguracionDTO configuracion) {
		JMapper<Configuracion, ConfiguracionDTO> mapper = new JMapper<>(Configuracion.class, ConfiguracionDTO.class);
		configuracionService.update(mapper.getDestination(configuracion));
		logService.save(new Log(getCurrentUserName(), Constantes.CONFIGURACION, Constantes.EDICION, Constantes.CONFIGURACION + Constantes.SEPARADOR_DOS_PUNTOS + configuracion.getNombre(), new Date()));
        return new ResponseEntity<>(Constantes.OPERACION_CORRECTA, HttpStatus.OK);
    }
	
	@GetMapping(Constantes.FIND)
    public ResponseEntity<ConfiguracionDTO> findById(@RequestParam Integer id) {
		JMapper<ConfiguracionDTO, Configuracion> mapper = new JMapper<>(ConfiguracionDTO.class, Configuracion.class);
		return new ResponseEntity<>(mapper.getDestination(configuracionService.findById(id)), HttpStatus.OK);
    }
	
	@DeleteMapping(Constantes.DELETE)
    public ResponseEntity<String> deleteById(@RequestParam Integer id) {
		Configuracion configuracion = configuracionService.findById(id);
		configuracionService.deleteById(id);
		logService.save(new Log(getCurrentUserName(), Constantes.CONFIGURACION, Constantes.BAJA, Constantes.CONFIGURACION + Constantes.SEPARADOR_DOS_PUNTOS + configuracion.getNombre(), new Date()));
		return new ResponseEntity<>(Constantes.OPERACION_CORRECTA, HttpStatus.OK);
    }
}
