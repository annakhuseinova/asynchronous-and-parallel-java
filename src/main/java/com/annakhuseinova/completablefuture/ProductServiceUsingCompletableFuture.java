package com.annakhuseinova.completablefuture;

import com.annakhuseinova.domain.Product;
import com.annakhuseinova.domain.ProductInfo;
import com.annakhuseinova.domain.Review;
import com.annakhuseinova.service.ProductInfoService;
import com.annakhuseinova.service.ReviewService;

import java.util.concurrent.CompletableFuture;

import static com.annakhuseinova.util.CommonUtil.stopWatch;

public class ProductServiceUsingCompletableFuture {

    private ProductInfoService productInfoService;
    private ReviewService reviewService;

    public ProductServiceUsingCompletableFuture(ProductInfoService productInfoService, ReviewService reviewService) {
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

    public CompletableFuture<Product> retrieveProductDetails_nonBlocking(String productId) {
        stopWatch.start();
        CompletableFuture<ProductInfo> cfProductInfo = CompletableFuture.supplyAsync(()->
                productInfoService.retrieveProductInfo(productId));
        CompletableFuture<Review> reviewCompletableFuture = CompletableFuture.supplyAsync(()->
                reviewService.retrieveReviews(productId));
        stopWatch.stop();
        return cfProductInfo.thenCombine(reviewCompletableFuture, (productInfo, review)->
                new Product(productId, productInfo, review));
//        ProductInfo productInfo = productInfoService.retrieveProductInfo(productId); // blocking call
//        Review review = reviewService.retrieveReviews(productId); // blocking call
    }
}
