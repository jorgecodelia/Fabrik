package com.fabrik.api.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class ClientListResponse<T> extends BaseClientResponse {
    private PayloadListResponse<T> payload;

    public ClientListResponse(String status, List<Error> error, PayloadListResponse<T> payload) {
        super(status, error);
        this.payload = payload;
    }
}
