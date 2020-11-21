package com.annakhuseinova.service;

import com.annakhuseinova.domain.Cart;
import com.annakhuseinova.domain.CartItem;
import com.annakhuseinova.domain.CheckoutResponse;

import java.util.List;
import java.util.stream.Collectors;

import static com.annakhuseinova.domain.CheckoutStatus.*;
import static com.annakhuseinova.util.CommonUtil.startTimer;
import static com.annakhuseinova.util.CommonUtil.timeTaken;
import static java.util.stream.Collectors.summingDouble;

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
       double finalPrice = calculateFinalPrice(cart);
       timeTaken();
        return new CheckoutResponse(SUCCESS);
    }

    private double calculateFinalPrice(Cart cart){
        return cart.getCartItemList().parallelStream().map(cartItem -> cartItem.getQuantity() * cartItem.getRate()).
                mapToDouble(Double::doubleValue).sum();
    }

    private double calculateFinalPrice_reduce(Cart cart){
        return cart.getCartItemList().parallelStream().map(cartItem -> cartItem.getQuantity()  * cartItem.getRate())
                .reduce(0.0, Double::sum);
    }
}
