package com.fundapp.dto;

public class UserProfileResponse {

    private String name;
    private String phone;
    private String role;

    public UserProfileResponse(
            String name,
            String phone,
            String role) {

        this.name = name;
        this.phone = phone;
        this.role = role;
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
}