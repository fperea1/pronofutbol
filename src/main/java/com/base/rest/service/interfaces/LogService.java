package com.base.rest.service.interfaces;

import java.util.List;

import com.base.rest.dtos.Log;

public interface LogService {

	public List<Log> findAll();
	
	public void save(Log log);
}
