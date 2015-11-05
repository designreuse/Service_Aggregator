package com.multitenant.repository;

import org.springframework.data.repository.CrudRepository;

import com.multitenant.domain.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Long>{

}
