package com.macaulay.olajide.smartfridgemanager.controller;

import com.macaulay.olajide.smartfridgemanager.jpa.model.Item;
import com.macaulay.olajide.smartfridgemanager.jpa.model.Type;
import com.macaulay.olajide.smartfridgemanager.jpa.repository.ItemRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class QueryControllerTest extends AbstractControllerTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    public void testQueryControllers(){

        fridgeStockingEvents();

        // get items
        Object[] items = getItems(0.1).getBody();
        //We should only get 2 items (Soda and Dairy) that have 0 < fillFactor <= 0.1
        Assert.assertEquals(2,items.length);

        // get fill factor
        ResponseEntity getFishFillFactorResponse = getTypeFillFactor(FISH_TYPE_ID);
        Assert.assertTrue(getFishFillFactorResponse.getStatusCode().is2xxSuccessful());
        Assert.assertEquals(0.2, (Double)getFishFillFactorResponse.getBody(), 0.0);

        //forget Dairy container
        forgetType(DAIRY_TYPE_ID);
        // get items
        Object[] itemsAfterForgettingDairy = getItems(0.1).getBody();
        //We should only get 1 item (Soda) that have 0 < fillFactor <= 0.1
        Assert.assertEquals(1,itemsAfterForgettingDairy.length);
    }

    /**
     * Sends item Added event to service for
     * 1) Salmon(Fish), FillFactor 0.2
     * 2) Coke (Soda), FillFactor 0.05
     * 3) Milk (Dairy), FillFactor 0.1
     */
    private void fridgeStockingEvents(){
        UUID salmonUUID = UUID.randomUUID();
        Item salmon = Item.builder()
                .uuid(salmonUUID)
                .name("salmon")
                .fillFactor(0.2)
                .type(Type.builder()
                        .id(FISH_TYPE_ID)
                        .build())
                .build();

        UUID cokeUUID = UUID.randomUUID();
        Item coke = Item.builder()
                .uuid(cokeUUID)
                .name("coke")
                .fillFactor(0.05)
                .type(Type.builder()
                        .id(SODA_TYPE_ID)
                        .build())
                .build();

        UUID milkUUID = UUID.randomUUID();
        Item milk = Item.builder()
                .uuid(milkUUID)
                .name("milk")
                .fillFactor(0.1)
                .type(Type.builder()
                        .id(DAIRY_TYPE_ID)
                        .build())
                .build();

        addItem(salmon);
        addItem(coke);
        addItem(milk);
    }
}
