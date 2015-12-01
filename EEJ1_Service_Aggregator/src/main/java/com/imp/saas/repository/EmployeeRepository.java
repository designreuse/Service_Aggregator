package com.imp.saas.repository;

import org.springframework.data.repository.CrudRepository;

import com.imp.saas.domain.Employee;

/**
 * Interface for CRUD operations
 * @author rakesh.singhania
 *
 */
public interface EmployeeRepository extends CrudRepository<Employee, Long>{

}
