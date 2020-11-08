package com.annakhuseinova.service;

import com.annakhuseinova.domain.Cart;
import com.annakhuseinova.domain.CheckoutResponse;
import com.annakhuseinova.domain.CheckoutStatus;
import com.annakhuseinova.util.DataSet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CheckoutServiceTest {

    PriceValidatorService priceValidatorService = new PriceValidatorService();
    CheckoutService checkoutService = new CheckoutService(priceValidatorService);

    @Test
    void checkout() {
        Cart cart = DataSet.createCart(6);
        CheckoutResponse checkoutResponse = checkoutService.checkout(cart);
        assertEquals(CheckoutStatus.SUCCESS, checkoutResponse.getCheckoutStatus());
    }

    @Test
    void showNumberOfCores() {
        System.out.println("Number of cores: " + Runtime.getRuntime().availableProcessors());
    }
}