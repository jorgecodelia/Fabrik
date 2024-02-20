package com.fabrik.api.client;

import com.fabrik.api.domain.Account;
import com.fabrik.api.domain.Balance;
import com.fabrik.api.domain.TransactionRecord;
import com.fabrik.api.domain.TransferDataRequest;
import com.fabrik.api.domain.TransferDataResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId = "FabrikClient", name = "fabrik", url = "${endpoint.fabrik}")
public interface FabrikClient {

  @GetMapping(value = "${endpoint.prefix}/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<List<Account>> getAccounts();

  @GetMapping(
      value = "${endpoint.prefix}/accounts/{accountId}/balance",
      produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Balance> getAccountBalance(@PathVariable Long accountId);

  @GetMapping(
      value = "${endpoint.prefix}/accounts/{accountId}/transactions",
      produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<List<TransactionRecord>> getTransactions(
      @PathVariable Long accountId,
      @RequestParam String fromAccountingDate,
      @RequestParam String toAccountingDate);

  @PostMapping(
      value = "${endpoint.prefix}/accounts/{accountId}/payments/money-transfers",
      produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<TransferDataResponse> generateTransfer(
      @PathVariable Long accountId, @RequestBody TransferDataRequest request);
}
