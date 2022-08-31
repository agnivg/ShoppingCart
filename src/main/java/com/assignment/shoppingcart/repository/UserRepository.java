package com.assignment.shoppingcart.repository;

import com.assignment.shoppingcart.model.Role;
import com.assignment.shoppingcart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String s);
    List<User> findAllByRole(Role role);
}