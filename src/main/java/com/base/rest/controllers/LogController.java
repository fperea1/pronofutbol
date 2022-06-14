package com.base.rest.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.base.rest.constant.Constantes;
import com.base.rest.dtos.BaseDTO;
import com.base.rest.dtos.LogDTO;
import com.base.rest.dtos.ResultTableDTO;
import com.base.rest.entities.BaseEntity;
import com.base.rest.entities.Log;
import com.base.rest.enums.reportes.TablaLogsEnum;
import com.base.rest.service.interfaces.LogService;
import com.base.rest.utils.Converter;
import com.base.rest.utils.POIUtils;
import com.google.common.net.HttpHeaders;

@RestController
@RequestMapping(Constantes.LOGS)
public class LogController extends BaseController {

	@Autowired
	private LogService logService;
	
	@GetMapping(Constantes.FIND_BY_FILTER)
    public ResponseEntity<ResultTableDTO> findByFilter(@RequestParam String filtro) {
		
		Page<BaseEntity> page = logService.findByFilter(filtro, false);
		List<BaseDTO> listado = new Converter<Log, LogDTO>().
				convertList(page, Log.class, LogDTO.class);
		
        return new ResponseEntity<>(new ResultTableDTO(listado, page.getTotalElements()), HttpStatus.OK);
    }
	
	@GetMapping(value = Constantes.GET_REPORT_EXCEL)
	public ResponseEntity<Resource> getReportExcel(@RequestParam String filtro) throws IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		List<BaseDTO> listado = new Converter<Log, LogDTO>().
				convertList(logService.findByFilter(filtro, true), Log.class, LogDTO.class);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, Constantes.ATTACHMENTS_EXCEL)
				.contentType(MediaType.parseMediaType(Constantes.CONTENT_EXCEL))
				.cacheControl(CacheControl.noCache())
				.body(new ByteArrayResource(POIUtils.getReportExcel(listado, TablaLogsEnum.values(), Constantes.SHEET_LOGS)));
	}
}
