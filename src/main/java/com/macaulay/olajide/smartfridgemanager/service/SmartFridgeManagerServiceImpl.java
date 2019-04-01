package com.macaulay.olajide.smartfridgemanager.service;

import com.macaulay.olajide.smartfridgemanager.jpa.model.Item;
import com.macaulay.olajide.smartfridgemanager.jpa.model.Type;
import com.macaulay.olajide.smartfridgemanager.jpa.repository.ItemRepository;
import com.macaulay.olajide.smartfridgemanager.jpa.repository.TypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class SmartFridgeManagerServiceImpl implements SmartFridgeManagerService {

    private ItemRepository itemRepository;
    private TypeRepository typeRepository;

    @Autowired
    public SmartFridgeManagerServiceImpl(ItemRepository itemRepository, TypeRepository typeRepository){
        Assert.notNull(itemRepository, "itemRepository cannot be null");
        Assert.notNull(typeRepository, "typeRepository cannot be null");
        this.itemRepository = itemRepository;
        this.typeRepository = typeRepository;
    }


    @Override
    public void handleItemRemoved(String itemUUID) {

        Item itemToDelete = itemRepository.getOne(UUID.fromString(itemUUID));

        // subtract from totalFillFactor
        Type type = itemToDelete.getType();
        type.setTotalFillFactor(type.getTotalFillFactor() - itemToDelete.getFillFactor());

        log.info("Removing {}", itemToDelete.getName());
        itemRepository.deleteById(UUID.fromString(itemUUID));

        typeRepository.save(type);
    }

    @Override
    public void handleItemAdded(long itemType, String itemUUID, String name, Double fillFactor) {


        //update totalFillFactor
        Type addedType = typeRepository.getOne(itemType);
        Double currentTotalFillFactor = addedType.getTotalFillFactor();
        addedType.setTotalFillFactor(currentTotalFillFactor + fillFactor);

        //add item
        Item item = Item.builder()
                .uuid(UUID.fromString(itemUUID))
                .name(name)
                .type(addedType)
                .fillFactor(fillFactor)
                .build();

        log.info("Adding Item: {} - {}", name, addedType.getName());
        itemRepository.save(item);

    }

    @Override
    public Object[] getItems(Double fillFactor) {

        fillFactor = Objects.isNull(fillFactor) ? 1.0 : fillFactor;
        log.info("Fetching items below {} FillFactor", fillFactor);
        List<Item> items = itemRepository.findNotForgottenItemsLessOrEqualToFillFactor(fillFactor);
        return items.toArray();
    }

    @Override
    public Double getFillFactor(long itemType) {

        log.info("Getting Fill Factor for item {}", itemType);
        return typeRepository.getOne(itemType).getTotalFillFactor();
    }

    @Override
    public void forgetType(long itemType) {

        Type type = typeRepository.getOne(itemType);
        type.setIsForgotten(true);

        log.info("Forgetting item type {}", type.getName());
        typeRepository.save(type);
    }
}
