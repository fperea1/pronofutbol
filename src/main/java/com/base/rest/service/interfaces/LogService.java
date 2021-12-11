package com.base.rest.service.interfaces;

import java.util.List;

import com.base.rest.entities.Log;

public interface LogService {

	public List<Log> findAll();
	
	public void save(Log log);
}
