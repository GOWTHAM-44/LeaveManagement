package com.example.leavemanagement.controller;

import com.example.leavemanagement.model.Employee;
import com.example.leavemanagement.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        Employee saved = employeeService.createEmployee(employee);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> get(@PathVariable Long id) {
        return employeeService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Employee>> list() {
        return ResponseEntity.ok(employeeService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable Long id, @RequestBody Employee body) {
        return employeeService.getById(id).map(existing -> {
            existing.setName(body.getName());
            existing.setEmail(body.getEmail());
            existing.setLeaveBalance(body.getLeaveBalance());
            Employee updated = employeeService.update(existing);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
