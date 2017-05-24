package com.esi.mxt07.pfe2017.alpr;

public class User {
    private int id;
    private String username;
    private String passwordSha256;
    private boolean isAdmin;

    public User(int id, String username, String passwordSha256, boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.passwordSha256 = passwordSha256;
        this.isAdmin = isAdmin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordSha256() {
        return passwordSha256;
    }

    public void setPasswordSha256(String passwordSha256) {
        this.passwordSha256 = passwordSha256;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
