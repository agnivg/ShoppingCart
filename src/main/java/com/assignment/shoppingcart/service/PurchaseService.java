package com.assignment.shoppingcart.service;

import com.assignment.shoppingcart.model.Purchase;

import java.util.UUID;

public interface PurchaseService {
    public Purchase purchase(UUID id, String username);
}
