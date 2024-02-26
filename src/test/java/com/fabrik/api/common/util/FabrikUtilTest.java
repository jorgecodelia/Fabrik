package com.fabrik.api.common.util;

import com.fabrik.api.common.domain.ClientResponse;
import com.fabrik.api.common.domain.Error;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FabrikUtilTest {

    private static final String responseString = "[400 Bad Request] during [POST] to "
            + "[https://sandbox.platfr.io/api/gbs/banking/v4.0//accounts/14537780/payments/money-transfers] "
            + "[FabrikClient#generateTransfer(String,String,String,Long,TransferDataRequest)]: [{\n"
            + "  \"status\" : \"KO\",\n"
            + "  \"errors\" :  [\n"
            + "\t\t{\n"
            + "\t\t\t\"code\" : \"API000\",\n"
            + "\t\t\t\"description\" : \"it.sella.pagamenti.servizibonifico.exception.ServiziInvioBonificoSubsystemException: "
            + "it.sella.pagamenti.sottosistemi.SottosistemiException: Errore tecnico CONTO 45685475:Conto 45685475 non esiste\",\n"
            + "\t\t\t\"params\" : \"\"\n"
            + "\t\t}\n"
            + "\t],\n"
            + "  \"payload\": {}\n"
            + "}]";

    @Test
    public void shouldReturnAJsonString() throws Exception {
        String msgResponse = responseString.replaceAll("\\s","");
        msgResponse = msgResponse.replaceAll("errors","error");
        String response = FabrikUtil.extractErrorFromResponse(msgResponse);
        Assertions.assertNotNull(response);
        Gson gson = new Gson();
        ClientResponse clientResponse = gson.fromJson(response, ClientResponse.class);
        Error error = clientResponse.getError().get(0);
        Assertions.assertEquals(error.getCode(), "API000");
    }
}