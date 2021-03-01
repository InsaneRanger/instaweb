package com.myweb.instaweb.payload.response;




/*
 * @author
 * @version
 * @return
 */

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class JWTTokenSuccessResponse {
    private boolean success;
    private String token;
}
