package com.annakhuseinova.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    private Integer itemId;
    private String itemName;
    private double rate;
    private Integer quantity;
    private boolean isExpired;
}
