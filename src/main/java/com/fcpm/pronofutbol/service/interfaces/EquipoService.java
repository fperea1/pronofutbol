package com.fcpm.pronofutbol.service.interfaces;

import java.util.List;

import com.fcpm.pronofutbol.dtos.BaseDTO;
import com.fcpm.pronofutbol.dtos.EquipoDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;

public interface EquipoService {

	void save(EquipoDTO dto);

	void update(EquipoDTO dto);

	ResultTableDTO findByFilter(String filtro, boolean b);

	EquipoDTO getById(Integer id);

	void delete(Integer id);

	List<BaseDTO> findForSelect();
}
