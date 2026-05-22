package com.fundapp.dto;

public class JoinRequestResponse {

    private Long requestId;

    private String userName;

    private String phone;

    private String fundName;

    public JoinRequestResponse(
            Long requestId,
            String userName,
            String phone,
            String fundName) {

        this.requestId = requestId;
        this.userName = userName;
        this.phone = phone;
        this.fundName = fundName;
    }

    public Long getRequestId() {
        return requestId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPhone() {
        return phone;
    }

    public String getFundName() {
        return fundName;
    }
}