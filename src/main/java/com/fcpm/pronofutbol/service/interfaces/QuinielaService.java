package com.fcpm.pronofutbol.service.interfaces;

import java.util.List;

import com.fcpm.pronofutbol.dtos.QuinielaDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.dtos.SelectDTO;

public interface QuinielaService extends BaseService<QuinielaDTO, Integer> {

	QuinielaDTO getByNumero(Integer numero);

	ResultTableDTO findByFilter(String filtro, boolean b);

	List<SelectDTO> findForSelect();

}
