package com.system.theatre.repo;

import com.system.theatre.model.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Long>
{
    public List<Employee> findByPostNameAndOntour(String name, boolean ontour);
    public List<Employee> findByLastnameOrFirstnameOrMiddlename(String lastname, String firstname, String middlename);
    public List<Employee> findByLastnameOrFirstnameOrMiddlenameContains(String lastname, String firstname, String middlename);
}
