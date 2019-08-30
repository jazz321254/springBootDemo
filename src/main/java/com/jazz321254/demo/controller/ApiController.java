package com.jazz321254.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jazz321254.demo.exception.ResourceNotFoundException;
import com.jazz321254.demo.model.Department;
import com.jazz321254.demo.model.Employees;
import com.jazz321254.demo.repository.DepartmentRepository;
import com.jazz321254.demo.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/v1")
public class ApiController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);

	protected static final String LOGGER_HEADER = "========== {} ==========";

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	@GetMapping("/employees")
	public List<Employees> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@GetMapping("/department")
	public List<Department> getAllDepartments() {
		return departmentRepository.findAll();
	}

	@PostMapping(value = "/employees", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public Map<String, Boolean> createEmployee(@Valid Employees employees) {
		LOGGER.info(LOGGER_HEADER, "Create a new employee");
		Department department = departmentRepository.findById(employees.getDepartment().getDepId()).get();
		employees.setDepartment(department);
		employeeRepository.save(employees);
		Map<String, Boolean> response = new HashMap<>();
		response.put("add", Boolean.TRUE);
		return response;
	}

	@PutMapping("/employees/{id}")
	public ResponseEntity<Employees> updateEmployee(@Valid Employees emp) throws ResourceNotFoundException {
		LOGGER.info(LOGGER_HEADER, "Update a new employee");
		Employees employee = employeeRepository.findById(emp.getEmpId()).orElseThrow(
				() -> new ResourceNotFoundException("Employee not found for this id :: " + emp.getEmpId()));
		employee.setEmail(emp.getEmail());
		employee.setLastName(emp.getLastName());
		employee.setFirstName(emp.getFirstName());
		final Employees updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}

	@DeleteMapping("/employees/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		LOGGER.info(LOGGER_HEADER, "Delete a new employee");
		Employees employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
