package com.todolist.service;

import com.todolist.config.JDBCConnection;
import com.todolist.model.User;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {

    private static final Logger log = LoggerFactory.getLogger(UserServiceTest.class);
    private Connection connection;
    private UserService userService;

    private static final String TEST_USER_EMAIL = "TEST_USER_EMAIL";
    private static final String TEST_USER_NAME = "TEST_USER_NAME";
    private static final String TEST_USER_PASSWORD = "TEST_USER_PASSWORD";
    private static final LocalDateTime TEST_REMOVE_DATE = null;

    // 테스트 전에 db연결, 트랜잭션 설정
    @BeforeEach
    void setUp() throws SQLException {
            connection = JDBCConnection.getConnection();
            connection.setAutoCommit(false);
            userService = new UserService(connection);

            // 준비(Arrange), 테스트용 데이터 삽입
          userService.registerUser(new User(TEST_USER_NAME,0, TEST_USER_PASSWORD,"Y",TEST_USER_EMAIL,null,TEST_REMOVE_DATE,0));
          log.info("테스트용 역할 추가 완료");
    }

    @Test
    @DisplayName("유저 조회 테스트 (정상 케이스)")
    void getUserByEmail() {

        User user = null;
        try{
            user = userService.getUserByEmail(TEST_USER_EMAIL);
        } catch (SQLException e) {
            Assertions.fail("SQL 실행 오류 발생 : " + e.getMessage());
        }

        //Assert
        Assertions.assertNotNull(user,"유저가 존재해야 합니다.");
        Assertions.assertEquals(TEST_USER_EMAIL, user.getEmail(), "이메일이 일치해야 합니다.");
        Assertions.assertEquals(TEST_USER_NAME, user.getUserName(), "이름이 일치해야 합니다.");
    }


    @Test
    @DisplayName("존재하지 않는 유저 조회 테스트 (예외 발생)")
    void testGetUserByEmail_NotFound() {
        // Act & Assert
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.getUserByEmail("ddd"); // 존재하지 않는 ID 조회
        });

        // 존재하지 않는 ID를 조회했을 때 나오는 메세지와 아래 메세지가 같아야함
        Assertions.assertEquals("해당 ID의 사용자를 찾을 수 없습니다.", exception.getMessage());
    }

    
    
    // 테스트가 끝나고 롤백
    @AfterEach
    void tearDown() {
        try {
            connection.rollback(); // 테스트 후 데이터 롤백
            connection.setAutoCommit(true); // 원래 상태로 변경
            connection.close();
            log.info("테스트 종료 후 데이터 롤백 완료");
        } catch (SQLException e) {
            log.error("테스트 종료 중 오류 발생: " + e.getMessage());
        }
    }
}