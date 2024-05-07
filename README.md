# SkyPay Kata
<hr />

## Introduction

The aim of the project is to emulate a primitive bank account with the simplest features i.e.
depositing money, withdrawing, and printing a "bank statement" in the console.

## Prerequisites

- The exercise was solved using the IntelliJ Community Edition using Java 17.0.2.<sup>*</sup>
- The code was bundled using Maven.<sup>**</sup>

<sup>*</sup> Java 17 is considered one of the more stable version for production.<br />
<sup>**</sup> Gradle is a more modern and powerful build tool, however, for the sake of simplicity
and familiarity I've decided to use Maven.

## Proposed Solution

The instructions mention that the `Account` class should implement the public interface included
in the exercise file:

```java
public interface AccountService {
    void deposit(int amount);
    void withdraw(int amount);
    void printStatement();
}
```
The proposed `Account` class therefore looks like this:

```java
package org.example;

import java.util.Date;
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
            throw new IllegalArgumentException("Withdrawal Amount must be Positive.");
        }
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient Funds for Withdrawal.");
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
```

The `RED`, `GREEN` and `RESET` variables, as well as the initial balance will be discussed later,
for now let's explain the implementation of each of the methods `deposit`, `withdraw`, `printStatement`:

`deposit()`: checks if the amount to be deposited is negative, throws an Exception if it's the case, or
add the amount to the initial balance and pushes it to the `transactions` List if no errors were found.

`withdraw()`: checks if the amount to be withdrawn is negative, throws an Exception if it's the case, checks
if the account balance is sufficient to perform the withdrawal and throws an Exception if not. then subtracts
the amount for the balance and add the transaction to the transaction list if everything else is fine.

`printStatement()`: prints the table header to the console, then loops over all the transactions and lists them
one by one.

In order to solve the exercise more elegantly I've elected to add a `Transaction` class that will be used to 
build a transaction history and the bank account statement. It looks like this:

```java
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
```

Each transaction is made up of a Date, an amount (to withdraw or deposit) and the balance
after the operation. the methods of this class are simply to getters for the amount and
balance, and a function that nicely formats the date using `SimpleDateFormat`.

## Additional Features

The exercise instruction emphasize simplicity and avoiding over-engineering, I thought it
would therefore be a good addition to the Developer Experience and User Experience to add
2 features:

- Mock tests using Mockito and JUnit
- Displaying deposits in Green and withdrawals in Red in the Console.<sup>***</sup>

<sup>***</sup> More on the feature: 
- In order for the colors to display properly your terminal emulator must
understand ANSI, most modern terminal emulators will have no problems understanding and
displaying colors however I still provided a method to display them normally in the
`printStatement()` (read [here](https://github.com/ceeyahya/SkyPayKata/blob/main/src/main/java/org/example/Account.java#L50))

- The `RED`, `GREEN` and `RESET` are ANSI escape sequences they control the formatting and color
of text among other things. (read [here](https://github.com/ceeyahya/SkyPayKata/blob/main/src/main/java/org/example/Account.java#L11-L13))
