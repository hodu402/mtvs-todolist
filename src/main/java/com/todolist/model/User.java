package com.todolist.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class User {
    private int userId;
    private String pass;
    private String status;
    private String email;
    private LocalDateTime createDate;
    private LocalDateTime removeDate;
    private int isDelete ;
    private String userName;

    public User(String userName, int userId, String pass, String status, String email, LocalDateTime createDate, LocalDateTime removeDate, int isDelete) {
        this.userId = userId;
        this.pass = pass;
        this.status = status;
        this.email = email;
        this.createDate = createDate;
        this.removeDate = removeDate;
        this.isDelete = isDelete;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getRemoveDate() {
        return removeDate;
    }

    public void setRemoveDate(LocalDateTime removeDate) {
        this.removeDate = removeDate;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", pass='" + pass + '\'' +
                ", status='" + status + '\'' +
                ", email='" + email + '\'' +
                ", createDate=" + createDate +
                ", removeDate=" + removeDate +
                ", isDelete=" + isDelete +
                ", userName='" + userName + '\'' +
                '}';
    }
}
