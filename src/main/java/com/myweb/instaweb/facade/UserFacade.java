package com.myweb.instaweb.facade;




/*
 * @author
 * @version
 * @return
 */

import com.myweb.instaweb.dto.UserDTO;
import com.myweb.instaweb.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserFacade {

    public UserDTO userToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstname(user.getName());
        userDTO.setLastname(user.getLastname());
        userDTO.setBio(user.getBio());
        return userDTO;
    }
}
