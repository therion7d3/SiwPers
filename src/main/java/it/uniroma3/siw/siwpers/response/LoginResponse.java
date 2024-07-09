package it.uniroma3.siw.siwpers.response;

import it.uniroma3.siw.siwpers.model.Role;

import java.util.List;

public class LoginResponse {
    private String token;
    private String message;
    private List<Role> roles;
    private boolean success;
    private String username;
    private Long userId;

    public LoginResponse(String token, String message, boolean success, List<Role> roles, String username, Long userId) {
        this.token = token;
        this.message = message;
        this.success = success;
        this.roles = roles;
        this.username = username;
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Role> getRole(){
        return roles;
    }

    public void setRole(List<Role> roles){
        this.roles = roles;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

