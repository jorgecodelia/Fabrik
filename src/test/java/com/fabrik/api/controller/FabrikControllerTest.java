package com.fabrik.api.controller;

import com.fabrik.api.domain.Account;
import com.fabrik.api.domain.Address;
import com.fabrik.api.domain.Amount;
import com.fabrik.api.domain.Balance;
import com.fabrik.api.domain.Beneficiary;
import com.fabrik.api.domain.Creditor;
import com.fabrik.api.domain.Debtor;
import com.fabrik.api.domain.Fee;
import com.fabrik.api.domain.TaxRelief;
import com.fabrik.api.domain.TransactionRecord;
import com.fabrik.api.domain.TransactionType;
import com.fabrik.api.domain.TransferDataRequest;
import com.fabrik.api.domain.TransferDataResponse;
import com.fabrik.api.service.FabrikService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class FabrikControllerTest {

    @InjectMocks
    private FabrikController fabrikController;
    @Mock
    private FabrikService fabrikService;
    private static final long ACCOUNT_ID = 1L;
    private static final String CURRENCY = "EUR";
    private static final String FROM_DATE = "2023-12-24";
    private static final String TO_DATE = "2024-02-20";

    @Test
    public void shouldGetAccounts() {
        Account account = new Account("453534", "AICKSDCA123123", "QEW@", "W455R",
                "IT", "WSF3", "RE33", "123321", "John",
                "SomeProductName", "HolderName", "2022-01-01", CURRENCY);
        Mockito.when(fabrikService.getAccounts()).thenReturn(List.of(account));
        ResponseEntity<List<Account>> responseEntity = fabrikController.getAccounts();
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void shouldGetAccountBalance() {
        Balance balance = new Balance(TO_DATE, 4300, 100000, CURRENCY);
        Mockito.when(fabrikService.getAccountBalance(ACCOUNT_ID)).thenReturn(balance);
        ResponseEntity<Balance> responseEntity = fabrikController.getAccountBalance(ACCOUNT_ID);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void shouldGetTransactions() {
        TransactionType transactionType = new TransactionType("1A", "2003");
        TransactionRecord transactionRecord = new TransactionRecord("123665", "1234",
                FROM_DATE, TO_DATE, transactionType, 200, CURRENCY, "Hello");
        Mockito.when(fabrikService.getTransactionsByDate(ACCOUNT_ID, FROM_DATE, TO_DATE)).thenReturn(List.of(transactionRecord));
        ResponseEntity<List<TransactionRecord>> responseEntity = fabrikController.getTransactionsByDate(ACCOUNT_ID, FROM_DATE, TO_DATE);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void shouldGenerateTransfer() {
        Account account = new Account("123", "AICKSDCA123123", "QEW@", "W455R",
                "IT", "WSF3", "RE33", "123321", "Susana Rossi",
                "SomeProductName", "HolderName", "2020-01-01", CURRENCY);
        Address address = new Address("via Magno 23", "Rome", "39");
        Beneficiary naturalPersonBeneficiary = new Beneficiary("EWEFSDF123123", "FVFW4423434");
        Beneficiary legalPersonBeneficiary = new Beneficiary("ACBBB4532121", "OI039234");
        TaxRelief taxRelief = new TaxRelief("123r3", true, "EWEFSDF123123",
                "Some-type", naturalPersonBeneficiary, legalPersonBeneficiary);
        Creditor creditor = new Creditor("Susana", account, address);
        TransferDataRequest transferDataRequest = new TransferDataRequest(creditor,
                FROM_DATE, "https//www.fakeBank.com", "new Transfer", 200,
                CURRENCY, true, true, "OG3", "34FFFEEW5", taxRelief);
        Account accountFranz = new Account("123", "AICKSDCA123123", "QEW@", "W455R",
                "IT", "WSF3", "RE33", "123321", "Susana Rossi",
                "SomeProductName", "HolderName", "2020-01-01", CURRENCY);
        Debtor debtor = new Debtor("Franz", accountFranz);
        Amount amount = new Amount(100, CURRENCY, 600, CURRENCY, TO_DATE, 2);
        Fee fee = new Fee("001", "a fee", 2000, CURRENCY);
        ArrayList<Fee> feeList = new ArrayList<>();
        feeList.add(fee);
        TransferDataResponse transferDataResponse = new TransferDataResponse("234", "200-OK", "UP",
                creditor, debtor, "CRO", "uri", "TRN", "Some description",
                new Date(), new Date(), "2021-01-03", "Creditor value data", amount,
                true, true, "0.5", "TXT", feeList, true);
        Mockito.when(fabrikService.generateTransfer(ACCOUNT_ID, transferDataRequest)).thenReturn(transferDataResponse);
        ResponseEntity<TransferDataResponse> responseEntity = fabrikController.generateTransfer(ACCOUNT_ID, transferDataRequest);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}