package com.sushi.members.jpa;

import com.sushi.members.jpa.entity.LocalShop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalStoreRepository extends JpaRepository<LocalShop, Long> {
    LocalShop findByStorename(String storeName);
    LocalShop getLocalShopById(Long id);
    Boolean existsByStorename(String storeName);
}
