package com.fcpm.pronofutbol.service.interfaces;

import java.util.List;

import com.fcpm.pronofutbol.dtos.PaisDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.dtos.SelectDTO;

public interface PaisService extends BaseService<PaisDTO, Integer> {

	ResultTableDTO findByFilter(String filtroWeb, boolean exportar);
	
	public List<SelectDTO> findForSelect();
}
