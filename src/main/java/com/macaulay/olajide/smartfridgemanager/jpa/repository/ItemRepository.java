package com.macaulay.olajide.smartfridgemanager.jpa.repository;

import com.macaulay.olajide.smartfridgemanager.jpa.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface ItemRepository extends JpaRepository<Item, UUID>, ItemRepositoryCustom {


}
