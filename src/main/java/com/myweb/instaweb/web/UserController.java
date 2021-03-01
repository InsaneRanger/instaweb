package com.myweb.instaweb.web;




/*
 * @author
 * @version
 * @return
 */

import com.myweb.instaweb.dto.UserDTO;
import com.myweb.instaweb.entity.User;
import com.myweb.instaweb.facade.UserFacade;
import com.myweb.instaweb.service.UserService;
import com.myweb.instaweb.validators.ResponseErrorValidation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    private final UserService userService;
    private final ResponseErrorValidation responseErrorValidation;
    private final UserFacade userFacade;

    public UserController(UserService userService, ResponseErrorValidation responseErrorValidation, UserFacade userFacade) {
        this.userService = userService;
        this.responseErrorValidation = responseErrorValidation;
        this.userFacade = userFacade;
    }

    @GetMapping("/")
    public ResponseEntity<UserDTO> getCurrentUser(Principal principal) {
        User user = userService.getCurrentUser(principal);
        UserDTO userDTO = userFacade.userToUserDTO(user);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }


    @GetMapping("{userId}")
    public ResponseEntity<UserDTO> getUserProfile(@PathVariable("userId") String userId){
        User user = userService.getUserById(Long.parseLong(userId));
        UserDTO userDTO = userFacade.userToUserDTO(user);

        return new ResponseEntity<>(userDTO,HttpStatus.OK);
    }


    @PostMapping("/update")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult, Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        User user = userService.userUpdate(userDTO, principal);

        UserDTO userUpdated = userFacade.userToUserDTO(user);
        return new ResponseEntity<>(userUpdated, HttpStatus.OK);
    }

}
