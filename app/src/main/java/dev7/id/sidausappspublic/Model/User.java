package dev7.id.sidausappspublic.Model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("username") private String username;
    @SerializedName("password") private String password;
    @SerializedName("token") private String token;
    @SerializedName("email") private String email;

    public User() {
    }

    public User(String username, String password, String token, String email) {
        this.username = username;
        this.password = password;
        this.token = token;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
