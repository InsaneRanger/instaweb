package com.myweb.instaweb.payload.response;




/*
 * @author
 * @version
 * @return
 */

import lombok.Getter;

@Getter
public class InvalidLoginResponse {

    private String username;
    private String password;

    public InvalidLoginResponse() {
        this.username = "Invalid username";
        this.password = "Invalid password";
    }
}
