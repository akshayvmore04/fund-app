package com.fundapp.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "fund_members")
public class FundMember {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

private String role;
private LocalDate joinDate;

@ManyToOne
@JoinColumn(name = "fund_id")
private Fund fund;

@ManyToOne 
@JoinColumn(name = "user_id")
private User user;

public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public String getRole() {
    return role;
}

public void setRole(String role) {
    this.role = role;
}

public LocalDate getJoinDate() {
    return joinDate;
}

public void setJoinDate(LocalDate joinDate) {
    this.joinDate = joinDate;
}

public Fund getFund() {
    return fund;
}

public void setFund(Fund fund) {
    this.fund = fund;
}

public User getUser() {
    return user;
}

public void setUser(User user) {
    this.user = user;
}

}
