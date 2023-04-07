package com.sushi.members.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Optional;

@Entity
@Data
@Table(name = "stores")
public class LocalShop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    private String storename;
    private String storeAddress;
    private String storeContactNumber;
    private String storeAdmin;
    private  int storeLevel;

    private Long parentStoreId;


}
