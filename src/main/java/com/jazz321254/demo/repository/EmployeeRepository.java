package com.jazz321254.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jazz321254.demo.model.Employees;

@Repository
public interface EmployeeRepository extends JpaRepository<Employees, Long>{

	/**
	 * 
	 * use native query to search
	 * 
	 * @return
	 */
	@Query(value = "SELECT * FROM EMPLOYEES e, DEPARTMENT d WHERE e.DEP_ID = d.DEP_ID", nativeQuery = true)
	List<Employees> findList();
}