package com.assignment.shoppingcart.repository;

import com.assignment.shoppingcart.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}