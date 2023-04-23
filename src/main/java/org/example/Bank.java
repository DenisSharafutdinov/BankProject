package org.example;

import java.util.ArrayList;
import java.util.Scanner;

class Bank {
    String bankName = "(NIBS) New International Bank System";
    ArrayList<User> users = new ArrayList<>();
 //   static ArrayList<Admin> admins = new ArrayList<>();
    User activeUser;
   // static int option;
    static int nextUserId = 1;
    public static final String ANSI_RESET = "\u001B[0m"; // Сброс цвета
    public static final String GREEN = "\u001B[32m"; // Зелёный цвет

    public  int generateUserId() {
        return nextUserId++;
    }

    public  void deleteAccount(String userName) {
        User user = findUser(userName);
        if (user != null) {
            users.remove(user);
            System.out.println(GREEN + "Аккаунт " + userName + " успешно удалён!" + ANSI_RESET);
        }
        else {
            System.out.println("Аккаунт " + userName+ " не зарегистрирован в системе");
        }
    }

     boolean login() {

        Scanner scanner = new Scanner(System.in);
        System.out.print("\033[H\033[J");
        System.out.print("\nПожалуйста, введите ваше имя: ");
        String userName = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        // Проверка, если пользователь вышел
        User user = findUser(userName);
        if (user == null) {
            System.out.println("Аккаунт " + userName+ " не зарегистрирован в системе");
            return false;
        }

        // Проверка, если пароль неверный
        if (!user.password.equals(password)) {
            System.out.println("Неверный пароль.");
            return false;
        }
        activeUser = user;
        return true;
    }

     void createUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nПожалуйста, введите имя аккаунта: ");
        String userName = scanner.nextLine();
        System.out.print("Придумайте пароль: ");
        String password = scanner.nextLine();

        // Проверка, используется ли уже такое имя
        User selUser = findUser(userName);
        if (selUser != null) {
            System.out.println("Аккаунт с таким именем уже существует.");
            return;
        }
        // Создать нового user
        User user = new User(userName, password);
        user.setBalance(100);
        users.add(user);
        System.out.println(GREEN + "Аккаунт " + userName + " успешно зарегистрирован в системе. Присвоен ID: " + user.id + ANSI_RESET);
    }

     void setUserStatus() {
         Scanner scanner = new Scanner(System.in);
         System.out.println(GREEN + "\nВведите имя аккаунта:" + ANSI_RESET);
         String userName = scanner.nextLine();
         System.out.println(GREEN + "Введите права доступа(0-Пользователь, 1-Администратор):" + ANSI_RESET);
         int status  = scanner.nextInt();
         User selUser = findUser(userName);
         if (selUser != null) {
            selUser.adm = (status == 1);}
         else {
            System.out.println("Аккаунт " + userName+ " не зарегистрирован в системе");
        }
            return;
        }


    void listUsers() {
        System.out.println("\nАккаунты банка:");
        for (User user : users) {
          if (user.adm ) {
            System.out.println(user.username + " - " + "Администратор");
          }
          else {
            System.out.println(user.username + " - " + "Пользователь");
          }
        }
        System.out.println("");
    }

    User findUser(String username) {
        for (User user : users) {
            if (user.username.equals(username)) {
                return user;
            }
        }

        return null;
    }

}