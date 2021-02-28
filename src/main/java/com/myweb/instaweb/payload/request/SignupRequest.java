package com.myweb.instaweb.payload.request;




/*
 * @author
 * @version
 * @return
 */

import com.myweb.instaweb.annotations.PasswordMatches;
import com.myweb.instaweb.annotations.ValidEmail;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@PasswordMatches
public class SignupRequest {

    @Email(message = "It should have email format")
    @NotBlank(message = "User email us required")
    @ValidEmail
    private String email;
    @NotEmpty(message = "Enter your name")
    private String firstname;
    @NotEmpty(message = "Enter your lastname")
    private String lastname;
    @NotEmpty(message = "Enter your username")
    private String username;
    @NotEmpty(message = "Password is required")
    @Size(min = 6)
    private String password;
    @NotEmpty(message = "Confirm your password")
    private String confirmPassword;
}
