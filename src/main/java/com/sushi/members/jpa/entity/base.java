package com.sushi.members.jpa.entity;

import jakarta.persistence.*;

public class base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
}
