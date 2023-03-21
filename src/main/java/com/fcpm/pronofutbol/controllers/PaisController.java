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
import com.fcpm.pronofutbol.dtos.PaisDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.dtos.SelectDTO;
import com.fcpm.pronofutbol.enums.reportes.TablaPaisEnum;
import com.fcpm.pronofutbol.service.interfaces.PaisService;
import com.fcpm.pronofutbol.utils.I18nUtils;
import com.fcpm.pronofutbol.utils.POIUtils;
import com.google.common.net.HttpHeaders;

import jakarta.validation.Valid;

@RestController
@RequestMapping(Constantes.PAISES)
public class PaisController extends BaseController {

	@Autowired
	private PaisService service;
	
	@GetMapping(Constantes.FIND_FOR_SELECT)
    public ResponseEntity<List<SelectDTO>> findForSelect() {
		
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
						TablaPaisEnum.values(), I18nUtils.getMensaje(Constantes.SHEET_PAISES))));
	}
	
	@PostMapping(Constantes.SAVE)
    public ResponseEntity<String> save(@Valid @RequestBody PaisDTO dto) {
		service.crear(dto);
		return responseOperationCorrecta(Constantes.PAIS, Constantes.ALTA, 
				I18nUtils.getMensaje(Constantes.PAIS) + Constantes.SEPARADOR_DOS_PUNTOS + dto.getNombre());
    }
	
	@PutMapping(Constantes.UPDATE + "/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @Valid @RequestBody PaisDTO dto) {
		service.actualizar(id, dto);
		return responseOperationCorrecta(Constantes.PAIS, Constantes.EDICION, 
				I18nUtils.getMensaje(Constantes.PAIS) + Constantes.SEPARADOR_DOS_PUNTOS + dto.getNombre());
    }
	
	@GetMapping(Constantes.GET_BY_ID)
    public ResponseEntity<PaisDTO> getById(@RequestParam Integer id) {
		return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }
	
	@DeleteMapping(Constantes.DELETE + "/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
		PaisDTO dto = service.getById(id);
		service.borrar(id);
		return responseOperationCorrecta(Constantes.PAIS, Constantes.BAJA, 
				I18nUtils.getMensaje(Constantes.PAIS) + Constantes.SEPARADOR_DOS_PUNTOS + dto.getNombre());
	}

}
