package com.fundapp.dto;

public class DefaulterResponse {

    private String name;
    private String phone;

    public DefaulterResponse(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() { return name; }
    public String getPhone() { return phone; }
}
