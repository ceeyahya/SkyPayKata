package org.example;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Transaction> transactions = new ArrayList<>();
        Account account = new Account(transactions);

        account.deposit(1000);
        account.withdraw(500);
        account.deposit(2500);

        account.printStatement();
    }
}