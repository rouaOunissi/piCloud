package org.example.purchase.controllers;

import org.example.purchase.dto.StripeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/stripe")
@CrossOrigin
public class StripeController {



        private final StripeService stripeService;

        public StripeController(StripeService stripeService) {
            this.stripeService = stripeService;
        }

        @PostMapping("/create-payment-intent")
        public ResponseEntity<Map<String, String>> createPaymentIntent(@RequestBody Map<String, Long> data) throws Exception {
            Long amount = data.get("amount");
            return  ResponseEntity.ok(Collections.singletonMap("clientSecret", stripeService.createPaymentIntent(amount)));
        }

}
