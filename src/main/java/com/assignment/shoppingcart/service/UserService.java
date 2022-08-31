package com.assignment.shoppingcart.service;

import com.assignment.shoppingcart.model.UserDto;
import com.assignment.shoppingcart.model.Purchase;
import com.assignment.shoppingcart.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    public User save(UserDto dto);
    public User saveAdmin(UserDto dto);
    public boolean isRegistered(UserDto dto);
    public List<Purchase> getPurchases(String email);
}
