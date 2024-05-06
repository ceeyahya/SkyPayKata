package org.example;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
    private final Date date;
    private final int amount;
    private final int balance;

    public Transaction(Date date, int amount, int balance) {
        this.date = date;
        this.amount = amount;
        this.balance = balance;
    }

    public String getDateString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
    }

    public int getAmount() {
        return amount;
    }

    public int getBalance() {
        return balance;
    }
}
