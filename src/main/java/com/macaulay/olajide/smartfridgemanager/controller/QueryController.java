package com.macaulay.olajide.smartfridgemanager.controller;

import com.macaulay.olajide.smartfridgemanager.service.SmartFridgeManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/query/v1/")
public class QueryController {


    private SmartFridgeManagerService smartFridgeManagerService;

    @Autowired
    public QueryController(SmartFridgeManagerService smartFridgeManagerService) {
        Assert.notNull(smartFridgeManagerService, "smartFridgeManagerService cannot be null");
        this.smartFridgeManagerService = smartFridgeManagerService;
    }


    /**
     * Called when user wants to know which Items are running out.
     * @param fillFactor
     * @return
     */
    @GetMapping("/items")
    public ResponseEntity<Object> getItems(@Param("fillFactor") Double fillFactor) {

        return ResponseEntity.ok(smartFridgeManagerService.getItems(fillFactor));
    }

    /**
     * Called when user wants to know quantity left for a specific type
     * @param id
     * @return
     */
    @GetMapping("/types/{id}/fill-factor")
    public ResponseEntity<Object> getTypeFillFactor(@PathVariable Long id) {

        Double fillFactor = smartFridgeManagerService.getFillFactor(id);

        return ResponseEntity.ok(fillFactor);
    }

    /**
     * Called when user wants to stop stocking a specific type
     * @param id
     * @return
     */
    @PutMapping("/types/{id}/forget")
    public ResponseEntity<Object> removeType(@PathVariable Long id) {

        smartFridgeManagerService.forgetType(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Called when user wants to start stocking a specific type
     */
    //TODO API to remember type

    /**
     * Called when user wants to add a new type to inventory
     */
    //TODO API to add a new type

}

