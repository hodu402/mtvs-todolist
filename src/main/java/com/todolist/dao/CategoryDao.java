package com.todolist.dao;

import com.todolist.model.Category;
import com.todolist.model.User;
import com.todolist.util.QueryUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {

    private final Connection connection;

    public CategoryDao(Connection connection) {
        this.connection = connection;
    }

    //유저에 따른 모든 카테고리 조회
    public List<String> getAllcategorys(String userId) {
        String query = QueryUtil.getQuery("getAllCategorys");
        //모든 카테고리 명을 배열로 담음
        List<String> categoryList = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            //행이 존재하면 카테고리 리스트에 계속 카테고리 이름을 추가
            while (rs.next()) {
                categoryList.add(rs.getString("category_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryList;
    }



    // 카테고리 추가
    public boolean addCategory(Category category,String userId) {
        String query = QueryUtil.getQuery("addCategory");
        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1,category.getCategoryName());
            ps.setString(2,userId);


            int affectedRows = ps.executeUpdate(); // 실행된 row 수
            return affectedRows > 0; // row가 1개 이상 추가되면 true

        } catch (SQLException e) {
            e.printStackTrace(); //예외 발생 시 출력
        }
        return false; // 예외 발생시 false (e.printStackTrace()가 출력되고 false반환)
    }

    //카테고리 삭제
    public boolean deleteCategory(String categoryName,String userId) {
        String query = QueryUtil.getQuery("deleteCategory");
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, userId);
            ps.setString(2, categoryName);


            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
