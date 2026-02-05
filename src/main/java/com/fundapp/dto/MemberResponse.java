package com.fundapp.dto;

import java.time.LocalDate;

public class MemberResponse {

    private String name;
    private String phone;
    private String role;
    private LocalDate joinDate;

    public MemberResponse(String name, String phone, String role, LocalDate joinDate) {
        this.name = name;
        this.phone = phone;
        this.role = role;
        this.joinDate = joinDate;

    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getRole() {
        return role;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

}
