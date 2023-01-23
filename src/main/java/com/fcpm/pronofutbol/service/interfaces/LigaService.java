package com.fcpm.pronofutbol.service.interfaces;

import java.util.List;

import com.fcpm.pronofutbol.dtos.LigaDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.dtos.SelectDTO;

public interface LigaService {

	ResultTableDTO findByFilter(String filtroWeb, boolean exportar);

	void save(LigaDTO liga);

	LigaDTO getById(int id);

	void update(LigaDTO liga);

	List<SelectDTO> findForSelect();

	void delete(Integer id);

}
