package com.sushi.members.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;


@Entity
@Data
@Table(name = "Persons")

public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")

    private long id;
    @NotEmpty

    private String username;
    @Size(min = 8, max = 15)

    private String phoneNum;
    @Column(name = "wechat")

    private String wechat;
    @Email

    private String email;

    private Long Point;

    private String Status;


    private LocalDate registerDate;
}
