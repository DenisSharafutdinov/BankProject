package org.example;

import java.util.ArrayList;
import java.util.Scanner;

class Bank {
    static String bankName = "(NIBS) New International Bank System";
    static ArrayList<User> users = new ArrayList<>();
    static ArrayList<Admin> admins = new ArrayList<>();
    static int option;
    static int nextUserId = 1;
    public static final String ANSI_RESET = "\u001B[0m"; // Сброс цвета
    public static final String GREEN = "\u001B[32m"; // Зелёный цвет

    public static int generateUserId() {
        return nextUserId++;
    }

    public static void deleteAccount(String username) {
        User user = findUser(username);
        if (user == null) {
            System.out.println("Пользователь не найден");
            return;
        }
        user.deleteAccount();
        System.out.println(GREEN + "Аккаунт " + username + " успешно удалён!" + ANSI_RESET);
    }

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
        // Создать нового user
        User user = new User(username, password);
        user.setBalance(100.0);
        users.add(user);
        System.out.println(GREEN + "Пользователь " + username + " успешно зарегистрирован в системе. Присвоен ID: " + user.id + ANSI_RESET);
    }

    static void createAdmin() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nВведите username: ");
        String username = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        if (findUser(username) != null) {
            System.out.println("Этот username уже используется.");
            return;
        }
        // Создать нового админа
        Admin admin = new Admin(username, password);
        admin.setBalance(500.0);
        admins.add(admin);
        System.out.println(GREEN + "Администратор " + username + " успешно зарегестрирован в системе. Присвоен ID:" + admin.id + ANSI_RESET);
    }

    static void listUsers() {
        System.out.println("\nUsers:");
        for (User user : users) {
            System.out.println(user.username);
        }

        System.out.println("\nAdmins:");
        for (Admin admin : admins) {
            System.out.println(admin.username);
        }
    }

    static User findUser(String username) {
        for (User user : users) {
            if (user.username.equals(username)) {
                return user;
            }
        }
        for (Admin admin : admins) {
            if (admin.username.equals(username)) {
                return admin;
            }
        }
        return null;
    }

    public static void transfer(User sender, User recipient, double amount) {
        if (sender == null && recipient == null) {
            System.out.println("Польователь не найден");
            return;
        }
        sender.transfer(recipient, amount);
    }

    // Денежные переводы между пользователями (Трансфер)
    public static void transferMoney() {
        String senderName;
        String recipientName;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Перевод средств между пользователями");
        System.out.println("Введите имя отправителя:");
        senderName = scanner.nextLine();
        System.out.println("Введите имя получателя:");
        recipientName = scanner.nextLine();
        System.out.println("Введите сумму перевода:");
        double amount = Double.parseDouble(scanner.nextLine());
        User sender = Bank.findUser(senderName);
        User recipient = Bank.findUser(recipientName);
        Bank.transfer(sender, recipient, amount);
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println(GREEN + "Добро пожаловать в " + bankName + "!" + ANSI_RESET);

        // Создание default admin (главный админ, который может регистрировать новых админов)
        Admin defaultAdmin = new Admin("admin", "admin");
        admins.add(defaultAdmin);
        // Меню 1 (Вход, регистрация ...)
        boolean exit = false;
        while (!exit) {
            System.out.println(GREEN + "Пожалуйста, выберите одну из предложенных операций:" + ANSI_RESET);
            System.out.println("\n[1]. Войти в личный кабинет");
            System.out.println("[2]. Регистрация аккаунта (User)");
            System.out.println("[3]. Трансфер");
            System.out.println("[4]. Выйти из банковской системы");

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
                    transferMoney();
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Неверная операция, попробуйте ещё раз.");
                    break;
            }
        }

        // Меню 2 Администратор
        while (option == 1) {
            System.out.println("[1]. Удалить пользователя");
            System.out.println("[2]. Проверить баланс");
            System.out.println("[3]. Посмотреть список последних пользователей банка");
            System.out.println("[4]. Регистрация аккаунта (Admin)");
            System.out.println("[5]. Выход");

            int adminpanel = scanner.nextInt();
            scanner.nextLine();

            switch (adminpanel) {
                case 1:
                    System.out.println(GREEN + "Введите имя пользователя для удаления:" + ANSI_RESET);
                    String deleteUsername = scanner.next();
                    Bank.deleteAccount(deleteUsername);
                    break;
                case 2:
                    System.out.print(GREEN + "Введите имя пользователя для просмотра баланса: " + ANSI_RESET);
                    String checkBalanceUsername = scanner.next();
                    User checkBalanceUser = findUser(checkBalanceUsername);
                    if (checkBalanceUser == null) {
                        System.out.println("Пользователь не найден");
                    } else {
                        System.out.println(GREEN + "Баланс " + checkBalanceUsername + ": " + checkBalanceUser.balance + ANSI_RESET);
                    }
                    break;
                case 3:
                    listUsers();
                    break;
                case 4:
                    createAdmin();
                    break;
                case 5:
                    option = 0;
                    break;
                default:
                    System.out.println("Неверная операция, попробуйте ещё раз.");
                    break;
            }
        }
    }
}