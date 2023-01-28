package com.fcpm.pronofutbol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
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

import com.fasterxml.jackson.annotation.JsonView;
import com.fcpm.pronofutbol.constant.Constantes;
import com.fcpm.pronofutbol.dtos.CambioPasswordDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.dtos.UsuarioDTO;
import com.fcpm.pronofutbol.enums.reportes.TablaUsuariosEnum;
import com.fcpm.pronofutbol.service.interfaces.UsuarioService;
import com.fcpm.pronofutbol.utils.I18nUtils;
import com.fcpm.pronofutbol.utils.POIUtils;
import com.fcpm.pronofutbol.views.View;
import com.google.common.net.HttpHeaders;

import jakarta.validation.Valid;

@RestController
@RequestMapping(Constantes.USUARIOS)
public class UsuarioController extends BaseController {

	@Autowired
	private UsuarioService service;
	
	@GetMapping(Constantes.FIND_BY_FILTER)
    public ResponseEntity<ResultTableDTO> findByFilter(@RequestParam String filtro) {
		
		ResultTableDTO resultTable = service.findByFilter(filtro, false);
		
        return new ResponseEntity<>(resultTable, HttpStatus.OK);
    }
	
	@GetMapping(value = Constantes.GET_REPORT_EXCEL)
	public ResponseEntity<Resource> getReportExcel(@RequestParam String filtro) {
		
		ResultTableDTO resultTable = service.findByFilter(filtro, true);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, Constantes.ATTACHMENTS_EXCEL)
				.contentType(MediaType.parseMediaType(Constantes.CONTENT_EXCEL))
				.cacheControl(CacheControl.noCache())
				.body(new ByteArrayResource(POIUtils.getReportExcel(resultTable.getList(), 
						TablaUsuariosEnum.values(), I18nUtils.getMensaje(Constantes.SHEET_USUARIOS))));
	}
	
	@PostMapping(Constantes.SAVE)
    public ResponseEntity<String> save(@Valid @RequestBody UsuarioDTO usuario) {
		service.save(usuario);
		return responseOperationCorrecta(Constantes.USUARIO, Constantes.ALTA, 
				I18nUtils.getMensaje(Constantes.USUARIO) + Constantes.SEPARADOR_DOS_PUNTOS + usuario.getUsername());
    }
	
	@PutMapping(Constantes.UPDATE)
    public ResponseEntity<String> update(@Valid @RequestBody UsuarioDTO usuario) {
		service.update(usuario);
		return responseOperationCorrecta(Constantes.USUARIO, Constantes.EDICION, 
				I18nUtils.getMensaje(Constantes.USUARIO) + Constantes.SEPARADOR_DOS_PUNTOS + usuario.getUsername());
    }
	
	@JsonView(View.Public.class)
	@GetMapping(Constantes.GET_BY_ID)
    public ResponseEntity<UsuarioDTO> getById(@RequestParam Integer id) {
		return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }
	
	@DeleteMapping(Constantes.DELETE)
    public ResponseEntity<String> deleteById(@RequestParam Integer id) {
		UsuarioDTO usuario = service.getById(id);
		service.deleteById(id);
		return responseOperationCorrecta(Constantes.USUARIO, Constantes.BAJA, 
				I18nUtils.getMensaje(Constantes.USUARIO) + Constantes.SEPARADOR_DOS_PUNTOS + usuario.getUsername());
	}
	
	@PutMapping(Constantes.DEACTIVATE)
    public ResponseEntity<String> deactivateById(@RequestBody Integer id) {
		UsuarioDTO usuario = service.getById(id);
		service.deactivate(id);
		return responseOperationCorrecta(Constantes.USUARIO, Constantes.DESACTIVAR, 
				I18nUtils.getMensaje(Constantes.USUARIO) + Constantes.SEPARADOR_DOS_PUNTOS + usuario.getUsername());
	}
	
	@PutMapping(Constantes.ACTIVATE)
    public ResponseEntity<String> activateById(@RequestBody Integer id) {
		UsuarioDTO usuario = service.getById(id);
		service.activate(id);
		return responseOperationCorrecta(Constantes.USUARIO, Constantes.ACTIVAR, 
				I18nUtils.getMensaje(Constantes.USUARIO) + Constantes.SEPARADOR_DOS_PUNTOS + usuario.getUsername());
	}
	
	@PutMapping(Constantes.CAMBIO_PASSWORD_ADMIN)
	public ResponseEntity<String> cambioPasswordAdmin(@Valid @RequestBody CambioPasswordDTO cambioPasswordDTO) {
		UsuarioDTO usuario = service.getById(cambioPasswordDTO.getId());
		service.cambioPasswordAdmin(cambioPasswordDTO.getId(), cambioPasswordDTO.getNewPassword(), cambioPasswordDTO.getNewPassword2());
		return responseOperationCorrecta(Constantes.USUARIO, Constantes.CAMBIO_PASS_ADMIN, 
				I18nUtils.getMensaje(Constantes.USUARIO) + Constantes.SEPARADOR_DOS_PUNTOS + usuario.getUsername());
	}
}
