package com.fcpm.pronofutbol.service.interfaces;

import java.util.List;

import com.fcpm.pronofutbol.dtos.BaseDTO;
import com.fcpm.pronofutbol.dtos.LigaDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;

public interface LigaService {

	ResultTableDTO findByFilter(String filtroWeb, boolean exportar);

	void save(LigaDTO dto);

	LigaDTO getById(int id);

	void update(LigaDTO dto);

	List<BaseDTO> findForSelect();

	void delete(Integer id);

}
