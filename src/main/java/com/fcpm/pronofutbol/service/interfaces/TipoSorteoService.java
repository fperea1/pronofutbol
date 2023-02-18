package com.fcpm.pronofutbol.service.interfaces;

import java.util.List;

import com.fcpm.pronofutbol.dtos.BaseDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.dtos.TipoSorteoDTO;

public interface TipoSorteoService {
	
	ResultTableDTO findByFilter(String filtroWeb, boolean exportar);
	
	TipoSorteoDTO getById(Integer id);

	void save(TipoSorteoDTO dto);

	void update(TipoSorteoDTO dto);

	List<BaseDTO> findForSelect();

}
