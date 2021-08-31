package com.uploder.demo.controller;

import com.uploder.demo.configuration.securityConfiguration.ExtendedUser;
import com.uploder.demo.configuration.securityConfiguration.UserDetailsServiceImpl;
import com.uploder.demo.configuration.securityConfiguration.jwt.JwtProvider;
import com.uploder.demo.dto.UserDto;
import com.uploder.demo.model.LoginRequest;
import com.uploder.demo.model.LoginResponse;
import com.uploder.demo.service.IUserService;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static utils.Constants.USER_ENDPOINT;

@RestController
@RequestMapping(USER_ENDPOINT)
@Api(USER_ENDPOINT)
public class UserController {

    private final IUserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtProvider provider;

    public UserController(IUserService userService, AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService, JwtProvider provider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.provider = provider;
    }


    //FIXME Make it with reponse entity !!
    @PostMapping(value = "/register",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> register(@RequestBody UserDto dto) {
        return ResponseEntity.ok(userService.register(dto));
    }

    @PostMapping(value = "/login",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());

        final String jwt = provider.generateToken((ExtendedUser) userDetails);

        return ResponseEntity.ok(
                LoginResponse
                        .builder()
                        .token(jwt)
                        .email(((ExtendedUser) userDetails).getUserDto().getEmail())
                        .firstname(((ExtendedUser) userDetails).getUserDto().getFirstname())
                        .lastname(((ExtendedUser) userDetails).getUserDto().getLastname())
                        .build()
        );
    }
}
