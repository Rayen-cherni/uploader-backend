package com.uploder.demo.configuration.securityConfiguration;

import com.uploder.demo.dto.UserDto;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class ExtendedUser extends User {

    @Getter
    UserDto userDto;

    public ExtendedUser(String username, String password, Collection<? extends GrantedAuthority> authorities, UserDto userDto) {
        super(username, password, authorities);
        this.userDto = userDto;
    }
}
