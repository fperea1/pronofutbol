package com.fcpm.pronofutbol.service.interfaces;

import java.util.List;

import com.fcpm.pronofutbol.dtos.ArbitroDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.dtos.SelectDTO;

public interface ArbitroService extends BaseService<ArbitroDTO, Integer> {

	ResultTableDTO findByFilter(String filtro, boolean b);

	List<SelectDTO> findForSelect();

}
