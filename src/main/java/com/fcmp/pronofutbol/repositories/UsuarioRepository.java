package com.fcmp.pronofutbol.repositories;


import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fcmp.pronofutbol.entities.BaseEntity;
import com.fcmp.pronofutbol.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>, JpaSpecificationExecutor<BaseEntity> {

	Usuario getByUsername(String username);

	@Modifying
	@Query("update Usuario set activo = 0, fechaDesactivacion = :fecha where id = :id")
	Integer deactivateById(@Param("fecha") Date fecha, @Param("id") Integer id);

	@Modifying
	@Query("update Usuario set activo = 1, fechaDesactivacion = null where id = :id")
	Integer activateById(@Param("id") Integer id);
	
	@Modifying
	@Query("update Usuario set password = :password where id = :id")
	Integer changePassword(@Param("password") String password, @Param("id") Integer id);

	@Query("select password from Usuario where id = :id")
	String getPassword(@Param("id") Integer id);

}
