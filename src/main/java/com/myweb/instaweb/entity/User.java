package com.myweb.instaweb.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myweb.instaweb.entity.roles.Roles;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity
@NoArgsConstructor
public class User {

    /** For db Generation Type*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** Not null*/
    @Column(nullable = false)
    private String name;
    /** Unique, not update */
    @Column(unique = true, updatable = false)
    private String username;
    /** Not Null*/
    @Column(nullable = false)
    private String lastname;
    /** Unique */
    @Column(unique = true)
    private String email;

    /** Increase field capacity
     * ( MB we make use @Lob @Type(type = "org.hibernate.type.TextType")
     *  for Postgres, because PG have LOB 2 TYPES -> BLOB, CLOB. If you use
     *  Oracle it is enough to use @Lob or @Column(columnDefinition = "text")
     */
    @Column(columnDefinition = "text")
    private String bio;

    /** length = 3000 for using bcrypt*/
    @Column(length = 3000)
    private String password;

    /** Dependency ROLES -> USER */
    @ElementCollection(targetClass = Roles.class)
    @CollectionTable(name = "user_role",
    joinColumns = @JoinColumn("user_id"))
    private Set<Roles> roles = new HashSet<>();

    /** Relationship between the user and the number of posts*/
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    /** When created user with pattern and not update time*/
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime createdDate;

    /**Spring Security Grand Authority interface return List collection
     *
     * see more: https://github.com/spring-projects/spring-security/blob/
     * master/core/src/main/java/org/springframework/security/core/
     * Authentication.java
     *
     * */
    @Transient
    private Collection<? extends GrantedAuthority> authorities;
    /**Before you start working with Persistent context*/
    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }


}
