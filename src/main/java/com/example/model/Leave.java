package com.example.leavemanagement.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "leaves")
public class Leave {

    public enum Status {
        PENDING, APPROVED, REJECTED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // who applied
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    // number of days requested (calculated or provided)
    @Column(nullable = false)
    private Integer days;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDING;

    @Column(length = 500)
    private String reason;

    public Leave() {}

    public Leave(Employee employee, LocalDate startDate, LocalDate endDate, Integer days, String reason) {
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.days = days;
        this.reason = reason;
        this.status = Status.PENDING;
    }

    public Long getId() {
        return id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
