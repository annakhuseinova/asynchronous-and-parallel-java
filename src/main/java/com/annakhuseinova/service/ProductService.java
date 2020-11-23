package com.annakhuseinova.service;

import com.annakhuseinova.domain.Product;
import com.annakhuseinova.domain.ProductInfo;
import com.annakhuseinova.domain.Review;

import java.util.concurrent.CompletableFuture;

import static com.annakhuseinova.util.CommonUtil.stopWatch;

public class ProductService {
    private ProductInfoService productInfoService;
    private ReviewService reviewService;

    public ProductService(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public Product retrieveProductDetails(String productId) {
        stopWatch.start();
        CompletableFuture<ProductInfo> cfProductInfo = CompletableFuture.supplyAsync(()->
                productInfoService.retrieveProductInfo(productId));
        CompletableFuture<Review> reviewCompletableFuture = CompletableFuture.supplyAsync(()->
                reviewService.retrieveReviews(productId));
        Product product = cfProductInfo.thenCombine(reviewCompletableFuture, (productInfo, review)->
                new Product(productId, productInfo, review)).join();
//        ProductInfo productInfo = productInfoService.retrieveProductInfo(productId); // blocking call
//        Review review = reviewService.retrieveReviews(productId); // blocking call
        stopWatch.stop();
        return product;
    }

    public static void main(String[] args) {
        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductService productService = new ProductService(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
    }
}
