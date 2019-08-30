package com.jazz321254.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jazz321254.demo.model.Department;
import com.jazz321254.demo.model.Employees;
import com.jazz321254.demo.repository.DepartmentRepository;
import com.jazz321254.demo.repository.EmployeeRepository;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

	protected static final String LOGGER_HEADER = "========== {} ==========";
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private DepartmentRepository departmentRepository;

	@GetMapping("")
	public String getAllEmployees(HttpServletRequest request, Model model) {
		LOGGER.info(LOGGER_HEADER, "Enter to employees view");
		List<Employees> employees = employeeRepository.findAll();
		List<Department> department = departmentRepository.findAll();
		model.addAttribute("employees", employees);
		model.addAttribute("department", department);
		return "employees-list";
	}
}
