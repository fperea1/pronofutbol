package com.fcpm.pronofutbol.service.interfaces;

import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.dtos.UsuarioDTO;

public interface UsuarioService {

	ResultTableDTO findByFilter(String filtroWeb, boolean exportar);
	
	void save(UsuarioDTO usuario);
	
	void update(UsuarioDTO usuario);
	
	UsuarioDTO getById(Integer id);
	
	UsuarioDTO findByUsername(String username);
	
	void deleteById(Integer id);
	
	void deactivate(Integer id);
	
	void activate(Integer id);

	void cambioPasswordUser(Integer id, String username, String oldPassword, String newPassword, String newPassword2);

	void cambioPasswordAdmin(Integer id, String newPassword, String newPassword2);
}
