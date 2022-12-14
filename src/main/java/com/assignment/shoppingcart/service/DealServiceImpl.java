package com.assignment.shoppingcart.service;

import com.assignment.shoppingcart.model.DealDto;
import com.assignment.shoppingcart.model.Apparel;
import com.assignment.shoppingcart.model.Deal;
import com.assignment.shoppingcart.repository.ApparelRepository;
import com.assignment.shoppingcart.repository.DealRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class DealServiceImpl implements DealService {
    private final DealRepository dealRepository;
    private final ApparelRepository apparelRepository;

    public DealServiceImpl(DealRepository dealRepository, ApparelRepository apparelRepository) {
        this.dealRepository = dealRepository;
        this.apparelRepository = apparelRepository;
    }

    @Override
    public double getDiscountedPrice(UUID apparelId) {

        double percentage = Optional.ofNullable(dealRepository.findByApparel_Id(apparelId))
                .map(deal -> {
                    if (deal.getValidUpto().isBefore(LocalDateTime.now())) {
                        dealRepository.delete(deal);
                        return 0.0;
                    } else if (deal.getValidFrom().isAfter(LocalDateTime.now())) {
                        return 0.0;
                    }
                    return deal.getPercentage();
                }).orElse(0.0);
        Apparel apparel = apparelRepository.getById(apparelId);
        return apparel.getPrice() * (1 - percentage / 100);
    }

    @Override
    public Deal save(DealDto dealDto) {
        if(dealDto.getValidFrom().isEmpty())
            dealDto.setValidFrom(LocalDateTime.now().toString());       
        Deal deal = new Deal(apparelRepository.getById(dealDto.getApparelId()),
                dealDto.getPercentage(),
                LocalDateTime.parse(dealDto.getValidFrom()),
                LocalDateTime.parse(dealDto.getValidUpto())
        );
        return dealRepository.save(deal);
    }
}
