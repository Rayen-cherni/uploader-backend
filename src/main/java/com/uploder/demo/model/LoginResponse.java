package com.uploder.demo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    private String token;

    private String firstname;

    private String lastname;

    private String email;
}
