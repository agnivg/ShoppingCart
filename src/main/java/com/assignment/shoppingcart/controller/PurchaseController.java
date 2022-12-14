package com.assignment.shoppingcart.controller;

import com.assignment.shoppingcart.model.PurchaseDto;
import com.assignment.shoppingcart.metric.Statistics;
import com.assignment.shoppingcart.model.Purchase;
import com.assignment.shoppingcart.service.PurchaseService;
import com.assignment.shoppingcart.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class PurchaseController {
    private final PurchaseService purchaseService;
    private final UserService userService;


    PurchaseController(PurchaseService purchaseService, UserService userService)
    {
        this.purchaseService = purchaseService;
        this.userService = userService;
    }

    @GetMapping("/user/purchases")
    String purchaseList(@RequestParam(required = false) String q, Model model, Authentication auth)
    {
        User user = (User) auth.getPrincipal();
        List<PurchaseDto> purchases = userService
                .getPurchases(user.getUsername())
                .stream()
                .map(PurchaseDto::new)
                .collect(Collectors.toList());
        if(q != null && !q.isEmpty())
        {
            purchases = purchases.stream().filter(purchase -> purchase.getApparel().getGenericName().toLowerCase(Locale.ROOT).startsWith(q.toLowerCase(Locale.ROOT)))
            		.collect(Collectors.toList());
        }
        model.addAttribute("purchases", purchases);
        return "purchases";
    }

    @PostMapping("/user/purchase")
    String purchase(@RequestParam UUID apparelId, @SessionAttribute Statistics metric, Authentication auth)
    {
        User user = (User) auth.getPrincipal();
        if(apparelId == null)
            return "redirect:/error";

        Purchase purchase = purchaseService.purchase(apparelId, user.getUsername());
        metric.update(purchase.getApparel().getPrice());
        return "redirect:/user/purchases";
    }
}
