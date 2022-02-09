package com.nikolai.network.service.impl;

import com.nikolai.network.dto.UserDto;
import com.nikolai.network.model.User;
import com.nikolai.network.repository.UserRepository;
import com.nikolai.network.service.interfaces.BaseService;
import com.nikolai.network.utils.DtoConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class BaseServiceImpl implements BaseService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DtoConvert dtoConvert;

    public String dateNow() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String format = formatter.format(new Date());
        return format;
    }

    public UserDto getUserDto(String email) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));
        UserDto userDto = dtoConvert.convertToUserDto(user.get());

        return userDto;
    }
}
