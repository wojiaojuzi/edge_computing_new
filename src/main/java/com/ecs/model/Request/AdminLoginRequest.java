package com.ecs.model.Request;

import lombok.Data;

@Data
public class AdminLoginRequest {
    private String adminId;
    private String password;

    public AdminLoginRequest(String adminId, String password) {
        this.adminId = adminId;
        this.password = password;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
