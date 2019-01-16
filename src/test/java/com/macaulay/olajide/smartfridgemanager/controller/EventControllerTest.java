package com.macaulay.olajide.smartfridgemanager.controller;

import com.macaulay.olajide.smartfridgemanager.jpa.model.Item;
import com.macaulay.olajide.smartfridgemanager.jpa.model.Type;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.Assert.assertTrue;

public class EventControllerTest extends AbstractControllerTest{

    @Test
    public void testEventControllers() {

        UUID uuid = UUID.randomUUID();
        // create item
        Type type = Type.builder()
                .id(1L)
                .build();

        Item item = Item.builder()
                .uuid(uuid)
                .type(type)
                .name("Carrots")
                .fillFactor(0.1)
                .build();

        // call item-added
        ResponseEntity itemAddedResponse = addItem(item);
        assertTrue(itemAddedResponse.getStatusCode().is2xxSuccessful());

        //call remove-item
        ResponseEntity itemRemovedResponse = removeItem(uuid.toString());
        assertTrue(itemRemovedResponse.getStatusCode().is2xxSuccessful());
    }
}
