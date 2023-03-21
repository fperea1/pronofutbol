package com.fcpm.pronofutbol.service.interfaces;

import java.util.List;

import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.dtos.SelectDTO;
import com.fcpm.pronofutbol.dtos.TipoSorteoDTO;

public interface TipoSorteoService extends BaseService<TipoSorteoDTO, Integer> {
	
	ResultTableDTO findByFilter(String filtroWeb, boolean exportar);

	List<SelectDTO> findForSelect();

}
