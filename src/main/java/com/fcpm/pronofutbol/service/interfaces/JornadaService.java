package com.fcpm.pronofutbol.service.interfaces;

import java.util.List;

import com.fcpm.pronofutbol.dtos.BaseDTO;
import com.fcpm.pronofutbol.dtos.JornadaDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;

public interface JornadaService {

	ResultTableDTO findByFilter(String filtroWeb, boolean exportar);
	
	JornadaDTO getById(Integer id);

	void save(JornadaDTO dto);

	void update(JornadaDTO dto);

	public List<BaseDTO> findForSelect();

	void delete(Integer id);
}
