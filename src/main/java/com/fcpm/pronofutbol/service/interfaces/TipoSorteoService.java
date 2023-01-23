package com.fcpm.pronofutbol.service.interfaces;

import java.util.List;

import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.dtos.TipoSorteoDTO;

public interface TipoSorteoService {
	
	ResultTableDTO findByFilter(String filtroWeb, boolean exportar);
	
	TipoSorteoDTO getById(Integer id);

	void save(TipoSorteoDTO pais);

	void update(TipoSorteoDTO pais);

	List<TipoSorteoDTO> findAll();

}
