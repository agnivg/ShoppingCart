package com.assignment.shoppingcart.repository;

import com.assignment.shoppingcart.model.Purchase;
import com.assignment.shoppingcart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findAllByUser(User user);
}