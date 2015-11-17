package com.imp.saas.repository;

import org.springframework.data.repository.CrudRepository;

import com.imp.saas.domain.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Long>{

}
