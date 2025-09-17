package com.example.leavemanagement.model;

import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    // remaining paid leave balance (simple integer days)
    @Column(nullable = false)
    private Integer leaveBalance;

    public Employee() {}

    public Employee(String name, String email, Integer leaveBalance) {
        this.name = name;
        this.email = email;
        this.leaveBalance = leaveBalance;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getLeaveBalance() {
        return leaveBalance;
    }

    public void setLeaveBalance(Integer leaveBalance) {
        this.leaveBalance = leaveBalance;
    }
}
