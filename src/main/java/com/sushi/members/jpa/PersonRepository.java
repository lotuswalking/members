package com.sushi.members.jpa;

import com.sushi.members.jpa.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person,Long> {
    Person findByUsername(String username);

    Person getPersonById(Long id);
    @Override
    boolean existsById(Long id);
    Boolean existsByUsername(String username);

}
