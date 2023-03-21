package com.fcpm.pronofutbol.service.interfaces;

import java.util.List;

import com.fcpm.pronofutbol.dtos.JornadaDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.dtos.SelectDTO;

public interface JornadaService extends BaseService<JornadaDTO, Integer> {

	ResultTableDTO findByFilter(String filtroWeb, boolean exportar);

	public List<SelectDTO> findForSelect();
}
