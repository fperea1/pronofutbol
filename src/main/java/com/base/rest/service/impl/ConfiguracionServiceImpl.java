package com.base.rest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.rest.entities.Configuracion;
import com.base.rest.exceptions.EntityNoExistsException;
import com.base.rest.repositories.ConfiguracionRepository;
import com.base.rest.service.interfaces.ConfiguracionService;

@Service
public class ConfiguracionServiceImpl implements ConfiguracionService {

	@Autowired
	private ConfiguracionRepository configuracionRepository;

	@Override
	public Configuracion getByNombre(String nombre) {
		
		return configuracionRepository.getByNombre(nombre);
	}
	
	public List<Configuracion> findAll() {
		
		return configuracionRepository.findAll();
	}

	@Override
	public void save(Configuracion configuracion) {

		configuracionRepository.save(configuracion);
	}

	@Override
	public void update(Configuracion configuracion) {

		configuracionRepository.save(configuracion);
	}

	@Override
	public Configuracion findById(Integer id) {
		
		if (!configuracionRepository.existsById(id)) {
			throw new EntityNoExistsException();
		}
		return configuracionRepository.findById(id).orElse(null);
	}

	@Override
	public void deleteById(Integer id) {
		
		if (!configuracionRepository.existsById(id)) {
			throw new EntityNoExistsException();
		}
		configuracionRepository.deleteById(id);
	}
}
