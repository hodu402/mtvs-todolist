package com.todolist.dao;

import com.todolist.model.Category;
import com.todolist.model.ToDoList;
import com.todolist.util.QueryUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ToDoListDao {

    private final Connection connection;

    public ToDoListDao (Connection connection) {
        this.connection = connection;
    }


    // todolist 조회
    //
    public List<ToDoList> getTodoList(String userId,String categoryName) {

        String query = QueryUtil.getQuery("getTodoList"); // XML에서 쿼리 로드
        List<ToDoList> lists = new ArrayList<>();
        // 파라미터가 있는 쿼리를 준비 prepareStatement
        try (PreparedStatement ps = connection.prepareStatement(query)){
             ps.setString(1, userId);
             ps.setString(2, categoryName);
            // executeQuery()로 ps.set으로 셋팅한 쿼리 결과를 받아옴
             ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lists.add(new ToDoList(
                        rs.getInt("list_id"),
                        rs.getString("title"),
                        rs.getTimestamp("create_date").toLocalDateTime(),
                        (rs.getTimestamp("update_date") != null) ? rs.getTimestamp("update_date").toLocalDateTime() : null,
                        (rs.getTimestamp("remove_date") != null) ? rs.getTimestamp("remove_date").toLocalDateTime() : null,
                        rs.getInt("is_completed"),
                        rs.getInt("is_delete"),
                        rs.getInt("category_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lists;
    }

    //todolist 생성
    public boolean addList(ToDoList toDoList, String userId,String categoryName) {
        String query = QueryUtil.getQuery("addTodoList");
        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1,toDoList.getTitle());
            if (toDoList.getUpdateDate() != null) {
                ps.setTimestamp(2, Timestamp.valueOf(toDoList.getUpdateDate()));
            } else {
                ps.setNull(2, java.sql.Types.TIMESTAMP);
            }
            if (toDoList.getRemoveDate() != null) {
                ps.setTimestamp(3, Timestamp.valueOf(toDoList.getRemoveDate()));
            } else {
                ps.setNull(3, java.sql.Types.TIMESTAMP);
            }
            ps.setString(4,userId);
            ps.setString(5,categoryName);

            int affectedRows = ps.executeUpdate(); // 실행된 row 수
            return affectedRows > 0; // row가 1개 이상 추가되면 true

        } catch (SQLException e) {
            e.printStackTrace(); //예외 발생 시 출력
        }
        return false; // 예외 발생시 false (e.printStackTrace()가 출력되고 false반환)
    }

    //todolist 삭제
    public boolean deleteList(String userId,String categoryName,String choiceList) {
        String query = QueryUtil.getQuery("deleteList");
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, userId);
            ps.setString(2, categoryName);
            ps.setString(3, choiceList);

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //todolist 체크
    public boolean checkList(String userId,String categoryName,String checkList) {
        String query = QueryUtil.getQuery("checkList");
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, userId);
            ps.setString(2, categoryName);
            ps.setString(3, checkList);

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }




}
