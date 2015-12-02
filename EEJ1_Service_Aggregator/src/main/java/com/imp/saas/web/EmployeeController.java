package com.imp.saas.web;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.imp.saas.domain.Employee;
import com.imp.saas.exception.DatabaseCustomException;
import com.imp.saas.repository.EmployeeRepository;

/**
 * Controller for CRUD operations
 * it maps tenantid from the URL to identify the correct tenant
 * @author rakesh.singhania
 *
 */
@Controller
@RequestMapping("/{tenantid}")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;

	@RequestMapping
	public String employees(@PathVariable String tenantid, Model model) throws DatabaseCustomException {
		model.addAttribute("tenantid", tenantid);
		model.addAttribute("employee", new Employee());
		try{
		model.addAttribute("employees", employeeRepository.findAll());
		}
		catch(Exception e){
		  throw new DatabaseCustomException(
        "Unable to Process Request. "
            + e.getMessage());
		}
		return "employees";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@Transactional
	public String addEmployee(@ModelAttribute Employee employee) {
		employeeRepository.save(employee);
		return "redirect:/{tenantid}";
	}
}
