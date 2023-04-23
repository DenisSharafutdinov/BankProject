package org.example;

import java.util.ArrayList;

class User {
    String username;
    String password;
    public int id;
    boolean adm;
    public float balance;
    ArrayList<Transaction> transactions = new ArrayList<>();
    User(String username, String password) {
        this.adm = false;
        this.username = username;
        this.password = password;
      //  this.id = myBank.generateUserId();
    }
    void listTransactions() {
        System.out.println("\nСписок транзакций:");
        for (Transaction transaction : transactions) {
          if (transaction.typeTrans) {
             if (transaction.recipient.isEmpty()) {
                 System.out.println(transaction.date + " Пополнение          +" + transaction.amount + " руб.");
             }
             else {
                 System.out.println(transaction.date + " Получение перевода  +" + transaction.amount + " руб. Отправитель: " + transaction.recipient);
             }
          }
          else {
              if ( transaction.recipient.indexOf("*") == -1) {
                 System.out.println(transaction.date + " Платёж              -" + transaction.amount + " руб. Получатель: " + transaction.recipient);
              }
              else {
                 System.out.println(transaction.date + " Отправка перевода   -" + transaction.amount + " руб. Получатель: " + transaction.recipient);
              }
          }
        }
        System.out.println("");
    }
    // Переводы между пользователями
    public void transfer(User recipient, float amount) {
        if (this.balance < amount) {
            System.out.println("Недостаточно средств на балансе");
            return;
        }
        this.balance -= amount;
        recipient.balance += amount;
        System.out.println("Перевод успешно выполнен");
    }

 /*   public void deleteAccount() {
        Bank.users.remove(this);
        balance = 0.0;
    }*/

    public void setBalance (float balance) {
        this.balance = balance;
    }
}
