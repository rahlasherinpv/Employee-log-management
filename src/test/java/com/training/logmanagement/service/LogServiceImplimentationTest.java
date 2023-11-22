package com.training.logmanagement.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.training.logmanagement.bean.Employee;

class LogServiceImplimentationTest {
	@BeforeEach
	void setup() {
		LogServiceImplimentation lsi = new LogServiceImplimentation();
	}
	
	@Test
	void test() {
		
		LogServiceImplimentation lsi = new LogServiceImplimentation();
		
		String key = "IN";
		List<Employee> actual = new ArrayList<Employee>();
		actual.add(new Employee(3, "sana", 12, "devoloper", "IN"));
		
		List<Employee> empList = new ArrayList<Employee>();
		empList=lsi.findAllEmployeeByKey(key);
		
		for(Employee e : empList) {
			
			assertEquals(e.getEmployeeName(), actual.get(0).getEmployeeName());
			
		}
		
		
		
		
		
		
		
	}

}
