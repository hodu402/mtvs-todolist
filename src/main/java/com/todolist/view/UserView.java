package com.todolist.view;
import com.todolist.config.JDBCConnection;
import com.todolist.model.Category;
import com.todolist.model.User;
import com.todolist.service.UserService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserView {

    private final UserService userService;
    private final Scanner scanner;


    public UserView(Connection connection) {
        this.userService = new UserService(connection);
        this.scanner = new Scanner(System.in);
    }

    // 로그인 view
    public void userLogin(){
        System.out.print("🔹회원 ID를 입력하세요: ");
        String userId = scanner.nextLine();
        System.out.print("🔹회원 비밀번호를 입력하세요: ");
        String password = scanner.nextLine();

        try {
            // 사용자에게 받은 아이디와 비밀번호를 userService 전달
            boolean success = userService.userLogin(userId,password);
            if (success) {
                System.out.println("로그인 되었습니다.✨");
                // 카테고리 관리 목록으로 넘어감, 로그인 할때 받은 id(이메일)을 카테고리로 넘김
                Connection connection = JDBCConnection.getConnection();
                CategoryView categoryView = new CategoryView(connection);
                categoryView.showMenu(userId);

            } else {
                System.out.println("비밀번호를 잘못입력했습니다.😔");
            }
        } catch (SQLException e) {
            System.out.println("로그인 중 오류가 발생했습니다.🫥");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    //회원가입 view
    public void registerUser() {
        System.out.println("----- 회원가입 📌 -----");
        System.out.print("🔹사용자 이름: ");
        String username = scanner.nextLine();

        System.out.print("🔹이메일: ");
        String email = scanner.nextLine();

        System.out.print("🔹비밀번호: ");
        String password = scanner.nextLine();

    // user 클래스를 생성 한 후 입력한 회원 정보를 넣어준 후, userService로 전달
        User user = new User(username,0, password, "Y",email, null,null, 0 );
        try {
            // 가입이 완료되면 ture, 완료되지 않으면 false를 반환
            boolean success = userService.registerUser(user);
            if (success) {
                System.out.println("회원가입이 완료되었습니다.✨");
            } else {
                System.out.println("회원가입에 실패했습니다.😭");
            }
        } catch (SQLException e) {
            System.out.println("사용자 등록 중 오류가 발생했습니다.🫥");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }



}
