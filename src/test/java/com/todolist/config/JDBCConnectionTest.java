package com.todolist.config;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class JDBCConnectionTest {
    //테스트 시작 전
    @BeforeAll
    static void setUp() {
        System.out.println("테스트 시작");
    }
    //테스트
    @Test
    void testConnection() throws SQLException {
        //JDBCConnection.getConnection()으로 DB 연결을 실제로 시도
        try(Connection connection = JDBCConnection.getConnection()){
            Assertions.assertNotNull(connection,"커넥션이 생성되지 않았습니다");
            Assertions.assertFalse(connection.isClosed(), "커넥션은 열려있어야합니다");
            System.out.println("DB 커넥션이 연결되었습니다");
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    static void tearDown() {
        System.out.println("테스트 종료 및 자원 반납");
        JDBCConnection.close();
    }
}