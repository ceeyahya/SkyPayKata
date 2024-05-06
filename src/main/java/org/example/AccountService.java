package org.example;

public interface AccountService {
    /**
     * Deposits money into the account.
     *
     * @param amount The amount of money to deposit (must be positive).
     * @throws IllegalArgumentException if the amount is not positive.
     */
    void deposit(int amount);

    /**
     * Withdraws money from the account.
     *
     * @param amount The amount of money to withdraw (must be positive).
     * @throws IllegalArgumentException if the amount is not positive.
     * @throws InsufficientFundsException if there are insufficient funds in the account.
     */
    void withdraw(int amount);

    /**
     * Prints a statement of the account transactions.
     */
    void printStatement();
}
