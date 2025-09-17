package com.example.leavemanagement.service.impl;

import com.example.leavemanagement.model.Leave;
import com.example.leavemanagement.model.Employee;
import com.example.leavemanagement.repository.LeaveRepository;
import com.example.leavemanagement.repository.EmployeeRepository;
import com.example.leavemanagement.service.LeaveService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class LeaveServiceImpl implements LeaveService {

    private final LeaveRepository leaveRepository;
    private final EmployeeRepository employeeRepository;

    public LeaveServiceImpl(LeaveRepository leaveRepository, EmployeeRepository employeeRepository) {
        this.leaveRepository = leaveRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public Leave applyLeave(Leave leave) throws IllegalArgumentException {
        if (leave.getEmployee() == null || leave.getEmployee().getId() == null) {
            throw new IllegalArgumentException("Employee must be set with valid id.");
        }

        Optional<Employee> empOpt = employeeRepository.findById(leave.getEmployee().getId());
        if (empOpt.isEmpty()) {
            throw new IllegalArgumentException("Employee not found.");
        }
        Employee emp = empOpt.get();

        // Calculate days if not provided (inclusive)
        if (leave.getDays() == null) {
            long daysBetween = ChronoUnit.DAYS.between(leave.getStartDate(), leave.getEndDate()) + 1;
            leave.setDays((int) daysBetween);
        }

        // Simple balance check (only allow if enough balance)
        if (leave.getDays() > emp.getLeaveBalance()) {
            throw new IllegalArgumentException("Insufficient leave balance.");
        }

        // Save leave in PENDING state
        leave.setStatus(Leave.Status.PENDING);
        Leave saved = leaveRepository.save(leave);

        // Do NOT deduct balance until approved (common workflow). If you prefer to deduct on apply, uncomment:
        // emp.setLeaveBalance(emp.getLeaveBalance() - saved.getDays());
        // employeeRepository.save(emp);

        return saved;
    }

    @Override
    public Optional<Leave> getById(Long id) {
        return leaveRepository.findById(id);
    }

    @Override
    public List<Leave> getAll() {
        return leaveRepository.findAll();
    }

    @Override
    public List<Leave> getByEmployeeId(Long employeeId) {
        Optional<Employee> empOpt = employeeRepository.findById(employeeId);
        return empOpt.map(leaveRepository::findByEmployee).orElse(List.of());
    }

    @Override
    @Transactional
    public Leave approveLeave(Long leaveId) throws IllegalArgumentException {
        Leave leave = leaveRepository.findById(leaveId).orElseThrow(() -> new IllegalArgumentException("Leave not found"));
        if (leave.getStatus() == Leave.Status.APPROVED) {
            return leave;
        }

        Employee emp = leave.getEmployee();
        if (leave.getDays() > emp.getLeaveBalance()) {
            throw new IllegalArgumentException("Employee has insufficient leave balance to approve.");
        }

        // Deduct balance and approve
        emp.setLeaveBalance(emp.getLeaveBalance() - leave.getDays());
        employeeRepository.save(emp);

        leave.setStatus(Leave.Status.APPROVED);
        return leaveRepository.save(leave);
    }

    @Override
    @Transactional
    public Leave rejectLeave(Long leaveId) throws IllegalArgumentException {
        Leave leave = leaveRepository.findById(leaveId).orElseThrow(() -> new IllegalArgumentException("Leave not found"));
        if (leave.getStatus() == Leave.Status.REJECTED) {
            return leave;
        }
        leave.setStatus(Leave.Status.REJECTED);
        return leaveRepository.save(leave);
    }
}
