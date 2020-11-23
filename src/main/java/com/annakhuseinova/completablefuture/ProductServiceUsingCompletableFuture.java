package com.annakhuseinova.completablefuture;

import com.annakhuseinova.domain.*;
import com.annakhuseinova.service.InventoryService;
import com.annakhuseinova.service.ProductInfoService;
import com.annakhuseinova.service.ReviewService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.annakhuseinova.util.CommonUtil.stopWatch;

public class ProductServiceUsingCompletableFuture {

    private ProductInfoService productInfoService;
    private ReviewService reviewService;
    private InventoryService inventoryService;

    public ProductServiceUsingCompletableFuture(ProductInfoService productInfoService,
                                                ReviewService reviewService,
                                                InventoryService inventoryService) {
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

    public Product retrieveProductDetailsWithInventory(String productId) {
        stopWatch.start();
        CompletableFuture<ProductInfo> cfProductInfo = CompletableFuture.supplyAsync(()->
                    productInfoService.retrieveProductInfo(productId)).thenApply(productInfo -> {
                    productInfo.setProductOptions(updateInventory(productInfo));
                    return productInfo;
                });
        CompletableFuture<Review> reviewCompletableFuture = CompletableFuture.supplyAsync(()->
                reviewService.retrieveReviews(productId));
        Product product = cfProductInfo.thenCombine(reviewCompletableFuture, (productInfo, review)->
                new Product(productId, productInfo, review)).join();
//        ProductInfo productInfo = productInfoService.retrieveProductInfo(productId); // blocking call
//        Review review = reviewService.retrieveReviews(productId); // blocking call
        stopWatch.stop();
        return product;
    }

    private List<ProductOption> updateInventory(ProductInfo productInfo){
       List<ProductOption> productOptionList = productInfo.getProductOptions().stream().map(productOption -> {
           Inventory inventory = inventoryService.retrieveInventory(productOption);
           productOption.setInventory(inventory);
           return productOption;
        }).collect(Collectors.toList());
       return productOptionList;
    }

    private List<ProductOption> updateInventory_approach2(ProductInfo productInfo){
        List<CompletableFuture<ProductOption>> productOptionList = productInfo.getProductOptions().stream().map(productOption -> {
             CompletableFuture.supplyAsync(()-> inventoryService.retrieveInventory(productOption)).thenApply(
                    inventory -> {
                        productOption.setInventory(inventory);
                        return productOption;
                    });
        }).collect(Collectors.toList());
        return productOptionList.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }
}
