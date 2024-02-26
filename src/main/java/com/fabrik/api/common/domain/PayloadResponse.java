package com.fabrik.api.common.domain;

import java.util.List;

public class PayloadResponse extends BaseClientResponse {

    private Object payload;

    public PayloadResponse(String status, List<Error> error, Object payload) {
        super(status, error);
        this.payload = payload;
    }
}
