package com.annakhuseinova.service;

import com.annakhuseinova.completablefuture.ProductServiceUsingCompletableFuture;
import com.annakhuseinova.domain.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductServiceTest {

    private ProductInfoService productInfoService = new ProductInfoService();
    private ReviewService reviewService = new ReviewService();
    private ProductServiceUsingCompletableFuture productServiceUsingCompletableFuture =
            new ProductServiceUsingCompletableFuture(productInfoService, reviewService);

    @Test
    void retrieveProductDetails() {

        String productId = "ABCC123";
        Product product = productServiceUsingCompletableFuture.retrieveProductDetails(productId);
        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        assertNotNull(product.getReview());
    }
}