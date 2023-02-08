package com.fcpm.pronofutbol.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fcpm.pronofutbol.constant.Constantes;
import com.fcpm.pronofutbol.dtos.ArbitroDTO;
import com.fcpm.pronofutbol.dtos.BaseDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.enums.reportes.TablaArbitrosEnum;
import com.fcpm.pronofutbol.service.interfaces.ArbitroService;
import com.fcpm.pronofutbol.utils.I18nUtils;
import com.fcpm.pronofutbol.utils.POIUtils;
import com.google.common.net.HttpHeaders;

import jakarta.validation.Valid;

@RestController
@RequestMapping(Constantes.ARBITROS)
public class ArbitroController extends BaseController {

	@Autowired
	private ArbitroService service;
	
	@GetMapping(Constantes.FIND_FOR_SELECT)
    public ResponseEntity<List<BaseDTO>> findForSelect() {
		
        return new ResponseEntity<>(service.findForSelect(), HttpStatus.OK);
    }
	
	@GetMapping(Constantes.FIND_BY_FILTER)
    public ResponseEntity<ResultTableDTO> findByFilter(@RequestParam String filtro) {
		
		return new ResponseEntity<>(service.findByFilter(filtro, false), HttpStatus.OK);
    }
	
	@GetMapping(value = Constantes.GET_REPORT_EXCEL)
	public ResponseEntity<Resource> getReportExcel(@RequestParam String filtro) {
		
		ResultTableDTO resultTable = service.findByFilter(filtro, true);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, Constantes.ATTACHMENTS_EXCEL)
				.contentType(MediaType.parseMediaType(Constantes.CONTENT_EXCEL))
				.cacheControl(CacheControl.noCache())
				.body(new ByteArrayResource(POIUtils.getReportExcel(resultTable.getList(), 
						TablaArbitrosEnum.values(), I18nUtils.getMensaje(Constantes.SHEET_ARBITROS))));
	}
	
	@PostMapping(Constantes.SAVE)
    public ResponseEntity<String> save(@Valid @RequestBody ArbitroDTO dto) {
		service.save(dto);
		return responseOperationCorrecta(Constantes.ARBITRO, Constantes.ALTA, 
				I18nUtils.getMensaje(Constantes.ARBITRO) + Constantes.SEPARADOR_DOS_PUNTOS + dto.getNombre());
    }
	
	@PutMapping(Constantes.UPDATE)
    public ResponseEntity<String> update(@Valid @RequestBody ArbitroDTO dto) {
		service.update(dto);
		return responseOperationCorrecta(Constantes.ARBITRO, Constantes.EDICION, 
				I18nUtils.getMensaje(Constantes.ARBITRO) + Constantes.SEPARADOR_DOS_PUNTOS + dto.getNombre());
    }
	
	@GetMapping(Constantes.GET_BY_ID)
    public ResponseEntity<ArbitroDTO> getById(@RequestParam Integer id) {
		return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }
	
	@DeleteMapping(Constantes.DELETE + "/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
		ArbitroDTO dto = service.getById(id);
		service.delete(id);
		return responseOperationCorrecta(Constantes.ARBITRO, Constantes.BAJA, 
				I18nUtils.getMensaje(Constantes.ARBITRO) + Constantes.SEPARADOR_DOS_PUNTOS + dto.getNombre());
	}

}
