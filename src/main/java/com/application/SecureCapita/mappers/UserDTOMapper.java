package com.application.SecureCapita.mappers;

import com.application.SecureCapita.dto.UserDTO;
import com.application.SecureCapita.models.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/*
 * @author Tyrien Gilpin
 * @version 1.0
 * @since 16/01/2024 dd/mm/yyyy
 * */

@Component
public class UserDTOMapper {
    public static UserDTO fromUser(User user){
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }
  public static User toUser(UserDTO userDTO){
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        return user;
    }






}
