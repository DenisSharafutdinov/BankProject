package org.example;

class User {
    String username;
    String password;
    public int id;

    public double balance;

    User(String username, String password) {
        this.username = username;
        this.password = password;
        this.id = Bank.generateUserId();
    }

    // Переводы между пользователями
    public void transfer(User recipient, double amount) {
        if (this.balance < amount) {
            System.out.println("Недостаточно средств на балансе");
            return;
        }
        this.balance -= amount;
        recipient.balance += amount;
        System.out.println("Перевод успешно выполнен");
    }

    public void deleteAccount() {
        Bank.users.remove(this);
        balance = 0.0;
    }

    public void setBalance (double balance) {
        this.balance = balance;
    }
}
