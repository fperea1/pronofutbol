package com.fcpm.pronofutbol.service.interfaces;

import com.fcpm.pronofutbol.dtos.ConfiguracionDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;

public interface ConfiguracionService extends BaseService<ConfiguracionDTO, Integer> {

	ConfiguracionDTO getByNombre(String nombre);

	ResultTableDTO findByFilter(String filtroWeb, boolean exportar);
}
