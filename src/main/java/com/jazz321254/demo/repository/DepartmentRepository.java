package com.jazz321254.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jazz321254.demo.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>{

}