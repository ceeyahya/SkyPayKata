package org.example;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AccountTest {

    @Mock
    private List<Transaction> mockTransactions;

    @InjectMocks
    private Account account;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        account = new Account(mockTransactions);
    }

    @Test
    public void testDeposit() {
        account.deposit(100);
        verify(mockTransactions, times(1)).add(any(Transaction.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeDeposit() {
        account.deposit(-100);
    }

    @Test
    public void testWithdraw() {
        account.withdraw(50);
        verify(mockTransactions, times(1)).add(any(Transaction.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testZeroWithdraw() {
        account.withdraw(0);
    }

    @Test(expected = InsufficientFundsException.class)
    public void testInsufficientFundsWithdraw() {
        account.withdraw(200);
    }

    @Test
    public void testPrintStatement() {
        Date date = new Date();
        Transaction transaction = new Transaction(date, 100, 100);
        when(mockTransactions.iterator()).thenReturn(List.of(transaction).iterator());
        account.printStatement();

        verify(mockTransactions).iterator();
    }
}