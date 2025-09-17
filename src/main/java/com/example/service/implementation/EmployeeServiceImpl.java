package com.example.leavemanagement.service.impl;

import com.example.leavemanagement.model.Employee;
import com.example.leavemanagement.repository.EmployeeRepository;
import com.example.leavemanagement.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee createEmployee(Employee emp) {
        if (emp.getLeaveBalance() == null) {
            emp.setLeaveBalance(0);
        }
        return employeeRepository.save(emp);
    }

    @Override
    public Optional<Employee> getById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee update(Employee emp) {
        return employeeRepository.save(emp);
    }

    @Override
    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }
}
