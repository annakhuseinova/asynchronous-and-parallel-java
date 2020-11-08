package com.annakhuseinova.service;

import com.annakhuseinova.domain.Cart;
import com.annakhuseinova.domain.CartItem;
import com.annakhuseinova.domain.CheckoutResponse;

import java.util.List;
import java.util.stream.Collectors;

import static com.annakhuseinova.domain.CheckoutStatus.*;
import static com.annakhuseinova.util.CommonUtil.startTimer;
import static com.annakhuseinova.util.CommonUtil.timeTaken;

public class CheckoutService {

    private PriceValidatorService priceValidatorService;

    public CheckoutService(PriceValidatorService priceValidatorService) {
        this.priceValidatorService = priceValidatorService;
    }

    public CheckoutResponse checkout(Cart cart){
        startTimer();
       List<CartItem> priceValidationList = cart.getCartItemList()
                .stream()
                .map(cartItem -> {
                    boolean isPriceInvalid = priceValidatorService.isCartItemInvalid(cartItem);
                    cartItem.setExpired(isPriceInvalid);
                    return cartItem;
                })
                .filter(CartItem::isExpired)
                .collect(Collectors.toList());

       if (priceValidationList.size() > 0){
           return new CheckoutResponse(FAILURE, priceValidationList);
       }
       timeTaken();
        return new CheckoutResponse(SUCCESS);
    }
}
