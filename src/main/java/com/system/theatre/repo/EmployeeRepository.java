package com.system.theatre.repo;

import com.system.theatre.model.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long>
{
}
