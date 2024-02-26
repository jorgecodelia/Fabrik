package com.fabrik.api.service;

import com.fabrik.api.client.FabrikClient;
import com.fabrik.api.common.exception.ServiceException;
import com.fabrik.api.common.domain.Account;
import com.fabrik.api.common.domain.Balance;
import com.fabrik.api.common.domain.ClientListResponse;
import com.fabrik.api.common.domain.ClientResponse;
import com.fabrik.api.common.domain.TransactionResponse;
import com.fabrik.api.common.domain.TransferDataRequest;
import com.fabrik.api.common.domain.TransferDataResponse;
import com.fabrik.api.common.util.FabrikUtil;
import java.util.List;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FabrikService {

    @Autowired
    private FabrikClient fabrikClient;
    @Value("${fabrik.api-key}")
    private String apiKey;
    @Value("${fabrik.auth-schema}")
    private String authSchema;
    @Value("${fabrik.time-zone}")
    private String timeZone;
    private static final Logger LOGGER = LoggerFactory.getLogger(FabrikService.class);

    /**
     * Retrieve the user accounts.
     */
    public List<Account> getAccounts() {
        try {
            ResponseEntity<String> responseEntity = fabrikClient.getAccounts(apiKey, authSchema);
            return FabrikUtil.mapToObjectList(getClientResponseList(responseEntity).getPayload().list(), Account.class);
        } catch (IllegalAccessException e) {
            LOGGER.error(this.getClass().getName() + " - getAccounts");
            LOGGER.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Retrieve a user account balance by accountId.
     */
    public Balance getAccountBalance(long accountId) {
        ResponseEntity<String> responseEntity = fabrikClient.getAccountBalance(apiKey, authSchema, accountId);
        return FabrikUtil.mapToObject(getClientResponse(responseEntity).getPayload(), Balance.class);
    }

    /**
     * Retrieve all the transaction by date.
     */
    public List<TransactionResponse> getTransactionsByDate(
            long accountId, String fromAccountingDate, String toAccountingDate) {
        try {
            ResponseEntity<String> responseEntity = fabrikClient.getTransactions(apiKey, authSchema, accountId, fromAccountingDate, toAccountingDate);
            return FabrikUtil.mapToObjectList((List<Object>) getClientResponseList(responseEntity).getPayload().list(), TransactionResponse.class);
        } catch (IllegalAccessException e) {
            LOGGER.error(this.getClass().getName() + " - getTransactions");
            LOGGER.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Generate a money transfer.
     */
    public TransferDataResponse generateTransfer(long accountId, TransferDataRequest request) {
        ResponseEntity<String> responseEntity = fabrikClient.generateTransfer(apiKey, authSchema, timeZone, accountId, request);
        return FabrikUtil.mapToObject(getClientResponse(responseEntity).getPayload(), TransferDataResponse.class);

    }

    @SuppressWarnings("rawtypes")
    private ClientListResponse getClientResponseList(ResponseEntity<String> responseEntity) {
        String response = responseEntity.getBody();
        Gson gson = new Gson();
        return gson.fromJson(response, ClientListResponse.class);
    }

    private ClientResponse getClientResponse(ResponseEntity<String> responseEntity) {
        String response = responseEntity.getBody();
        Gson gson = new Gson();
        return gson.fromJson(response, ClientResponse.class);
    }
}
