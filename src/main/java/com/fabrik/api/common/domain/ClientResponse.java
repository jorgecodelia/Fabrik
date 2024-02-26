package com.fabrik.api.common.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ClientResponse extends BaseClientResponse {
    private Object payload;

    public ClientResponse(String status, List<Error> error, Object payload) {
        super(status, error);
        this.payload = payload;
    }
}
