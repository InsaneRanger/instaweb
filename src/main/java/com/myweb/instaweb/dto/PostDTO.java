package com.myweb.instaweb.dto;




/*
 * @author
 * @version
 * @return
 */

import lombok.Data;

import java.util.Set;

@Data
public class PostDTO {

    private Long id;
    private String title;
    private String caption;
    private String location;
    private String username;
    private Integer likes;
    private Set<String> userLiked;

}
