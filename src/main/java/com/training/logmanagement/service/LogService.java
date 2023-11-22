package com.training.logmanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.training.logmanagement.bean.Employee;

public interface LogService {

	List<Employee> findAllEmployeeByKey(String key);
	List<Employee> findAll();

}
