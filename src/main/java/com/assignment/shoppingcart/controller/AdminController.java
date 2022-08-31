package com.assignment.shoppingcart.controller;

import com.assignment.shoppingcart.model.ApparelDto;
import com.assignment.shoppingcart.model.DealDto;
import com.assignment.shoppingcart.model.UserDto;
import com.assignment.shoppingcart.service.ApparelService;
import com.assignment.shoppingcart.service.DealService;
import com.assignment.shoppingcart.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.stream.Collectors;

@Controller
@RequestMapping("admin")
public class AdminController {
    private final UserService userService;
    private final ApparelService apparelService;
    private final DealService dealService;

    public AdminController(UserService userService, ApparelService apparelService, DealService dealService) {
        this.userService = userService;
        this.apparelService = apparelService;
        this.dealService = dealService;
    }

    @ModelAttribute("user")
    UserDto userDto() {
        return new UserDto();
    }

    @ModelAttribute("apparel")
    ApparelDto apparelDto()
    {
        return new ApparelDto();
    }

    @ModelAttribute("deal")
    DealDto dealDto()
    {
        return new DealDto();
    }

    @GetMapping("/signup")
    public String signupForm() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("user") UserDto dto) {
        if (userService.isRegistered(dto))
            return "redirect:/admin/signup";
        userService.saveAdmin(dto);
        return "redirect:/loginadmin";
    }

    @GetMapping("")
    public String adminPanel(@RequestParam(required = false) String q, Model model) {
        if (q == null || q.isEmpty())
            model.addAttribute("apparelList", apparelService.listApparel()
                    .stream().map(ApparelDto::new)
                    .peek(apparelDto -> apparelDto.setDiscountedPrice(dealService.getDiscountedPrice(apparelDto.getId())))
                    .collect(Collectors.toList()));
        else {
            model.addAttribute("apparelList", apparelService.listApparel()
                    .stream()
                    .filter(apparel -> apparel.getGenericName().toLowerCase(Locale.ROOT).startsWith(q.toLowerCase(Locale.ROOT)))
                    .map(ApparelDto::new)
                    .peek(apparelDto -> apparelDto.setDiscountedPrice(dealService.getDiscountedPrice(apparelDto.getId())))
                    .collect(Collectors.toList()));
        }
        return "admin";
    }

}
