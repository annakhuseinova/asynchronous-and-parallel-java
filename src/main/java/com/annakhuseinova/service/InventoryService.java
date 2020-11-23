package com.annakhuseinova.service;

import com.annakhuseinova.domain.Inventory;
import com.annakhuseinova.domain.ProductOption;

import static com.annakhuseinova.util.CommonUtil.delay;

public class InventoryService {

    public Inventory retrieveInventory(ProductOption productOption){
        delay(1000);
        return Inventory.builder()
                .count(2)
                .build();
    }
}
