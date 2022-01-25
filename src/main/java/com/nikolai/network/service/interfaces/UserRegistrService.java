package com.nikolai.network.service.interfaces;

import com.nikolai.network.dto.UserRegistrDto;
import com.nikolai.network.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserRegistrService extends UserDetailsService {

    User findByEmail(String email);
    User saveUser(UserRegistrDto userRegistrDto);
}
