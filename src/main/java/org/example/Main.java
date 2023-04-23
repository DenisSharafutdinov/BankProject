package org.example;

import java.util.Scanner;


public class Main {
    public static int option;
    public static final String ANSI_RESET = "\u001B[0m"; // Сброс цвета
    public static final String GREEN = "\u001B[32m"; // Зелёный цвет
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Bank myBank = new Bank();

        System.out.println(GREEN + "Добро пожаловать в " + myBank.bankName + "!" + ANSI_RESET);

        // Создание default admin (главный админ, который может регистрировать новых админов)
        User defaultAdmin = new User("admin", "admin");
        defaultAdmin.adm = true;
        defaultAdmin.balance = 199;
        myBank.users.add(defaultAdmin);

        // Меню 1 (Вход, регистрация ...)
        boolean exit = false;
        while (!exit) {

            System.out.println(GREEN + "Пожалуйста, выберите одну из предложенных операций:" + ANSI_RESET);
            System.out.println("\n[1]. Войти в личный кабинет");
            System.out.println("[2]. Регистрация аккаунта");
            System.out.println("[3]. Выйти из банковской системы");

            option = scanner.nextInt();
            scanner.nextLine(); // использовать символ новой строки

            switch (option) {
                case 1:
                    if (!myBank.login()) {
                     option = 0; }
                    else {
                      System.out.println(GREEN + "Вы успешно вошли в свой личный кабинет!" + ANSI_RESET);
                      System.out.println("");
                    }
                    break;
                case 2:
                    myBank.createUser();
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("Неверная операция, попробуйте ещё раз.");
                    break;
            }


        // Меню 2 User + Администратор
        while (option == 1) {
            System.out.println("Ваш баланс: " + myBank.activeUser.balance);
            System.out.println("Операции со счётом:");
            System.out.println("[1]. Пополнить счёт");
            System.out.println("[2]. Платёж");
            System.out.println("[3]. Перевод");
            System.out.println("[4]. Список транзакций");
            if (myBank.activeUser.adm) {
                System.out.println("");
                System.out.println("Операции администрирования:");
                System.out.println("[5]. Список аккаунтов");
                System.out.println("[6]. Добавить аккаунт");
                System.out.println("[7]. Удалить аккаунт");
                System.out.println("[8]. Изменить права аккаунта");
            }
            System.out.println("");
            System.out.println("[0]. Выход");

            int userPanel = scanner.nextInt();
            scanner.nextLine();
             if ((!myBank.activeUser.adm) && (userPanel>4)) {
                userPanel = 10;
            }
            float money;
            Transaction transaction;
            String recipient;
            Scanner scanner2 = new Scanner(System.in);
            switch (userPanel) {
                case 1:
                    System.out.println(GREEN + "Введите сумму пополнения:" + ANSI_RESET);
                    money = scanner.nextFloat();
                    transaction = new Transaction(true, money, "");
                    myBank.activeUser.balance = transaction.Execute(myBank.activeUser.balance);
                    myBank.activeUser.transactions.add(transaction);
                    break;
                case 2:
                    System.out.println(GREEN + "Введите сумму платежа:" + ANSI_RESET);
                    money = scanner2.nextFloat();
                    System.out.println(GREEN + "Введите интернет-магазин:" + ANSI_RESET);
                    recipient = scanner.nextLine();
                    transaction = new Transaction(false, money, "*"+recipient);
                    myBank.activeUser.balance = transaction.Execute(myBank.activeUser.balance);
                    myBank.activeUser.transactions.add(transaction);
                    break;
                case 3:
                    System.out.println(GREEN + "Введите сумму перевода:" + ANSI_RESET);
                    money = scanner2.nextFloat();
                    System.out.println(GREEN + "Введите получателя:" + ANSI_RESET);
                    recipient = scanner.nextLine();
                    User user = myBank.findUser(recipient);
                    if (user != null) {
                      transaction = new Transaction(false, money, recipient);
                      transaction.transfer(myBank.activeUser, user);
                      myBank.activeUser.transactions.add(transaction);}
                    else {
                        System.out.println("Аккаунт " + recipient+ " не зарегистрирован в системе");
                    }
                    break;
                case 4:
                    myBank.activeUser.listTransactions();
                    break;
                case 5:
                    myBank.listUsers();
                    break;
                case 6:
                    myBank.createUser();
                    break;
                case 7:
                    System.out.println(GREEN + "Введите аккаунт для удаления:" + ANSI_RESET);
                    String deleteUsername = scanner.next();
                    myBank.deleteAccount(deleteUsername);
                    break;
                case 8:
                    myBank.setUserStatus();
                    break;
                case 0:
                    option = 0;
                    break;
                default:
                    System.out.println("Неверная операция, попробуйте ещё раз.");
                    break;
            }
          }
        }
    }
}
