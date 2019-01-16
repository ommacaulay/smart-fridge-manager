package com.macaulay.olajide.smartfridgemanager.jpa.repository;

import com.macaulay.olajide.smartfridgemanager.jpa.model.Item;

import java.util.List;

public interface ItemRepositoryCustom {

    List<Item> findNotForgottenItemsLessOrEqualToFillFactor(Double fillFactor);

}
