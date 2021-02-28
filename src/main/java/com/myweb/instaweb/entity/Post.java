package com.myweb.instaweb.entity;




/*
 * @author
 * @version
 * @return
 */


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String caption;
    private String location;
    private Integer likes;


    @Column
    @ElementCollection(targetClass = String.class)
    private Set<String> likedUsers = new HashSet<>();

    /**
     * Many posts -> one user
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    /**
     * One post ->many comments.
     * CT. REFRESH -> When we refresh an entity all the entities
     * held in this field refresh too.
     * Fetch EAGER to see the comments immediately
     */
    @OneToMany(
            cascade = CascadeType.REFRESH,
            fetch = FetchType.EAGER,
            mappedBy = "post",
            orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }


}
