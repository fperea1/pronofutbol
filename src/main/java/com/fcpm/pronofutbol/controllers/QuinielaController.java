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
import com.fcpm.pronofutbol.dtos.QuinielaDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.dtos.SelectDTO;
import com.fcpm.pronofutbol.enums.reportes.TablaQuinielaEnum;
import com.fcpm.pronofutbol.service.interfaces.QuinielaService;
import com.fcpm.pronofutbol.utils.I18nUtils;
import com.fcpm.pronofutbol.utils.POIUtils;
import com.google.common.net.HttpHeaders;

import jakarta.validation.Valid;

@RestController
@RequestMapping(Constantes.QUINIELAS)
public class QuinielaController extends BaseController {

	@Autowired
	private QuinielaService service;
	
	@GetMapping(Constantes.FIND_FOR_SELECT)
    public ResponseEntity<List<SelectDTO>> findAll() {
		
		List<SelectDTO> list = service.findForSelect();
		
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
	
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
						TablaQuinielaEnum.values(), I18nUtils.getMensaje(Constantes.SHEET_QUINIELAS))));
	}
	
	@PostMapping(Constantes.SAVE)
    public ResponseEntity<String> save(@Valid @RequestBody QuinielaDTO dto) {
		service.save(dto);
		return responseOperationCorrecta(Constantes.QUINIELA, Constantes.ALTA, 
				I18nUtils.getMensaje(Constantes.QUINIELA) + Constantes.SEPARADOR_DOS_PUNTOS + dto.getNombre());
    }
	
	@PutMapping(Constantes.UPDATE)
    public ResponseEntity<String> update(@Valid @RequestBody QuinielaDTO dto) {
		service.update(dto);
		return responseOperationCorrecta(Constantes.QUINIELA, Constantes.EDICION, 
				I18nUtils.getMensaje(Constantes.QUINIELA) + Constantes.SEPARADOR_DOS_PUNTOS + dto.getNombre());
    }
	
	@GetMapping(Constantes.GET_BY_ID)
    public ResponseEntity<QuinielaDTO> getById(@RequestParam Integer id) {
		return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }
	
	@DeleteMapping(Constantes.DELETE + "/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
		QuinielaDTO dto = service.getById(id);
		service.delete(id);
		return responseOperationCorrecta(Constantes.QUINIELA, Constantes.BAJA, 
				I18nUtils.getMensaje(Constantes.QUINIELA) + Constantes.SEPARADOR_DOS_PUNTOS + dto.getNombre() + " - " + dto.getNumero());
	}

}
