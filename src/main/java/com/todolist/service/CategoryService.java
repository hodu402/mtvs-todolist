package com.todolist.service;


import com.todolist.dao.CategoryDao;
import com.todolist.model.Category;
import com.todolist.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CategoryService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final CategoryDao categoryDao;
    private final Connection connection;

    public CategoryService(Connection connection) {
        this.connection = connection;
        this.categoryDao = new CategoryDao(connection);
    }

    // 모든 카테고리 조회
    public List<String> getAllcategorys(String userId) throws SQLException {
        List<String> categories = categoryDao.getAllcategorys(userId);
    //카테고리가 하나도 없을때
        if(categories == null) {
            log.error("생성된 카테고리가 없습니다.");
            return null;
        }
        return categoryDao.getAllcategorys(userId);
    }

 // 카테고리 생성
    public boolean addCategory(Category category,String userId) throws SQLException{
        // 중복 카테고리 검사
        List<String> existingCategorys = getAllcategorys(userId);
        for (String u : existingCategorys) {
            if (u.equals(category.getCategoryName())) {
                throw new IllegalArgumentException("이미 존재하는 카테고리입니다.");
            }
        }
        return categoryDao.addCategory(category, userId);
    }


    //카테고리 삭제
    public boolean deleteCategory(String userId) throws SQLException {
        // 모든 카테고리를 categoryDao에서 불러와야하므로 카테고리view가 아닌 service에서 처리
        List<String> categories = categoryDao.getAllcategorys(userId);
        System.out.println("----- 카테고리 삭제 -----");
        categories.forEach(category -> System.out.println("🔹" + category));
        System.out.println("삭제할 카테고리 명을 입력하세요:");

        Scanner scanner = new Scanner(System.in);
        String categoryNameChoice = scanner.nextLine();

        // 입력한 카테고리가 목록에 없는 경우 예외 발생
        if (!categories.contains(categoryNameChoice)) {
            throw new IllegalArgumentException("삭제할 카테고리를 찾을 수 없습니다.");
        }

        return categoryDao.deleteCategory(categoryNameChoice, userId);
    }


}
