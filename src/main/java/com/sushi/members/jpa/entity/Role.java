package com.sushi.members.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name="ROLES")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long roleId;
    @Column(name = "role_nm")
    private String roleName;

    public Role(String roleName) {
        this.roleName = roleName;
    }
}
