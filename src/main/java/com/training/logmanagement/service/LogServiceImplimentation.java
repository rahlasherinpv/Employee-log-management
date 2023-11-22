package com.training.logmanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.logmanagement.bean.Employee;
import com.training.logmanagement.repo.EmployeeRepo;
@Service
public class LogServiceImplimentation implements LogService {
	@Autowired
	private EmployeeRepo employeeRepo;
	String emptyerror = null;
	List<Employee> empList = new ArrayList<>();
	@Override
	public List<Employee> findAllEmployeeByKey(String key) {
		
		employeeRepo.findAllEmployeeByKey(key).forEach(empList::add);
		return empList;
	}
	public List<Employee> findAll() {
		employeeRepo.findAll().forEach(empList::add);
		return empList;
	}
}
