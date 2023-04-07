package com.sushi.members.jpa.entity;


import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

//INSERT INTO users (user_id, password, roles, scopes)
//        VALUES ('appuser', 'appusersecret', 'USER', 'resource.read resource.write');

@Table(name="Users")
@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name = "username")
    private String username;
    private String displayName;
    private boolean active;
    @Column(name = "effectiveData")
    private LocalDate effectiveData;

    @Column(name = "password")
    private String password;
    @Column(name = "roles")
    private String roles;
    @Column(name = "scopes")
    private String scopes;

}
