package com.fcpm.pronofutbol.service.interfaces;

import java.util.List;

import com.fcpm.pronofutbol.dtos.LigaDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.dtos.SelectDTO;

public interface LigaService extends BaseService<LigaDTO, Integer> {

	ResultTableDTO findByFilter(String filtroWeb, boolean exportar);

	List<SelectDTO> findForSelect();

}
