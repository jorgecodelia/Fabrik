package com.fabrik.api.client;

import com.fabrik.api.common.ApiConstants;
import com.fabrik.api.common.domain.TransferDataRequest;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId = "FabrikClient", name = "fabrik", url = "${endpoint.fabrik}")
public interface FabrikClient {

    @GetMapping(value = "${endpoint.prefix}/accounts",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> getAccounts(@NotEmpty @RequestHeader(value = ApiConstants.X_API_KEY) String apikey,
                                                   @NotEmpty @RequestHeader(value = ApiConstants.X_AUTH_SCHEMA) String authSchema);

    @GetMapping(
            value = "${endpoint.prefix}/accounts/{accountId}/balance",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> getAccountBalance(@NotEmpty @RequestHeader(value = ApiConstants.X_API_KEY) String apikey,
                                              @NotEmpty @RequestHeader(value = ApiConstants.X_AUTH_SCHEMA) String authSchema,
                                              @PathVariable Long accountId);

    @GetMapping(
            value = "${endpoint.prefix}/accounts/{accountId}/transactions",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> getTransactions(
            @NotEmpty @RequestHeader(value = ApiConstants.X_API_KEY) String apikey,
            @NotEmpty @RequestHeader(value = ApiConstants.X_AUTH_SCHEMA) String authSchema,
            @PathVariable Long accountId,
            @RequestParam String fromAccountingDate,
            @RequestParam String toAccountingDate);

    @PostMapping(
            value = "${endpoint.prefix}/accounts/{accountId}/payments/money-transfers",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> generateTransfer(
            @NotEmpty @RequestHeader(value = ApiConstants.X_API_KEY) String apikey,
            @NotEmpty @RequestHeader(value = ApiConstants.X_AUTH_SCHEMA) String authSchema,
            @NotEmpty @RequestHeader(value = ApiConstants.X_TIME_ZONE) String timeZone,
            @PathVariable Long accountId,
            @RequestBody TransferDataRequest request);
}
