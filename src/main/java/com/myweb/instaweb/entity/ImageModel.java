package com.myweb.instaweb.entity;




/*
 * @author
 * @version
 * @return
 */

import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class ImageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] imageByte;
    @JsonIgnore
    private Long userId;
    @JsonIgnore
    private Long postId;


}
