package com.todolist.service;

import com.todolist.dao.ToDoListDao;
import com.todolist.model.Category;
import com.todolist.model.ToDoList;
import com.todolist.util.QueryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

public class ToDoListService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final ToDoListDao toDoListDao;
    private final Connection connection;

    public ToDoListService(Connection connection) {
        this.toDoListDao = new ToDoListDao(connection);
        this.connection = connection;

    }

    // 모든 리스트 조회
    public List<ToDoList> getTodoList(String userId,String categoryName) throws SQLException {
        List<ToDoList> todoLists = toDoListDao.getTodoList(userId,categoryName);
        if( todoLists == null) {
            log.error("조회된 리스트가 없거나 오류가 발생했습니다.");
            return null;
        }
        return toDoListDao.getTodoList(userId,categoryName);
    }

    //리스트 생성
    public boolean addList(ToDoList toDoList,String userId,String categoryName) throws SQLException{
        // 중복 리스트 검사
        List<ToDoList> existingList = getTodoList(userId,categoryName);
        
        //리스트 생성시 받은 데이터와 기존 목록을 조회
        // 기존 list에 똑같은 이름이 있으면
        if (existingList.stream().anyMatch(list -> list.getTitle().equals(toDoList.getTitle()))) {
            throw new IllegalArgumentException("이미 존재하는 리스트 제목입니다");
        }
        return toDoListDao.addList(toDoList, userId,categoryName);
    }

    // 리스트 삭제
    public boolean deleteList(String userId,String categoryName) throws SQLException {
        // 모든 카테고리를 categoryDao에서 불러와야하므로 카테고리view가 아닌 service에서 처리
        List<ToDoList> todoLists = toDoListDao.getTodoList(userId,categoryName);
        System.out.println("----- 리스트 삭제 -----");
        todoLists.forEach(todoList -> System.out.println("🔹" + todoList.getTitle()));
        System.out.println("삭제할 리스트를 입력하세요: ");
        Scanner scanner = new Scanner(System.in);
        String choiceList = scanner.nextLine();
        
        //리스트의 모든항목을 불러와서 타이틀만 초이스와 비교
        //기존 리스트에 내가 삭제하려는 이름이 없으면
        if (todoLists.stream().noneMatch(toDoList -> toDoList.getTitle().contains(choiceList))) {
            throw new IllegalArgumentException("삭제할 리스트를 잘못 입력했습니다");
        }

        return toDoListDao.deleteList(userId,categoryName,choiceList);
    }
    
    //리스트 체크
    public boolean checkList(String userId,String categoryName,String checkList) throws SQLException {
        List<ToDoList> todoLists = toDoListDao.getTodoList(userId,categoryName);

        // 리스트에 내가 체크하고 싶은 리스트가 없으면
        if (todoLists.stream().noneMatch(toDoList -> toDoList.getTitle().contains(checkList))) {
            throw new IllegalArgumentException("체크할 리스트를 잘못 입력했습니다");
        }
        return toDoListDao.checkList(userId,categoryName,checkList);
    }









}
