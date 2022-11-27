package com.fcpm.pronofutbol.service.interfaces;

import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.dtos.UsuarioDTO;

public interface UsuarioService {

	public ResultTableDTO findByFilter(String filtroWeb, boolean exportar);
	
	public void save(UsuarioDTO usuario);
	
	public void update(UsuarioDTO usuario);
	
	public UsuarioDTO findById(Integer id);
	
	public UsuarioDTO findByUsername(String username);
	
	public void deleteById(Integer id);
	
	public void deactivate(Integer id);
	
	public void activate(Integer id);

	public void cambioPasswordUser(Integer id, String username, String oldPassword, String newPassword, String newPassword2);

	public void cambioPasswordAdmin(Integer id, String newPassword, String newPassword2);
}
