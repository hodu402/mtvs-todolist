package com.todolist.dao;

import com.todolist.config.JDBCConnection;
import com.todolist.model.User;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS) //클래스 단위로 인스턴스 유지
class UserDaoTest {

    private Connection connection;
    private UserDao userDao;

    //테스트 전 실행 설정
    // - connection 생성 및 트랜젝션 시작, 테스트용 데이터 삽입

    @BeforeAll
    void setUp() {
        try{
            connection = JDBCConnection.getConnection();
            userDao = new UserDao(connection);

        } catch (SQLException e){
            Assertions.fail("데이터 베이스 연결 실패");
        }
    }

    @Test
    @DisplayName("user email로 유저 정보 조회")
    void getUserByEmail() {
        User user = userDao.getUserByEmail("Anne@naver.com");
        System.out.println("유저의 정보" + user);

        //Assert 검증
        Assertions.assertNotNull(user, "유저의 정보가 없습니다");
        Assertions.assertEquals("Anne@naver.com",user.getEmail(), "삽입한 이메일과 조회된 이메일이 일치해야합니다");
        Assertions.assertEquals("Anne" , user.getUserName(), "조회된 유저의 이름이 일치해야합니다");
    }
}