package com.ssafy.payment.controller;

import com.ssafy.payment.config.PaymentsCancelConfig;
import com.ssafy.payment.dto.request.CancelPaymentsRequest;
import com.ssafy.payment.dto.response.PaymentsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "PaymentsCancelClient",
        url = "${feign.toss.url}",
        configuration = {PaymentsCancelConfig.class})
public interface PaymentsCancelClient {
    @PostMapping("/v1/payments/{paymentKey}/cancel")
    PaymentsResponse execute(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @PathVariable("paymentKey") String paymentKey,
            @RequestBody CancelPaymentsRequest cancelPaymentsRequest);
}
