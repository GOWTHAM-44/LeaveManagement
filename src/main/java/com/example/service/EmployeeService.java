package com.example.leavemanagement.service;

import com.example.leavemanagement.model.Employee;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Employee createEmployee(Employee emp);
    Optional<Employee> getById(Long id);
    List<Employee> getAll();
    Employee update(Employee emp);
    void delete(Long id);
}
