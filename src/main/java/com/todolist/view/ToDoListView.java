package com.todolist.view;

import com.todolist.model.ToDoList;
import com.todolist.service.ToDoListService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ToDoListView {

    private final ToDoListService listService;
    private final Scanner scanner;

    public ToDoListView(Connection connection) {
        this.listService = new ToDoListService(connection);
        this.scanner = new Scanner (System.in);
    }

    public void showMenu(String userId, String categoryName) {

        while (true) {
            System.out.println( "----- "+ categoryName + " Category -----");
            System.out.println("----- List Menu ✏️ -----");
            System.out.println("1. List 조회");
            System.out.println("2. List 생성");
            System.out.println("3. List 삭제");
            System.out.println("0. 이전");
            System.out.print("선택하세요: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 개행 문자 처리

            switch (choice) {
               case 1 -> getTodoList(userId,categoryName);
               case 2 -> createList(userId,categoryName);
               case 3 -> deleteList(userId,categoryName);
                case 0 -> {
                    System.out.println("이전 메뉴로 되돌아갑니다");
                    return;
                }
                default -> System.out.println("잘못된 입력입니다. 다시 선택하세요.🫥");
            }
        }
    }

    //모든 리스트 조회
    private void getTodoList(String userId,String categoryName){
        try {
            //리스트 서비스에서 모든 리스트를 배열형식으로 받아옴
            List<ToDoList> todoLists = listService.getTodoList(userId,categoryName);
            if (todoLists.isEmpty()) {
                System.out.println("생성된 리스트가 없습니다.🗑️");
            } else {
                // null이거나 비어있지 않은 리스트들을 관리
                System.out.println("----- "+ categoryName + " Category -----");
                System.out.println("----- ToDo List ✏️ -----");
                //조회할때 체크여부에 따라 도형 바뀌게!!!
                todoLists.forEach( todoList -> {
                    if(todoList.getIsCompleted() == 1){
                        System.out.println("✅ "+todoList.getTitle());
                    }
                    else { System.out.println("🟪 "+todoList.getTitle());}
                });
                System.out.println("체크/체크해제 할 항목을 선택해주세요 ☑️");
                String choiceCheck = scanner.nextLine();
                // 체크하는 로직 추가!
                checkList(userId,categoryName,choiceCheck);

            };
        } catch (SQLException e) {
            System.out.println("리스트 목록을 조회하는 중 오류가 발생했습니다.🫥");
        }

    }

    //리스트 생성
    private void createList(String userId,String categoryName) {
        System.out.println("생성할 리스트를 작성해주세요.");
        System.out.print("List : ");
        String listTitle = scanner.nextLine();
        
        ToDoList toDoList = new ToDoList(0, listTitle, null, null, null, 0, 0, 0);
        try {
            boolean success = listService.addList(toDoList,userId,categoryName);
            if (success) {
                System.out.println("리스트가 등록되었습니다.✨");
            } else {
                System.out.println("리스트 등록에 실패하였습니다.😭");
            }
        } catch (SQLException e) {
            System.out.println("리스트 등록 중 오류가 발생했습니다.🫥");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

    //리스트 삭제
    private void deleteList(String userId,String categoryName) {
        try {
            //Service에서 처리
            boolean success = listService.deleteList(userId,categoryName);
            if (success) {
                System.out.println("리스트가 삭제되었습니다.🗑️");
            } else {
                System.out.println("리스트 삭제에 실패하였습니다.😭");
            }
        } catch (SQLException e) {
            System.out.println("리스트 삭제 중 오류가 발생했습니다.🫥");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }


    //리스트 체크
    private void checkList(String userId,String categoryName,String checkList) {
        try {
            //Service에서 처리
            boolean success = listService.checkList(userId,categoryName,checkList);
            if (success) {
                System.out.println("리스트 체크여부를 수정했습니다☑️");
            } else {
                System.out.println("리스트 체크/체크해제에 실패하였습니다.😭");
            }
        } catch (SQLException e) {
            System.out.println("리스트 체크 수정 중 오류가 발생했습니다.🫥");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }




}
