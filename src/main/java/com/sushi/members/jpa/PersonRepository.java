package com.sushi.members.jpa;

import com.sushi.members.jpa.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person,Long> {
    Person findByUsername(String username);
    @Override
    boolean existsById(Long id);
}
