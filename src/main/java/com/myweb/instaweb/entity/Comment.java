package com.myweb.instaweb.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@Entity
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * One comment -> One post
     */
    @ManyToOne(fetch = FetchType.EAGER)
    private Post post;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private Long userId;
    /**
     * Increase field capacity
     * ( MB we make use @Lob @Type(type = "org.hibernate.type.TextType")
     * for Postgres, because PG have LOB 2 TYPES -> BLOB, CLOB. If you use
     * Oracle it is enough to use @Lob or @Column(columnDefinition = "text")
     * <p>
     * Not nullable
     */
    @Column(columnDefinition = "text", nullable = false)
    private String message;

    @Column(updatable = false)
    private LocalDateTime createdDate;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }

}
