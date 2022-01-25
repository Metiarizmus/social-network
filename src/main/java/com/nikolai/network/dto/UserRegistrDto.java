package com.nikolai.network.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserRegistrDto {

    @Size(min = 2, max = 10)
    private String firstName;

    @Size(min = 2, max = 10)
    private String lastName;

    @Email
    private String email;

    @Size(min = 4, max = 20)
    private String password;

    @Size(min = 4, max = 20)
    private String confirmPassword;

    @NotNull
    private String birthday;

    private String dateRegistr;

}
