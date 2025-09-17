package com.example.leavemanagement.repository;

import com.example.leavemanagement.model.Leave;
import com.example.leavemanagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LeaveRepository extends JpaRepository<Leave, Long> {
    List<Leave> findByEmployee(Employee employee);
}
