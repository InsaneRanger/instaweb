package com.myweb.instaweb.entity;




/*
 * @author
 * @version
 * @return
 */


import javax.persistence.PrePersist;
import java.time.LocalDateTime;

public class Comment {

    private Long Id;
    private Post post;
    private String username;
    private Long userId;
    private String message;
    private LocalDateTime createdDate;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }

}
