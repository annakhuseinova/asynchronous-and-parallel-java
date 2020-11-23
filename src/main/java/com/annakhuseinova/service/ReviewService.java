package com.annakhuseinova.service;

import com.annakhuseinova.domain.Review;

import static com.annakhuseinova.util.CommonUtil.delay;

public class ReviewService {

    public Review retrieveReviews(String productId){
        delay(1000);
        return new Review(200, 4.5);
    }

}
