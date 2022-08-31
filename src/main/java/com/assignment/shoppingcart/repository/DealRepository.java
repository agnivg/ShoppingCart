package com.assignment.shoppingcart.repository;

import com.assignment.shoppingcart.model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DealRepository extends JpaRepository<Deal, Long> {
    Deal findByApparel_Id(UUID id);
}