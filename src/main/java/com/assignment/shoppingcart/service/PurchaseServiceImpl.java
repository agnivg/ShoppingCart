package com.assignment.shoppingcart.service;

import com.assignment.shoppingcart.model.Purchase;
import com.assignment.shoppingcart.repository.ApparelRepository;
import com.assignment.shoppingcart.repository.PurchaseRepository;
import com.assignment.shoppingcart.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    private final ApparelRepository apparelRepository;
    private final UserRepository userRepository;
    private final PurchaseRepository purchaseRepository;

    public PurchaseServiceImpl(ApparelRepository apparelRepository, UserRepository userRepository, PurchaseRepository purchaseRepository) {
        this.apparelRepository = apparelRepository;
        this.userRepository = userRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    public Purchase purchase(UUID apparelId, String username) {
        Purchase purchase = new Purchase();
        purchase.setApparel(apparelRepository.getById(apparelId));
        purchase.setUser(userRepository.findByEmail(username));
        return purchaseRepository.save(purchase);
    }
}
