package com.fabrik.api.controller;

import com.fabrik.api.common.domain.Account;
import com.fabrik.api.common.domain.Balance;
import com.fabrik.api.common.domain.TransferDataRequest;
import com.fabrik.api.common.domain.TransferDataResponse;
import com.fabrik.api.common.domain.TransactionResponse;
import com.fabrik.api.service.FabrikService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class FabrikController {

    @Autowired
    private FabrikService fabrikService;

    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> getAccounts() {
        return ResponseEntity.status(HttpStatus.OK).body(fabrikService.getAccounts());
    }

    @GetMapping("/{accountId}/balance")
    public ResponseEntity<Balance> getAccountBalance(@PathVariable long accountId) {
        return ResponseEntity.status(HttpStatus.OK).body(fabrikService.getAccountBalance(accountId));
    }

    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByDate(
            @PathVariable long accountId,
            @RequestParam String fromAccountingDate,
            @RequestParam String toAccountingDate) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(fabrikService.getTransactionsByDate(accountId, fromAccountingDate, toAccountingDate));
    }

    @PostMapping("/{accountId}/transfer")
    public ResponseEntity<TransferDataResponse> generateTransfer(
            @PathVariable long accountId, @RequestBody TransferDataRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(fabrikService.generateTransfer(accountId, request));
    }
}
