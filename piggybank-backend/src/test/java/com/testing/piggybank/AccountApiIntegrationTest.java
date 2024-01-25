package com.testing.piggybank;

import com.testing.piggybank.account.UpdateAccountRequest;
import com.testing.piggybank.model.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountApiIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;


    // Test fetching specific account details
    @Test
    public void testGetAccountDetails() {
        // Arange
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-User-Id", "1");

        // Act
        ResponseEntity<Account> response = restTemplate
                .exchange("/api/v1/accounts/1", HttpMethod.GET, new HttpEntity<>(headers), Account.class);

        // Assert
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    // Test updating account information
    @Test
    public void testUpdateAccount() {
        UpdateAccountRequest request = new UpdateAccountRequest();
        request.setAccountId(1L);
        request.setAccountName("Test Account");

        HttpEntity<UpdateAccountRequest> entity = new HttpEntity<>(request);

        ResponseEntity<HttpStatus> response = restTemplate.exchange("/api/v1/accounts", HttpMethod.PUT, entity, HttpStatus.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
