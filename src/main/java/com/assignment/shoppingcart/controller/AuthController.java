package com.assignment.shoppingcart.controller;

import com.assignment.shoppingcart.model.UserDto;
import com.assignment.shoppingcart.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("user")
    UserDto userDto()
    {
        return new UserDto();
    }
    
    @RequestMapping("/")
    public String homepage() {
    	return "home";
    }
    
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }   
    
    @GetMapping("/loginadmin")
    public String loginFormAdmin() {
        return "loginadmin";
    }

    @GetMapping("/signup")
    public String signupForm() {
        return "signup";
    }
    
    @GetMapping("/home")
    public String home() {
        return "temphome";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("user") UserDto dto) {
        if(userService.isRegistered(dto))
            return "redirect:/signup";
        userService.save(dto);
        return "redirect:/login";
    }
}
