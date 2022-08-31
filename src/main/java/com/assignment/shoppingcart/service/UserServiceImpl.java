package com.assignment.shoppingcart.service;

import com.assignment.shoppingcart.model.UserDto;
import com.assignment.shoppingcart.model.Purchase;
import com.assignment.shoppingcart.model.Role;
import com.assignment.shoppingcart.model.User;
import com.assignment.shoppingcart.repository.PurchaseRepository;
import com.assignment.shoppingcart.repository.RoleRepository;
import com.assignment.shoppingcart.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final String ROLE_USER = "ROLE_USER";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PurchaseRepository purchaseRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PurchaseRepository purchaseRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.purchaseRepository = purchaseRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User save(UserDto dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        User user = new User(dto);
        Role role = Optional.ofNullable(roleRepository.findByName(ROLE_USER)).orElse(new Role(ROLE_USER));
        user.setRole(role);
        return userRepository.save(user);
    }

    @Override
    public User saveAdmin(UserDto dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        User user = new User(dto);
        Role role = Optional.ofNullable(roleRepository.findByName(ROLE_ADMIN)).orElse(new Role(ROLE_ADMIN));
        user.setRole(role);
        return userRepository.save(user);
    }

    @Override
    public boolean isRegistered(UserDto dto) {
        return userRepository.findByEmail(dto.getEmail()) != null;
    }

    @Override
    public List<Purchase> getPurchases(String email) {
        return purchaseRepository.findAllByUser(userRepository.findByEmail(email));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if(user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                mapRolesToAuthorities(Collections.singleton(user.getRole())));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles)
    {
        return roles.stream().map(Role::getName).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
