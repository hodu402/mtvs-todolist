package com.todolist.model;

import java.time.LocalDateTime;

public class Category {
    private int categoryId;
    private int userId;
    private String categoryName;
    private int isDelete;
    private LocalDateTime removeDate;
    private LocalDateTime createDate;

    public Category(int categoryId, int userId, String categoryName, int isDelete,LocalDateTime removeDate, LocalDateTime createDate) {
        this.categoryId = categoryId;
        this.userId = userId;
        this.categoryName = categoryName;
        this.isDelete = isDelete;
        this.removeDate = removeDate;
        this.createDate = createDate;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public LocalDateTime getRemoveDate() {
        return removeDate;
    }

    public void setRemoveDate(LocalDateTime removeDate) {
        this.removeDate = removeDate;
    }
}


