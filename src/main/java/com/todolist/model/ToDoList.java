package com.todolist.model;

import java.time.LocalDateTime;

public class ToDoList {
    private int listId;
    private String title;
    private LocalDateTime crateDate;
    private LocalDateTime updateDate;
    private LocalDateTime removeDate;
    private int isCompleted;
    private int isDelete;
    private int categoryId;

    public ToDoList(int listId, String title, LocalDateTime crateDate, LocalDateTime updateDate, LocalDateTime removeDate, int isCompleted, int isDelete, int categoryId) {
        this.listId = listId;
        this.title = title;
        this.crateDate = crateDate;
        this.updateDate = updateDate;
        this.removeDate = removeDate;
        this.isCompleted = isCompleted;
        this.isDelete = isDelete;
        this.categoryId = categoryId;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCrateDate() {
        return crateDate;
    }

    public void setCrateDate(LocalDateTime crateDate) {
        this.crateDate = crateDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public LocalDateTime getRemoveDate() {
        return removeDate;
    }

    public void setRemoveDate(LocalDateTime removeDate) {
        this.removeDate = removeDate;
    }

    public int getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(int isCompleted) {
        this.isCompleted = isCompleted;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
