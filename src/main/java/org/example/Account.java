package org.example;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Account implements AccountService {
    private int balance;
    private final List<Transaction> transactions;

    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String RESET = "\u001B[0m";

    public Account(List<Transaction> transactions) {
        this.balance = 100;
        this.transactions = transactions;
    }

    @Override
    public void deposit(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Deposit Amount must be Positive");
        }
        balance += amount;
        transactions.add(new Transaction(new Date(), amount, balance));
    }

    @Override
    public void withdraw(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient funds for withdrawal.");
        }
        balance -= amount;
        transactions.add(new Transaction(new Date(), -amount, balance));
    }

    @Override
    public void printStatement() {
        System.out.println("==========================================");
        System.out.println("|| Date           || Amount  || Balance ||");
        System.out.println("==========================================");
        for (Transaction transaction : transactions) {
            String color = transaction.getAmount() > 0 ? GREEN : RED;
            String colorsAmount = color + String.valueOf(transaction.getAmount()) + RESET;
            String amount = String.valueOf(transaction.getAmount());
            // If your terminal doesn't understand ANSI replace `colorsAmount` with `amount`.
            System.out.printf("|| %-15s || %7s  || %8d ||\n", transaction.getDateString(), colorsAmount, transaction.getBalance());
        }
        System.out.println("==========================================");
    }
}
