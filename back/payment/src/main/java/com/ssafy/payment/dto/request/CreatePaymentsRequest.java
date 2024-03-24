package com.ssafy.payment.dto.request;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentsRequest {
    private String method;

    private Integer amount;

    private String orderId;

    private String orderName;

    private String successUrl;

    private String failUrl;
}
