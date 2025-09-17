package com.example.leavemanagement.controller;

import com.example.leavemanagement.model.Leave;
import com.example.leavemanagement.model.Employee;
import com.example.leavemanagement.service.LeaveService;
import com.example.leavemanagement.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {

    private final LeaveService leaveService;
    private final EmployeeService employeeService;

    public LeaveController(LeaveService leaveService, EmployeeService employeeService){
        this.leaveService = leaveService;
        this.employeeService = employeeService;
    }

    // apply leave - expects JSON with employee.id, startDate, endDate, optional days and reason
    @PostMapping("/apply")
    public ResponseEntity<?> applyLeave(@RequestBody Leave leave) {
        try {
            // attach full employee if only id provided
            if (leave.getEmployee() != null && leave.getEmployee().getId() != null) {
                employeeService.getById(leave.getEmployee().getId()).ifPresent(leave::setEmployee);
            }
            Leave saved = leaveService.applyLeave(leave);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Leave> get(@PathVariable Long id) {
        return leaveService.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Leave>> all() {
        return ResponseEntity.ok(leaveService.getAll());
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Leave>> forEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(leaveService.getByEmployeeId(employeeId));
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<?> approve(@PathVariable Long id) {
        try {
            Leave l = leaveService.approveLeave(id);
            return ResponseEntity.ok(l);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<?> reject(@PathVariable Long id) {
        try {
            Leave l = leaveService.rejectLeave(id);
            return ResponseEntity.ok(l);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
