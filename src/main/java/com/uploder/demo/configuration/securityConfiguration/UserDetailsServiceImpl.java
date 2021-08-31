package com.uploder.demo.configuration.securityConfiguration;

import com.uploder.demo.dto.UserDto;
import com.uploder.demo.entity.User;
import com.uploder.demo.exception.EntityNotFoundException;
import com.uploder.demo.repository.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.uploder.demo.exception.ErrorMessage.USER_NOT_FOUND_BY_EMAIL;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UserDetailsServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Return user's informations !
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new EntityNotFoundException(USER_NOT_FOUND_BY_EMAIL));
        UserDto userDto = modelMapper.map(user, UserDto.class);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("User"));
        return new ExtendedUser(userDto.getEmail(), userDto.getPassword(), authorities, userDto);
    }
}
