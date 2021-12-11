package com.base.rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.base.rest.entities.Log;

public interface LogRepository extends JpaRepository<Log, Integer>{

}
