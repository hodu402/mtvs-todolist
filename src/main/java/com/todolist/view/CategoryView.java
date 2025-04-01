package com.todolist.view;

import com.todolist.model.Category;
import com.todolist.service.CategoryService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CategoryView {
    private final CategoryService categoryService;
    private final Scanner scanner;
    private final ToDoListView listView;

    public CategoryView(Connection connection) {
        this.listView = new ToDoListView(connection);
        this.categoryService = new CategoryService(connection);
        this.scanner = new Scanner(System.in);

    }
    //userview에서 받은 userid(이메일)을 받아옴
    public void showMenu(String userId) {

        while (true) {
            System.out.println("----- Category Menu 📌 -----");
            System.out.println("1. 카테고리 생성");
            System.out.println("2. 카테고리 조회");
            System.out.println("3. 카테고리 삭제");
            System.out.println("0. 이전");
            System.out.print("선택하세요: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 개행 문자 처리

            switch (choice) {
                case 1 -> createCategory(userId);
                case 2 -> getAllCategorys(userId);
                case 3 -> deleteCategory(userId);
                case 0 -> {
                    System.out.println("이전 메뉴로 되돌아갑니다");
                    return;
                }
                default -> System.out.println("잘못된 입력입니다. 다시 선택하세요.🫥");
            }
        }
    }

    //카테고리 생성
    private void createCategory(String userId){
        System.out.print("🔹카테고리 제목: ");
        String categoryName = scanner.nextLine();

        // 카테고리 객체 생성 후 서비스 클래스로 넘김
        Category category = new Category(0,0,categoryName,0,null,null);

        //카테고리 생성 실패 or 성공
        try {
            boolean success = categoryService.addCategory(category,userId);
            if (success) {
                System.out.println("카테고리 생성이 완료되었습니다.✨");
            } else {
                System.out.println("카테고리 생성이 실패했습니다.😭");
            }
        } catch (SQLException e) {
            System.out.println("카테고리 생성 중 오류가 발생했습니다.🫥");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // 해당 아이디의 모든 카테고리 조회
    private void getAllCategorys(String userId){
        try {
            //카테고리 서비스에서 모든 카테고리를 배열형식으로 받아옴
            List<String> categories = categoryService.getAllcategorys(userId);
            if (categories.isEmpty()) {
                System.out.println("카테고리가 없습니다.🗑️");
            } else {
                // 카테고리가 null이 아니고, 비어있지 않을 때만 출력하고
                // TODOLIST를 불러옴
                System.out.println("----- Category 목록 📌 -----");
                System.out.println("조회할 카테고리를 선택하세요.");
                categories.forEach(category -> {
                    System.out.println("🔹"+ category);
                });
                // 선택한 카테고리와 유저아이디를 List에 넘김
                String categoryName = scanner.nextLine();
                listView.showMenu(userId, categoryName);
            };
        } catch (SQLException e) {
            System.out.println("카테고리 목록을 조회하는 중 오류가 발생했습니다.🫥");
        }

    }

    //카테고리 삭제
    private void deleteCategory(String userId) {
        try {
            boolean success = categoryService.deleteCategory(userId);
            if (success) {
                System.out.println("카테고리가 성공적으로 삭제되었습니다.🗑️");
            } else {
                System.out.println("카테고리 삭제에 실패하였습니다.😭");
            }
        } catch (SQLException e) {
            System.out.println("카테고리 삭제 중 오류가 발생했습니다.🫥");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

}
