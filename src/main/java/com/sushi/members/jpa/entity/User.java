package com.sushi.members.jpa.entity;


import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

//INSERT INTO users (user_id, password, roles, scopes)
//        VALUES ('appuser', 'appusersecret', 'USER', 'resource.read resource.write');

@Table(name="Users")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @NotEmpty(message = "用户账号不能为空！")
    @Column(name = "username")
    private String username;
    @NotEmpty(message = "用户姓名不能为空！")
    private String displayName;
    private boolean active;
    @Column(name = "effectiveData")
    private LocalDate effectiveData;
    private LocalDate expiryDate;
    private boolean isAuthorized;
    @NotEmpty
    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="users_roles",
    joinColumns = @JoinColumn(name="user_id",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name="role_id",referencedColumnName = "id")
    )
    private Collection<Role> roles = new ArrayList<>();
    @Column(name = "scopes")
    private String scopes;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    @Transactional
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

            try {
                List<Role> roleList = roles.stream().collect(Collectors.toList());
                authorities = roles.stream()
                        .map(this::mapRoleToAuthority)
                        .collect(Collectors.toList());

            } catch (Exception e) {
                throw e;
            }




        return authorities;
    }

    private GrantedAuthority mapRoleToAuthority(Role role) {

        return new SimpleGrantedAuthority(role.getRoleName());
    }

}
