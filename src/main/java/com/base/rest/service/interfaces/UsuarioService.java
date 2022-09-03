package com.base.rest.service.interfaces;

import org.springframework.data.domain.Page;

import com.base.rest.entities.BaseEntity;
import com.base.rest.entities.Usuario;

public interface UsuarioService {

	public Page<BaseEntity> findByFilter(String filtroWeb, boolean exportar);
	
	public void save(Usuario usuario);
	
	public void update(Usuario usuario);
	
	public Usuario findById(Integer id);
	
	public Usuario findByUsername(String username);
	
	public void deleteById(Integer id);
	
	public void deactivateById(Integer id);
	
	public void activateById(Integer id);

	public void cambioPasswordUser(Integer id, String username, String oldPassword, String newPassword, String newPassword2);

	public void cambioPasswordAdmin(Integer id, String newPassword, String newPassword2);
}
