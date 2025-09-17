package com.example.leavemanagement.service;

import com.example.leavemanagement.model.Leave;
import java.util.List;
import java.util.Optional;

public interface LeaveService {
    Leave applyLeave(Leave leave) throws IllegalArgumentException;
    Optional<Leave> getById(Long id);
    List<Leave> getAll();
    List<Leave> getByEmployeeId(Long employeeId);
    Leave approveLeave(Long leaveId) throws IllegalArgumentException;
    Leave rejectLeave(Long leaveId) throws IllegalArgumentException;
}
