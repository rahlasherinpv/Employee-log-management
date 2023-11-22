package com.training.logmanagement.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.training.logmanagement.bean.Employee;
@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

	Employee findByEmployeeId(Integer employeeId);
	
	@Query(value = "SELECT * FROM EMPLOYEE WHERE employee_name like CONCAT('%', ?1, '%') "
			+ "or designation like CONCAT('%', ?1, '%')"
			+ " or employee_id like CONCAT('%', ?1, '%')"
			+ "or age like CONCAT('%', ?1, '%')"
			+ "or status like CONCAT('%', ?1, '%') ", nativeQuery = true)
	List<Employee> findAllEmployeeByKey(String key);

}
