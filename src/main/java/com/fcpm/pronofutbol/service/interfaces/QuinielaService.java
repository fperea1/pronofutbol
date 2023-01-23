package com.fcpm.pronofutbol.service.interfaces;

import java.util.List;

import com.fcpm.pronofutbol.dtos.QuinielaDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.dtos.SelectDTO;

public interface QuinielaService {

	void save(QuinielaDTO quiniela);
	
	void update(QuinielaDTO quiniela);

	QuinielaDTO getByNumero(Integer numero);

	ResultTableDTO findByFilter(String filtro, boolean b);

	QuinielaDTO getById(Integer id);

	void delete(Integer id);

	List<SelectDTO> findForSelect();

}
