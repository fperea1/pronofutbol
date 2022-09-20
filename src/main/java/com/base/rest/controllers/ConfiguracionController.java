package com.base.rest.controllers;

import java.util.List;

import javax.validation.Valid;

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

import com.base.rest.constant.Constantes;
import com.base.rest.dtos.BaseDTO;
import com.base.rest.dtos.ConfiguracionDTO;
import com.base.rest.dtos.ResultTableDTO;
import com.base.rest.enums.reportes.TablaConfiguracionEnum;
import com.base.rest.service.interfaces.ConfiguracionService;
import com.base.rest.utils.POIUtils;
import com.google.common.net.HttpHeaders;

@RestController
@RequestMapping(Constantes.CONFIG)
public class ConfiguracionController extends BaseController {

	@Autowired
	private ConfiguracionService configuracionService;
	
	@GetMapping(Constantes.FIND_BY_FILTER)
    public ResponseEntity<ResultTableDTO> findByFilter(@RequestParam String filtro) {
		
		List<BaseDTO> listado = configuracionService.findByFilter(filtro, false);
        return new ResponseEntity<>(new ResultTableDTO(listado, listado.size()), HttpStatus.OK);
    }
	
	@GetMapping(value = Constantes.GET_REPORT_EXCEL)
	public ResponseEntity<Resource> getReportExcel(@RequestParam String filtro) {
		
		List<BaseDTO> listado = configuracionService.findByFilter(filtro, true);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, Constantes.ATTACHMENTS_EXCEL)
				.contentType(MediaType.parseMediaType(Constantes.CONTENT_EXCEL))
				.cacheControl(CacheControl.noCache())
				.body(new ByteArrayResource(POIUtils.getReportExcel(listado, TablaConfiguracionEnum.values(), Constantes.SHEET_CONFIGURACION)));
	}
	
	@PostMapping(Constantes.SAVE)
    public ResponseEntity<String> save(@Valid @RequestBody ConfiguracionDTO configuracion) {
		configuracionService.save(configuracion);
		return responseOperationCorrecta(Constantes.CONFIGURACION, Constantes.ALTA, Constantes.CONFIGURACION + Constantes.SEPARADOR_DOS_PUNTOS + configuracion.getNombre());
    }
	
	@PutMapping(Constantes.UPDATE)
    public ResponseEntity<String> update(@Valid @RequestBody ConfiguracionDTO configuracion) {
		configuracionService.update(configuracion);
		return responseOperationCorrecta(Constantes.CONFIGURACION, Constantes.EDICION, Constantes.CONFIGURACION + Constantes.SEPARADOR_DOS_PUNTOS + configuracion.getNombre());
    }
	
	@GetMapping(Constantes.FIND)
    public ResponseEntity<ConfiguracionDTO> findById(@RequestParam Integer id) {
		return new ResponseEntity<>(configuracionService.findById(id), HttpStatus.OK);
    }
	
	@DeleteMapping(Constantes.DELETE)
    public ResponseEntity<String> deleteById(@RequestParam Integer id) {
		ConfiguracionDTO configuracion = configuracionService.findById(id);
		configuracionService.deleteById(id);
		return responseOperationCorrecta(Constantes.CONFIGURACION, Constantes.BAJA, Constantes.CONFIGURACION + Constantes.SEPARADOR_DOS_PUNTOS + configuracion.getNombre());
	}
}
