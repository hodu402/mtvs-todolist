package com.todolist;


import com.todolist.config.JDBCConnection;
import com.todolist.dao.UserDao;
import com.todolist.view.UserView;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws SQLException {
        Connection connection = JDBCConnection.getConnection();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("----- TODO LIST ✏️ -----");
            System.out.println("1. 로그인");
            System.out.println("2. 회원가입");
            System.out.println("0. 종료");
            System.out.print("선택: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 개행 문자 처리

            switch (choice) {
                // case 1
                case 2 -> startUserManagement(connection);
                case 0 -> {
                    connection.close();
                    System.out.println(" 프로그램을 종료합니다.😔");
                    return;
                }
                default -> System.out.println("❌ 잘못된 입력입니다. 다시 선택하세요.");
            }
        }

    }

    private static void startUserManagement(Connection connection) {
        UserView userView = new UserView(connection);
        userView.registerUser();
    };


}

