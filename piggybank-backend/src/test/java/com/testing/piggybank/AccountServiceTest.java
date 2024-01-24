package com.testing.piggybank;
import com.testing.piggybank.model.Direction;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


import com.testing.piggybank.account.AccountService;
import com.testing.piggybank.model.Account;
import com.testing.piggybank.account.AccountRepository;


public class AccountServiceTest {

    @Test
    public void testGetAccount() {
        AccountRepository repository = mock(AccountRepository.class);
        AccountService service = new AccountService(repository);

        Account mockAccount = new Account();
        mockAccount.setId(1L);
        mockAccount.setBalance(new BigDecimal("1000"));
        mockAccount.setName("Test Account");

        when(repository.findById(1L)).thenReturn(Optional.of(mockAccount));

        Optional<Account> result = service.getAccount(1L);

        assertTrue(result.isPresent());
        assertEquals("Test Account", result.get().getName());
        assertEquals(0, new BigDecimal("1000").compareTo(result.get().getBalance()));
    }

    @Test
    public void testUpdateBalance() {
        AccountRepository repository = mock(AccountRepository.class);
        AccountService service = new AccountService(repository);

        Account mockAccount = new Account();
        mockAccount.setId(1L);
        mockAccount.setBalance(new BigDecimal("1000"));
        mockAccount.setName("Test Account");

        when(repository.findById(1L)).thenReturn(Optional.of(mockAccount));

        service.updateBalance(1L, new BigDecimal("500"), Direction.CREDIT);

        assertEquals(0, new BigDecimal("500").compareTo(mockAccount.getBalance()));
    }

    @Test
    public void testGetAccountsByUserId() {
        AccountRepository repository = mock(AccountRepository.class);
        AccountService service = new AccountService(repository);

        Account account1 = new Account();
        account1.setId(1L);
        account1.setName("Account 1");
        account1.setBalance(new BigDecimal("1000"));

        Account account2 = new Account();
        account2.setId(2L);
        account2.setName("Account 2");
        account2.setBalance(new BigDecimal("2000"));

        List<Account> accounts = Arrays.asList(account1, account2);

        when(repository.findAllByUserId(1L)).thenReturn(accounts);

        List<Account> result = service.getAccountsByUserId(1L);

        assertEquals(2, result.size());
        assertEquals("Account 1", result.get(0).getName());
        assertEquals("Account 2", result.get(1).getName());
    }

    // Extra unit test for the save account method
    @Test
    public void testSaveAccount() {
        AccountRepository repository = mock(AccountRepository.class);
        AccountService service = new AccountService(repository);

        Account mockAccount = new Account();
        mockAccount.setId(1L);
        mockAccount.setBalance(new BigDecimal("1000"));
        mockAccount.setName("Test Account");

        service.saveAccount(mockAccount);

        verify(repository, times(1)).save(mockAccount);
    }
}
