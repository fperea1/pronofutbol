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

import com.fcpm.pronofutbol.constant.Constantes;
import com.fcpm.pronofutbol.dtos.ConfiguracionDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.enums.reportes.TablaConfiguracionEnum;
import com.fcpm.pronofutbol.service.interfaces.ConfiguracionService;
import com.fcpm.pronofutbol.utils.I18nUtils;
import com.fcpm.pronofutbol.utils.POIUtils;
import com.google.common.net.HttpHeaders;

import jakarta.validation.Valid;

@RestController
@RequestMapping(Constantes.CONFIG)
public class ConfiguracionController extends BaseController {

	@Autowired
	private ConfiguracionService service;
	
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
						TablaConfiguracionEnum.values(), I18nUtils.getMensaje(Constantes.SHEET_CONFIGURACION))));
	}
	
	@PostMapping(Constantes.SAVE)
    public ResponseEntity<String> save(@Valid @RequestBody ConfiguracionDTO configuracion) {
		service.save(configuracion);
		return responseOperationCorrecta(Constantes.CONFIGURACION, Constantes.ALTA, 
				I18nUtils.getMensaje(Constantes.CONFIGURACION) + Constantes.SEPARADOR_DOS_PUNTOS + configuracion.getNombre());
    }
	
	@PutMapping(Constantes.UPDATE)
    public ResponseEntity<String> update(@Valid @RequestBody ConfiguracionDTO configuracion) {
		service.update(configuracion);
		return responseOperationCorrecta(Constantes.CONFIGURACION, Constantes.EDICION, 
				I18nUtils.getMensaje(Constantes.CONFIGURACION) + Constantes.SEPARADOR_DOS_PUNTOS + configuracion.getNombre());
    }
	
	@GetMapping(Constantes.GET_BY_ID)
    public ResponseEntity<ConfiguracionDTO> getById(@RequestParam Integer id) {
		return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }
	
	@DeleteMapping(Constantes.DELETE)
    public ResponseEntity<String> deleteById(@RequestParam Integer id) {
		ConfiguracionDTO configuracion = service.getById(id);
		service.deleteById(id);
		return responseOperationCorrecta(Constantes.CONFIGURACION, Constantes.BAJA, 
				I18nUtils.getMensaje(Constantes.CONFIGURACION) + Constantes.SEPARADOR_DOS_PUNTOS + configuracion.getNombre());
	}
}
