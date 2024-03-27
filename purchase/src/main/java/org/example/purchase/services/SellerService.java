package org.example.purchase.services;

import com.nimbusds.jose.shaded.gson.JsonSyntaxException;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.net.Webhook;
import com.stripe.param.AccountCreateParams;
import com.stripe.param.AccountLinkCreateParams;
import org.example.purchase.entities.Seller;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class SellerService {



    public void createSeller(Seller seller) throws StripeException {
        Stripe.apiKey ="sk_test_51OvQL1JeISkzjGkftJ4YeJZTGwgr5KxjespCPaAL1BwNNXvtzXZFCRJUEGsZfuqfRO43gXiV4fPDqbnN2YTkmPTA00eGjIguha";
                Map<String, Object> accountParams = new HashMap<>();
        accountParams.put("type", "express");
        Account account = Account.create(accountParams);


    }
    private static final String endpointSecret = " whsec_c910f8a98cde96dfe7a77c5dc31c22b7f04bc1b6b80f5771c685d1363e3ab085";

    @PostMapping("/webhook")
    @ResponseStatus(HttpStatus.OK)
    public void handleWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        Stripe.apiKey ="sk_test_51OvQL1JeISkzjGkftJ4YeJZTGwgr5KxjespCPaAL1BwNNXvtzXZFCRJUEGsZfuqfRO43gXiV4fPDqbnN2YTkmPTA00eGjIguha";

        try {
            Event event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
            EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
            StripeObject stripeObject = null;

            if (dataObjectDeserializer.getObject().isPresent()) {
                stripeObject = dataObjectDeserializer.getObject().get();
            } else {
                // Deserialization failed
            }

            switch (event.getType()) {
                case "payment_intent.succeeded":
                    // Handle the event
                    break;
                default:
                    System.out.println("Unhandled event type: " + event.getType());
            }
        } catch (JsonSyntaxException | SignatureVerificationException e) {
            throw new RuntimeException("Webhook error: " + e.getMessage());
        }

    }
}








