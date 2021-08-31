package com.uploder.demo.service.implementation;

import com.uploder.demo.dto.UserDto;
import com.uploder.demo.entity.User;
import com.uploder.demo.exception.InvalidOperationException;
import com.uploder.demo.repository.IUserRepository;
import com.uploder.demo.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.uploder.demo.exception.ErrorMessage.USER_ALREADY_EXISTS;

@Service
public class UserImpl implements IUserService {

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    private final IUserRepository userRepository;

    public UserImpl(IUserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public UserDto register(UserDto dto) {

        Boolean isExist = userRepository.existsByEmail(dto.getEmail());

        if (isExist) {
            throw new InvalidOperationException(USER_ALREADY_EXISTS);
        } else {
            User user = new User();
            String encodedPassword = passwordEncoder.encode(dto.getPassword());
            user.setEmail(dto.getEmail());
            user.setPassword(encodedPassword);
            user.setFirstname(dto.getFirstname());
            user.setLastname(dto.getLastname());
            UserDto userDtoCreated = modelMapper.map(userRepository.save(user), UserDto.class);
            return userDtoCreated;
        }
    }

}
