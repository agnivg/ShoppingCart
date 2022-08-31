package com.assignment.shoppingcart.controller;

import com.assignment.shoppingcart.model.ApparelDto;
import com.assignment.shoppingcart.metric.Statistics;
import com.assignment.shoppingcart.model.Apparel;
import com.assignment.shoppingcart.model.Purchase;
import com.assignment.shoppingcart.service.ApparelService;
import com.assignment.shoppingcart.service.DealService;
import com.assignment.shoppingcart.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


@Controller
public class MainController {
    private final ApparelService apparelService;
    private final DealService dealService;
    private final UserService userService;

    public MainController(ApparelService apparelService, DealService dealService, UserService userService) {
        this.apparelService = apparelService;
        this.dealService = dealService;
        this.userService = userService;
    }

    @GetMapping("/user")
    public String home(@RequestParam(required = false) String q, Authentication auth, Model model, HttpSession session) {
        Statistics metric = (Statistics) session.getAttribute("metric");
        User user = (User) auth.getPrincipal();

        if (metric == null) {
            metric = new Statistics();
            userService.getPurchases(user.getUsername()).stream().map(Purchase::getApparel).mapToDouble(Apparel::getPrice).forEach(metric::update);
            session.setAttribute("metric", metric);
        }

        if (q == null || q.isEmpty()) {

            List<ApparelDto> apparelList = apparelService.listApparel(user.getUsername()).stream().map(ApparelDto::new)
                    .peek(apparelDto -> apparelDto.setDiscountedPrice(dealService.getDiscountedPrice(apparelDto.getId())))
                    .collect(Collectors.toList());
            model.addAttribute("apparelList", sortAccordingToStats(apparelList, metric));
        } 
        else {
            List<ApparelDto> apparelList = apparelService.listApparel()
                    .stream()
                    .filter(apparel -> apparel.getGenericName().toLowerCase(Locale.ROOT).startsWith(q.toLowerCase(Locale.ROOT)))
                    .map(ApparelDto::new)
                    .peek(apparelDto -> apparelDto.setDiscountedPrice(dealService.getDiscountedPrice(apparelDto.getId())))
                    .collect(Collectors.toList());
            model.addAttribute("apparelList", sortAccordingToStats(apparelList, metric));
        }
        return "index";
    }

    private List<ApparelDto> sortAccordingToStats(List<ApparelDto> apparels, Statistics metric) {
        apparels.sort((a, b) -> {
            return Double.compare(Math.abs(a.getPrice() - metric.getMean()), Math.abs(b.getPrice() - metric.getMean()));
        });

        return apparels;
    }
}
