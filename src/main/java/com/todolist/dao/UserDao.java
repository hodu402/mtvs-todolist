package com.todolist.dao;

import com.todolist.model.User;
import com.todolist.util.QueryUtil;

import java.sql.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private final Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    //모든 유저 조회
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = QueryUtil.getQuery("getAllUsers");

       // rs.next() : 다음 행이 있으면 true, 없으면 false
        try(Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query)){
            while (rs.next()){
                users.add(new User(
                        rs.getString("user_name"),
                        rs.getInt("user_id"),
                        rs.getString("pass"),
                        rs.getString("status"),
                        rs.getString("email"),
                        rs.getTimestamp("create_date").toLocalDateTime(),
                        (rs.getTimestamp("remove_date") != null) ? rs.getTimestamp("remove_date").toLocalDateTime() : null,
                        rs.getInt("is_delete")
                ));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return users;
    }


    // 단일 사용자 조회
    // userId를 받아서 테이블과 조회 후, userId가 존재하는 행의 모든 정보를 받아옴
    public User getUserByEmail(String email) {
        String query = QueryUtil.getQuery("getUserByEmail");
        User user = null;

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User(
                        rs.getString("user_name"),
                        rs.getInt("user_id"),
                        rs.getString("pass"),
                        rs.getString("status"),
                        rs.getString("email"),
                        rs.getTimestamp("create_date").toLocalDateTime(),
                        (rs.getTimestamp("remove_date") != null) ? rs.getTimestamp("remove_date").toLocalDateTime() : null,
                        rs.getInt("is_delete")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


    //유저 등록
    public boolean addUser(User user) {
        String query = QueryUtil.getQuery("addUser");

        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPass());
            if (user.getRemoveDate() != null) {
                ps.setTimestamp(4, Timestamp.valueOf(user.getRemoveDate()));
            } else {
                ps.setNull(4, java.sql.Types.TIMESTAMP);
            }
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
