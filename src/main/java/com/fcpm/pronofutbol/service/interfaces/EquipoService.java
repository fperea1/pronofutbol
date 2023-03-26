package com.fcpm.pronofutbol.service.interfaces;

import java.util.List;

import com.fcpm.pronofutbol.dtos.EquipoDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.dtos.SelectDTO;

public interface EquipoService extends BaseService<EquipoDTO, Integer> {

	ResultTableDTO findByFilter(String filtro, boolean b);

	List<SelectDTO> findForSelect();
}
