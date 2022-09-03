package com.base.rest.controllers;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.base.rest.dtos.BaseDTO;
import com.base.rest.dtos.ResultTableDTO;
import com.base.rest.dtos.UsuarioDTO;
import com.base.rest.entities.BaseEntity;
import com.base.rest.entities.Log;
import com.base.rest.entities.Usuario;
import com.base.rest.enums.reportes.TablaUsuariosEnum;
import com.base.rest.service.interfaces.LogService;
import com.base.rest.service.interfaces.UsuarioService;
import com.base.rest.utils.Converter;
import com.base.rest.utils.POIUtils;
import com.base.rest.views.View;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.net.HttpHeaders;
import com.googlecode.jmapper.JMapper;

@RestController
@RequestMapping(Constantes.USUARIOS)
public class UsuarioController extends BaseController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private LogService logService;
	
	@GetMapping(Constantes.FIND_BY_FILTER)
    public ResponseEntity<ResultTableDTO> findByFilter(@RequestParam String filtro) {
		
		Page<BaseEntity> page = usuarioService.findByFilter(filtro, false);
		List<BaseDTO> listado = new Converter<Usuario, UsuarioDTO>().
				convertList(page, Usuario.class, UsuarioDTO.class);
		
        return new ResponseEntity<>(new ResultTableDTO(listado, page.getTotalElements()), HttpStatus.OK);
    }
	
	@GetMapping(value = Constantes.GET_REPORT_EXCEL)
	public ResponseEntity<Resource> getReportExcel(@RequestParam String filtro) {
		
		List<BaseDTO> listado = new Converter<Usuario, UsuarioDTO>().
				convertList(usuarioService.findByFilter(filtro, true), Usuario.class, UsuarioDTO.class);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, Constantes.ATTACHMENTS_EXCEL)
				.contentType(MediaType.parseMediaType(Constantes.CONTENT_EXCEL))
				.cacheControl(CacheControl.noCache())
				.body(new ByteArrayResource(POIUtils.getReportExcel(listado, TablaUsuariosEnum.values(), Constantes.SHEET_USUARIOS)));
	}
	
	@PostMapping(Constantes.SAVE)
    public ResponseEntity<String> save(@Valid @RequestBody UsuarioDTO usuario) {
		JMapper<Usuario, UsuarioDTO> mapper = new JMapper<>(Usuario.class, UsuarioDTO.class);
		usuarioService.save(mapper.getDestination(usuario));
		logService.save(new Log(getCurrentUserName(), Constantes.USUARIO, Constantes.ALTA, Constantes.USUARIO + Constantes.SEPARADOR_DOS_PUNTOS + usuario.getUsername(), new Date()));
        return new ResponseEntity<>(Constantes.OPERACION_CORRECTA, HttpStatus.OK);
    }
	
	@PutMapping(Constantes.UPDATE)
    public ResponseEntity<String> update(@Valid @RequestBody UsuarioDTO usuario) {
		JMapper<Usuario, UsuarioDTO> mapper = new JMapper<>(Usuario.class, UsuarioDTO.class);
		usuarioService.update(mapper.getDestination(usuario));
		logService.save(new Log(getCurrentUserName(), Constantes.USUARIO, Constantes.EDICION, Constantes.USUARIO + Constantes.SEPARADOR_DOS_PUNTOS + usuario.getUsername(), new Date()));
        return new ResponseEntity<>(Constantes.OPERACION_CORRECTA, HttpStatus.OK);
    }
	
	@JsonView(View.Public.class)
	@GetMapping(Constantes.FIND)
    public ResponseEntity<UsuarioDTO> findById(@RequestParam Integer id) {
		JMapper<UsuarioDTO, Usuario> mapper = new JMapper<>(UsuarioDTO.class, Usuario.class);
		return new ResponseEntity<>(mapper.getDestination(usuarioService.findById(id)), HttpStatus.OK);
    }
	
	@DeleteMapping(Constantes.DELETE)
    public ResponseEntity<String> deleteById(@RequestParam Integer id) {
		Usuario usuario = usuarioService.findById(id);
		usuarioService.deleteById(id);
		logService.save(new Log(getCurrentUserName(), Constantes.USUARIO, Constantes.BAJA, Constantes.USUARIO + Constantes.SEPARADOR_DOS_PUNTOS + usuario.getUsername(), new Date()));
		return new ResponseEntity<>(Constantes.OPERACION_CORRECTA, HttpStatus.OK);
    }
	
	@PutMapping(Constantes.DEACTIVATE)
    public ResponseEntity<String> deactivateById(@RequestBody Integer id) {
		Usuario usuario = usuarioService.findById(id);
		usuarioService.deactivateById(id);
		logService.save(new Log(getCurrentUserName(), Constantes.USUARIO, Constantes.DESACTIVAR, Constantes.USUARIO +Constantes.SEPARADOR_DOS_PUNTOS + usuario.getUsername(), new Date()));
		return new ResponseEntity<>(Constantes.OPERACION_CORRECTA, HttpStatus.OK);
    }
	
	@PutMapping(Constantes.ACTIVATE)
    public ResponseEntity<String> activateById(@RequestBody Integer id) {
		Usuario usuario = usuarioService.findById(id);
		usuarioService.activateById(id);
		logService.save(new Log(getCurrentUserName(), Constantes.USUARIO, Constantes.ACTIVAR, Constantes.USUARIO + Constantes.SEPARADOR_DOS_PUNTOS + usuario.getUsername(), new Date()));
		return new ResponseEntity<>(Constantes.OPERACION_CORRECTA, HttpStatus.OK);
    }
	
	@PutMapping(Constantes.CAMBIO_PASSWORD_ADMIN)
	public ResponseEntity<String> cambioPasswordAdmin(@RequestParam Integer id, @RequestParam String newPassword, @RequestParam String newPassword2) {
		Usuario usuario = usuarioService.findById(id);
		usuarioService.cambioPasswordAdmin(id, newPassword, newPassword2);
		logService.save(new Log(getCurrentUserName(), Constantes.USUARIO, Constantes.CAMBIO_PASS_ADMIN, Constantes.USUARIO + Constantes.SEPARADOR_DOS_PUNTOS + usuario.getUsername(), new Date()));
		return new ResponseEntity<>(Constantes.OPERACION_CORRECTA, HttpStatus.OK);
    }
}
