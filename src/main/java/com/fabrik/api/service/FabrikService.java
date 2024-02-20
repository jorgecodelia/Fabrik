package com.fabrik.api.service;

import com.fabrik.api.client.FabrikClient;
import com.fabrik.api.common.exceptions.ServiceException;
import com.fabrik.api.domain.Account;
import com.fabrik.api.domain.Balance;
import com.fabrik.api.domain.TransactionRecord;
import com.fabrik.api.domain.TransferDataRequest;
import com.fabrik.api.domain.TransferDataResponse;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FabrikService {

  @Autowired private FabrikClient fabrikClient;
  private static final Logger LOGGER = LoggerFactory.getLogger(FabrikService.class);

  /** Retrieve the user accounts. */
  public List<Account> getAccounts() {
    try {
      return fabrikClient.getAccounts().getBody();
    } catch (Exception e) {
      LOGGER.error(this.getClass().getName() + " - getAccounts");
      LOGGER.error(e.getMessage());
      throw new ServiceException(e.getMessage());
    }
  }

  /** Retrieve a user account balance by accountId. */
  public Balance getAccountBalance(long accountId) {
    try {
      return fabrikClient.getAccountBalance(accountId).getBody();
    } catch (Exception e) {
      LOGGER.error(this.getClass().getName() + " - getAccountBalance");
      LOGGER.error(e.getMessage());
      throw new ServiceException(e.getMessage());
    }
  }

  /** Retrieve all the transaction by date. */
  public List<TransactionRecord> getTransactionsByDate(
      long accountId, String fromAccountingDate, String toAccountingDate) {
    try {
      return fabrikClient
          .getTransactions(accountId, fromAccountingDate, toAccountingDate)
          .getBody();
    } catch (Exception e) {
      LOGGER.error(this.getClass().getName() + " - getTransactions");
      LOGGER.error(e.getMessage());
      throw new ServiceException(e.getMessage());
    }
  }

  /** Generate a money transfer. */
  public TransferDataResponse generateTransfer(long accountId, TransferDataRequest request) {
    try {
      return fabrikClient.generateTransfer(accountId, request).getBody();
    } catch (Exception e) {
      LOGGER.error(this.getClass().getName() + " - generateTransfer");
      LOGGER.error(e.getMessage());
      throw new ServiceException(e.getMessage());
    }
  }
}
