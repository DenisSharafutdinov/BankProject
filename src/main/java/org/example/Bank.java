package org.example;

import java.util.ArrayList;
import java.util.Scanner;



public class Bank {
    static String bankName = "(NIBS) New International Bank System";
    static ArrayList<User> users = new ArrayList<>();
    static int option;
    static int nextUserId = 1;
    public static final String ANSI_RESET = "\u001B[0m"; // Сброс цвета
    public static final String GREEN = "\u001B[32m"; // Зелёный цвет

    public static int generateUserId() {
        return nextUserId++;
    }

    // Вход в систему
    static void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nПожалуйста, введите ваш username: ");
        String username = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        // Проверка, если пользователь вышел
        User user = findUser(username);
        if (user == null) {
            System.out.println("Пользователь несуществует.");
            return;
        }

        // Проверка, если пароль неверный
        if (!user.password.equals(password)) {
            System.out.println("Неверный пароль.");
            return;
        }
        System.out.println(GREEN + "Вы успешно вошли в свой личный кабинет!" + ANSI_RESET);
        System.out.println("Баланс: " + user.balance);
    }

    // Создать нового пользователя
    static void createUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nПожалуйста, введите ваш username: ");
        String username = scanner.nextLine();
        System.out.print("Придумайте пароль: ");
        String password = scanner.nextLine();

        // Проверка, используется ли уже такое имя
        if (findUser(username) != null) {
            System.out.println("Такой username уже существует.");
            return;
        }
        User user = new User(username, password);
        user.setBalance(100.0);
        users.add(user);
        System.out.println(GREEN + "Пользователь " + username + " успешно зарегистрирован в системе. Присвоен ID: " + user.id + ANSI_RESET);
    }


    static User findUser(String username) {
        for (User user : users) {
            if (user.username.equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(GREEN + "Добро пожаловать в " + bankName + "!" + ANSI_RESET);


        // Меню 1 (Вход, регистрация ...)
        boolean exit = false;
        while (!exit) {
            System.out.println(GREEN + "Пожалуйста, выберите одну из предложенных операций:" + ANSI_RESET);
            System.out.println("\n[1]. Войти в личный кабинет");
            System.out.println("[2]. Регистрация аккаунта (User)");
            System.out.println("[3]. Выйти из банковской системы");

            option = scanner.nextInt();
            scanner.nextLine(); // использовать символ новой строки

            switch (option) {
                case 1:
                    login();
                    exit = true;
                    break;
                case 2:
                    createUser();
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("Неверная операция, попробуйте ещё раз.");
                    break;
            }
        }
    }
}
