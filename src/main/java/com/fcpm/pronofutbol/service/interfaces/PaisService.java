package com.fcpm.pronofutbol.service.interfaces;

import java.util.List;

import com.fcpm.pronofutbol.dtos.PaisDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.dtos.SelectDTO;

public interface PaisService {

	ResultTableDTO findByFilter(String filtroWeb, boolean exportar);
	
	PaisDTO getById(Integer id);

	void save(PaisDTO dto);

	void update(PaisDTO dto);

	public List<SelectDTO> findForSelect();

	void delete(Integer id);
}