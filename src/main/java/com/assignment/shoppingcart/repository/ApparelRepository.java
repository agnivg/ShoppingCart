package com.assignment.shoppingcart.repository;

import com.assignment.shoppingcart.model.Apparel;
import com.assignment.shoppingcart.model.Season;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ApparelRepository extends JpaRepository<Apparel, UUID> {
    List<Apparel> findAllBySeason(Season season);
}