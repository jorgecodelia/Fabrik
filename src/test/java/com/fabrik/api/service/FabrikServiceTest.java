package com.fabrik.api.service;

import com.fabrik.api.client.FabrikClient;
import com.fabrik.api.common.exception.ServiceException;
import com.fabrik.api.common.domain.Account;
import com.fabrik.api.common.domain.Address;
import com.fabrik.api.common.domain.Amount;
import com.fabrik.api.common.domain.Balance;
import com.fabrik.api.common.domain.Beneficiary;
import com.fabrik.api.common.domain.ClientListResponse;
import com.fabrik.api.common.domain.ClientResponse;
import com.fabrik.api.common.domain.Creditor;
import com.fabrik.api.common.domain.Debtor;
import com.fabrik.api.common.domain.Fee;
import com.fabrik.api.common.domain.PayloadListResponse;
import com.fabrik.api.common.domain.TaxRelief;
import com.fabrik.api.common.domain.TransactionResponse;
import com.fabrik.api.common.domain.TransactionType;
import com.fabrik.api.common.domain.TransferDataRequest;
import com.fabrik.api.common.domain.TransferDataResponse;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class FabrikServiceTest {

    @InjectMocks
    private FabrikService fabrikService;
    @Mock
    private FabrikClient fabrikClient;
    private static final long ACCOUNT_ID = 2L;
    private static final String CURRENCY = "EUR";
    private static final String FROM_DATE = "2023-12-24";
    private static final String TO_DATE = "2024-02-20";
    private static final String ERROR = "ERROR";
    private static final String API_KEY = "ABGSDFWERHGTYFGHFHDDFGHSDF";
    private static final String AUTH_SCHEMA = "T3T";
    private static final String TIME_ZONE = "Europe/Rome";

    @BeforeEach
    public void init() {
        ReflectionTestUtils.setField(fabrikService, "apiKey", API_KEY);
        ReflectionTestUtils.setField(fabrikService, "authSchema", AUTH_SCHEMA);
        ReflectionTestUtils.setField(fabrikService, "timeZone", TIME_ZONE);
    }

    @Test
    public void shouldGetAccounts() {
        Account account = new Account("1237766", "AICKSDCA123123", "QEW@", "W455R",
                "IT", "WSF3", "RE33", "123321", "John",
                "SomeProductName", "HolderName", "2022-01-01", CURRENCY, null, null);
        List<Account> accountList = new ArrayList<>();
        accountList.add(account);
        PayloadListResponse payloadListResponse = new PayloadListResponse(accountList);
        ClientListResponse clientListResponse = new ClientListResponse("OK",null, payloadListResponse);
        Mockito.when(fabrikClient.getAccounts(API_KEY, AUTH_SCHEMA)).thenReturn(ResponseEntity.ok(convertObjectToJson(clientListResponse)));
        List<Account> response = fabrikService.getAccounts();
        Assertions.assertNotNull(response);
        Assertions.assertEquals("1237766", response.get(0).getAccountId());
    }

    @Test
    public void shouldGetAccountsFailWithException() {
        Mockito.when(fabrikClient.getAccounts(API_KEY, AUTH_SCHEMA))
                .thenThrow(new ServiceException(ERROR));

        Assertions.assertThrows(
                Exception.class, () -> fabrikService.getAccounts());
    }


    @Test
    public void shouldGetAccountBalance() {
        Balance balance = new Balance(TO_DATE, 4300, 100000, "CURRENCY");
        ClientResponse clientResponse = new ClientResponse("200", null, balance);
        Mockito.when(fabrikClient.getAccountBalance(API_KEY, AUTH_SCHEMA, ACCOUNT_ID)).thenReturn(
                ResponseEntity.ok(convertObjectToJson(clientResponse)));
        Balance response = fabrikService.getAccountBalance(ACCOUNT_ID);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(4300, response.getBalance());
    }

    @Test
    public void shouldGetAccountBalanceFailWithException() {
        Mockito.when(fabrikClient.getAccountBalance(API_KEY, AUTH_SCHEMA, ACCOUNT_ID))
                .thenThrow(new ServiceException(ERROR));

        Assertions.assertThrows(
                Exception.class, () -> fabrikService.getAccountBalance(ACCOUNT_ID));
    }

    @Disabled ("WIP")
    @Test
    public void shouldGetTransactionsByDate() {
        TransactionType transactionType = new TransactionType("GBS_TRANSACTION_TYPE", "GBS_ACCOUNT_TRANSACTION_TYPE_0009");
        TransactionResponse transactionResponse = new TransactionResponse("1627525111001", "20000178206975",
                FROM_DATE, TO_DATE, transactionType, -0.10, CURRENCY, "BA LUCA T.               TEST PAGAMENTO RICORRENTE PRO PSD2");
        List<TransactionResponse> transactionResponseList = new ArrayList<>();
        transactionResponseList.add(transactionResponse);
        ClientListResponse clientListResponse = new ClientListResponse("200", Collections.emptyList(),
                new PayloadListResponse(transactionResponseList));
        Mockito.when(fabrikClient.getTransactions(API_KEY, AUTH_SCHEMA, ACCOUNT_ID, FROM_DATE, TO_DATE))
                .thenReturn(ResponseEntity.ok(convertObjectToJson(clientListResponse)));
        List<TransactionResponse> responseList = fabrikService.getTransactionsByDate(ACCOUNT_ID, FROM_DATE, TO_DATE);
        Assertions.assertNotNull(responseList);
        Assertions.assertEquals("123", responseList.get(0).getTransactionId());
    }

    @Test
    public void shouldGetTransactionsByDateFailWithException() {
        Mockito.when(fabrikClient.getTransactions(API_KEY, AUTH_SCHEMA, ACCOUNT_ID, FROM_DATE, TO_DATE))
                .thenThrow(new ServiceException(ERROR));

        Assertions.assertThrows(
                Exception.class, () -> fabrikService.getTransactionsByDate(ACCOUNT_ID, FROM_DATE, TO_DATE));
    }

    @Disabled ("WIP")
    @Test
    public void shouldGenerateTransfer() {
        Account account = new Account("123443", "AICKSDCA123123", "QEW@", "W455R",
                "IT", "WSF3", "RE33", "123321", "Susana Rossi",
                "SomeProductName", "HolderName", "2020-01-01", CURRENCY, null, null);
        Address address = new Address("via Magno 23", "Rome", "39");
        Beneficiary naturalPersonBeneficiary = new Beneficiary("EWEFSDF123123", "FVFW4423434", null, null, null, null);
        Beneficiary legalPersonBeneficiary = new Beneficiary("ACBBB4532121", "OI039234", null, null, null, null);
        TaxRelief taxRelief = new TaxRelief("123r3", true, "EWEFSDF123123",
                "Some-type", naturalPersonBeneficiary, legalPersonBeneficiary);
        Creditor creditor = new Creditor("Susana", account, address);
        TransferDataRequest transferDataRequest = new TransferDataRequest(creditor,
                FROM_DATE, "https//www.fakeBank.com", "new Transfer", 200,
                CURRENCY, true, true, "OG3", "34FFFEEW5", taxRelief);
        Account accountFranz = new Account("123", "AICKSDCA123123", "QEW@", "W455R",
                "IT", "WSF3", "RE33", "123321", "Susana Rossi",
                "SomeProductName", "HolderName", "2020-01-01", CURRENCY, null, null);
        Debtor debtor = new Debtor("Franz", accountFranz);
        Amount amount = new Amount(100, CURRENCY, 600, CURRENCY, TO_DATE, 2);
        Fee fee = new Fee("001", "a fee", 2000, CURRENCY);
        ArrayList<Fee> feeList = new ArrayList<>();
        feeList.add(fee);
        TransferDataResponse transferDataResponse = new TransferDataResponse("234", "200-OK", "UP",
                creditor, debtor, "CRO", "uri", "TRN", "Some description",
                new Date(), new Date(), "2021-01-03", "Creditor value data", amount,
                true, true, "0.5", "TXT", feeList, true);
        ClientResponse clientResponse = new ClientResponse("200", Collections.emptyList(), transferDataResponse);
        Mockito.when(fabrikClient.generateTransfer(API_KEY, AUTH_SCHEMA,TIME_ZONE, ACCOUNT_ID, transferDataRequest))
                .thenReturn(ResponseEntity.ok(convertObjectToJson(clientResponse)));
        TransferDataResponse response = fabrikService.generateTransfer(ACCOUNT_ID, transferDataRequest);
        Assertions.assertNotNull(response);
        Assertions.assertEquals("234", response.getMoneyTransferId());
    }

    @Test
    public void shouldGenerateTransferFailWithException() {
        Mockito.when(fabrikClient.generateTransfer(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyLong(), Mockito.any()))
                .thenThrow(new ServiceException(ERROR));

        TransferDataRequest transferDataRequest = new TransferDataRequest(null,
                FROM_DATE, "https//www.fakeBank.com", "new Transfer", 200,
                "CURRENCY", true, true, "OG3", "34FFFEEW5", null);
        Assertions.assertThrows(
                Exception.class, () -> fabrikService.generateTransfer(ACCOUNT_ID, transferDataRequest));
    }

    @Test
    public void shouldFailWithoutClientHeaders() {
        ReflectionTestUtils.setField(fabrikService, "apiKey", null);
        ReflectionTestUtils.setField(fabrikService, "authSchema", null);
        Mockito.when(fabrikClient.generateTransfer(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyLong(), Mockito.any()))
                .thenThrow(new RuntimeException());
        TransferDataRequest transferDataRequest = new TransferDataRequest(null,
                FROM_DATE, "https//www.fakeBank.com", "new Transfer", 200,
                "CURRENCY", true, true, "OG3", "34FFFEEW5", null);
        Assertions.assertThrows(
                Exception.class, () -> fabrikService.generateTransfer(ACCOUNT_ID, transferDataRequest));
    }

    private String convertObjectToJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

}