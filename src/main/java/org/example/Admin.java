package org.example;

class Admin extends User {
    Admin(String username, String password) {
        super(username, password);

    }
    public void deleteAccount() {
        Bank.admins.remove(this);
        balance = 0.0;
    }
}
