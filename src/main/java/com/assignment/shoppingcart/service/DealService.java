package com.assignment.shoppingcart.service;

import com.assignment.shoppingcart.model.DealDto;
import com.assignment.shoppingcart.model.Deal;

import java.util.UUID;

public interface DealService {
    public double getDiscountedPrice(UUID apparelId);
    public Deal save(DealDto dealDto);
}
