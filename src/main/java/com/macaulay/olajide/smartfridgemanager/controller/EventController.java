package com.macaulay.olajide.smartfridgemanager.controller;

import com.macaulay.olajide.smartfridgemanager.jpa.model.Item;
import com.macaulay.olajide.smartfridgemanager.service.SmartFridgeManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/event/v1/")
public class EventController {


    private SmartFridgeManagerService smartFridgeManagerService;

    @Autowired
    public EventController(SmartFridgeManagerService smartFridgeManagerService) {
        Assert.notNull(smartFridgeManagerService, "smartFridgeManagerService cannot be null");
        this.smartFridgeManagerService = smartFridgeManagerService;
    }


    @PostMapping("/item-added")
    public ResponseEntity<Object> itemAddedEvent(@RequestBody Item item) {
        smartFridgeManagerService.handleItemAdded(item.getType().getId(),
                item.getUuid().toString(), item.getName(), item.getFillFactor());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/item-removed/{id}")
    public ResponseEntity<Object> itemRemovedEvent(@PathVariable String id) {
        smartFridgeManagerService.handleItemRemoved(id);

        return ResponseEntity.noContent().build();
    }
}

