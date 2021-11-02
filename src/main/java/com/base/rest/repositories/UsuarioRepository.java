package com.base.rest.repositories;


import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.base.rest.dtos.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

	Usuario getByUsername(String username);

	@Transactional
	@Modifying
	@Query("update Usuario set activo = 0, fechaDesactivacion = :fecha where id = :id")
	Integer deactivateById(@Param("fecha") Date fecha, @Param("id") Integer id);

	@Transactional
	@Modifying
	@Query("update Usuario set activo = 1, fechaDesactivacion = null where id = :id")
	Integer activateById(@Param("id") Integer id);
	
	@Transactional
	@Modifying
	@Query("update Usuario set password = :password where id = :id")
	Integer changePassword(@Param("password") String password, @Param("id") Integer id);

}
