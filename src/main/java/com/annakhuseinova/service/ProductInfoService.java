package com.annakhuseinova.service;

import com.annakhuseinova.domain.ProductInfo;
import com.annakhuseinova.domain.ProductOption;

import java.util.List;

import static com.annakhuseinova.util.CommonUtil.delay;

public class ProductInfoService {

    public ProductInfo retrieveProductInfo(String productId){
        delay(1000);
        List<ProductOption> productOptions = List.of(new ProductOption(1, "64GB", "Black",
                699.99));
        return ProductInfo.builder()
                .productId(productId)
                .productOptions(productOptions)
                .build();
    }
}
