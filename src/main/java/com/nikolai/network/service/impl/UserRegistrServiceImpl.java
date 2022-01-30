package com.nikolai.network.service.impl;

import com.nikolai.network.dto.UserRegistrDto;
import com.nikolai.network.enums.EnumRoles;
import com.nikolai.network.model.Role;
import com.nikolai.network.model.User;
import com.nikolai.network.repository.UserRepository;
import com.nikolai.network.service.interfaces.UserRegistrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class UserRegistrServiceImpl extends BaseServiceImpl implements UserRegistrService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;

    public UserRegistrServiceImpl(@Lazy BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User saveUser(UserRegistrDto userRegistrDto) {
        User user = new User();
        user.setFirstName(userRegistrDto.getFirstName());
        user.setLastName(userRegistrDto.getLastName());
        user.setEmail(userRegistrDto.getEmail());
        user.setPassword(passwordEncoder.encode(userRegistrDto.getPassword()));
        user.setBirthday(convertDateToFormat(userRegistrDto.getBirthday()));
        user.setDate(dateNow());
        user.setRoles(Collections.singleton(new Role(EnumRoles.ROLE_USER)));

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getNameRoles().name()))
                .collect(Collectors.toList());
    }


}
