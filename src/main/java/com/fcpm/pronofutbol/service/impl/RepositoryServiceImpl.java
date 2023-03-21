package com.fcpm.pronofutbol.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fcpm.pronofutbol.constant.Constantes;
import com.fcpm.pronofutbol.exceptions.ServiceException;
import com.fcpm.pronofutbol.service.interfaces.RepositoryService;

public abstract class RepositoryServiceImpl<T, I> extends SpecificationBaseServiceImpl implements RepositoryService<T, I> {

	protected JpaRepository<T, I> repository;
	
	protected RepositoryServiceImpl(JpaRepository<T, I> repository) {
		this.repository = repository;
	}
	
	public void save(T entity) {

		repository.save(entity);
	}

	public void update(I id, T entity) {

		if (!repository.existsById(id)) {
			throw new ServiceException(Constantes.EXC_NO_EXISTE_ENTIDAD);
		}
		repository.save(entity);
	}
	
	public T findById(I id) {
		
		if (!repository.existsById(id)) {
			throw new ServiceException(Constantes.EXC_NO_EXISTE_ENTIDAD);
		}
		return repository.findById(id).orElse(null);
	}

	public void deleteById(I id) {
		
		repository.delete(findById(id));
	}
}
