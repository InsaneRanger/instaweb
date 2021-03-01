package com.myweb.instaweb.service;




/*
 * @author
 * @version
 * @return
 */

import com.myweb.instaweb.dto.UserDTO;
import com.myweb.instaweb.entity.User;
import com.myweb.instaweb.entity.roles.Roles;
import com.myweb.instaweb.exceptions.UserExistException;
import com.myweb.instaweb.payload.request.SignupRequest;
import com.myweb.instaweb.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    /**
     * Mapping model attribute and Bcrypt password from database
     */
    public User createUser(SignupRequest userIn) {
        User user = new User();
        user.setEmail(userIn.getEmail());
        user.setName(userIn.getFirstname());
        user.setLastname(userIn.getLastname());
        user.setUsername(userIn.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userIn.getPassword()));
        user.getRoles().add(Roles.ROLE_USER);

        try {
            log.info("Saving User {}", userIn.getEmail());
            return userRepository.save(user);
        } catch (Exception e) {
            log.error("Error during registration. {}", e.getMessage());
            throw new UserExistException("The user " + user.getUsername() + " already exist. Please check credentials");
        }
    }

    public User userUpdate(UserDTO userDTO, Principal principal){
        User user = getUserByPrincipal(principal);
        user.setName(userDTO.getUsername());
        user.setLastname(userDTO.getLastname());
        user.setBio(userDTO.getBio());

        return userRepository.save(user);
    }

    private User getCurrentUser(Principal principal){
        return getUserByPrincipal(principal);
    }

    private User getUserByPrincipal(Principal principal){
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("Username not found with username "+username));
    }
}
