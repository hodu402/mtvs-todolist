package com.todolist.service;
import com.todolist.dao.CategoryDao;
import com.todolist.dao.UserDao;
import com.todolist.model.Category;
import com.todolist.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserDao userDao;
    private final Connection connection;

    public UserService(Connection connection) {
        this.connection = connection;
        this.userDao = new UserDao(connection);
    }

    //모든 사용자 조회
    public List<User> getAllUsers() throws SQLException {
        List<User> users = userDao.getAllUsers();

        if(users == null) {
            log.error("조회한 사용자의 정보가 없거나 DB와 연결하는 과정에서 오류가 발생했습니다.");
            return null;
        }

        return userDao.getAllUsers();
    }

    //단일 사용자 조회
    public User getUserByEmail(String email) throws SQLException {
        User user = userDao.getUserByEmail(email);

        if (user == null) {
            throw new IllegalArgumentException("해당 ID의 사용자를 찾을 수 없습니다.");
        }
        return user;
    }

    //로그인
    public boolean userLogin(String userId,String password) throws SQLException {
        User existingUser = getUserByEmail(userId);
        if (existingUser == null) {
            return false;
        } else if(existingUser.getPass().equals(password)) {
            return true;
        }
        return false; // 비밀번호가 틀린 경우
    }

    //회원가입
    public boolean registerUser(User user) throws SQLException {
        // 중복 이메일 검사
        List<User> existingUsers = getAllUsers();
        for (User u : existingUsers) {
            if (u.getEmail().equals(user.getEmail())) {
                throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
            }
        }
        return userDao.addUser(user);
    }


}
