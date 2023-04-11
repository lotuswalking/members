package com.sushi.members.jpa;

import com.sushi.members.jpa.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByRoleName(String rolename);
}
