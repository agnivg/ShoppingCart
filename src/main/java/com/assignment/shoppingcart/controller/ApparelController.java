package com.assignment.shoppingcart.controller;

import com.assignment.shoppingcart.model.ApparelDto;
import com.assignment.shoppingcart.model.Apparel;
import com.assignment.shoppingcart.service.ApparelService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/apparel")
public class ApparelController {

    private ApparelService apparelService;

    public ApparelController(ApparelService apparelService) {
        this.apparelService = apparelService;
    }

    @PostMapping("")
    String createApparel(@ModelAttribute("apparel") ApparelDto dto)
    {
        Apparel apparel = apparelService.save(dto);
        if(apparel == null)
        {
            return "redirect:/admin";
        }
        return "redirect:/admin";
    }

}
