package org.example;

import java.sql.Time;
import java.util.Date;

public class Transaction {
    Date date;
    Boolean typeTrans;
    Float amount;
    String recipient;

    Transaction(Boolean typeTrans, Float amount, String recipient) {
        this.date = new Date();
        this.typeTrans = typeTrans;
        this.amount = amount;
        this.recipient = recipient;

    }

    public float Execute (float balance) {
      float nbalance;
        if (typeTrans) {
            nbalance = balance + amount;
        }
        else {
          //  float newBalance
            nbalance = balance - amount;
        }
     return nbalance;
    }
    public void transfer(User sender, User recipient) {
        sender.balance = sender.balance - amount;
        recipient.balance = recipient.balance + amount;
        Transaction transaction = new Transaction(true, amount, sender.username);
        recipient.transactions.add(transaction);
    }
}
