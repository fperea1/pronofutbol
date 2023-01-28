package com.fcpm.pronofutbol.service.interfaces;

import java.util.List;

import com.fcpm.pronofutbol.dtos.ArbitroDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.dtos.SelectDTO;

public interface ArbitroService {

	void save(ArbitroDTO dto);
	
	void update(ArbitroDTO dto);

	ResultTableDTO findByFilter(String filtro, boolean b);

	ArbitroDTO getById(Integer id);

	void delete(Integer id);

	List<SelectDTO> findForSelect();

}
