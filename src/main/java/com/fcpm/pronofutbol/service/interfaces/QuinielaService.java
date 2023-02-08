package com.fcpm.pronofutbol.service.interfaces;

import java.util.List;

import com.fcpm.pronofutbol.dtos.BaseDTO;
import com.fcpm.pronofutbol.dtos.QuinielaDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;

public interface QuinielaService {

	void save(QuinielaDTO dto);
	
	void update(QuinielaDTO dto);

	QuinielaDTO getByNumero(Integer numero);

	ResultTableDTO findByFilter(String filtro, boolean b);

	QuinielaDTO getById(Integer id);

	void delete(Integer id);

	List<BaseDTO> findForSelect();

}
