package com.fcpm.pronofutbol.service.interfaces;

import java.util.List;

import com.fcpm.pronofutbol.dtos.SelectDTO;

public interface RolService {

	List<SelectDTO> findForSelect();

}
