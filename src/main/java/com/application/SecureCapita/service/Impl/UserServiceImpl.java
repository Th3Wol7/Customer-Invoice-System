package com.application.SecureCapita.service.Impl;

import com.application.SecureCapita.dto.UserDTO;
import com.application.SecureCapita.mappers.UserDTOMapper;
import com.application.SecureCapita.models.User;
import com.application.SecureCapita.repositories.UserRepository;
import com.application.SecureCapita.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/*
 * @author Tyrien Gilpin
 * @version 1.0
 * @since 16/01/2024 dd/mm/yyyy
 * */

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository<User> userRepository;
    @Override
    public UserDTO create(User user) {
        return UserDTOMapper.fromUser(userRepository.create(user));
    }
}
