package com.assignment.shoppingcart.controller;

import com.assignment.shoppingcart.model.DealDto;
import com.assignment.shoppingcart.model.Deal;
import com.assignment.shoppingcart.service.DealService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/admin/deal")
public class DealController {
    private final DealService dealService;


    public DealController(DealService dealService) {
        this.dealService = dealService;
    }

    @PostMapping("")
    String createDeal(@ModelAttribute("deal") DealDto dto)
    {
        if(dto.getValidFrom() == null || dto.getValidFrom().isEmpty())
        {
            dto.setValidFrom(LocalDateTime.now().toString());
        }

        Deal deal = dealService.save(dto);
        if(deal == null)
        {
            return "redirect:/admin";
        }
        return "redirect:/admin";
    }


}
