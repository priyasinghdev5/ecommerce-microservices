package com.ecommerce.payment_service.controller;

import com.ecommerce.payment_service.model.PaymentRequest;
import com.ecommerce.payment_service.model.PaymentResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @PostMapping
    public PaymentResponse pay(@RequestBody PaymentRequest request) {

        if (request.getAmount() < 100000) {
            return new PaymentResponse("SUCCESS");
        } else {
            return new PaymentResponse("FAILED");
        }
    }
}
