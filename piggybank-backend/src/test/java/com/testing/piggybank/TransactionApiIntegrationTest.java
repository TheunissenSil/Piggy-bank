package com.testing.piggybank;

import com.testing.piggybank.model.Currency;
import com.testing.piggybank.model.Transaction;
import com.testing.piggybank.transaction.CreateTransactionRequest;
import com.testing.piggybank.transaction.GetTransactionsResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.math.BigDecimal;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionApiIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    // Test creating a new transaction
    @Test
    public void testCreateTransaction() {
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setSenderAccountId(1L);
        request.setReceiverAccountId(2L);
        request.setAmount(new BigDecimal("100"));
        request.setDescription("Test Transaction");
        request.setCurrency(Currency.EURO);

        HttpEntity<CreateTransactionRequest> entity = new HttpEntity<>(request);

        ResponseEntity<Transaction> response = restTemplate.postForEntity("/api/v1/transactions", entity, Transaction.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // Test fetching transactions for an account
    @Test
    public void testGetTransactionsForAccount() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-User-Id", "1");

        ResponseEntity<GetTransactionsResponse> response = restTemplate
                .exchange("/api/v1/transactions/1", HttpMethod.GET, new HttpEntity<>(headers), GetTransactionsResponse.class);

        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
    }
}
