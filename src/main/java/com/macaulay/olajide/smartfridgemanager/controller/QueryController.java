package com.macaulay.olajide.smartfridgemanager.controller;

import com.macaulay.olajide.smartfridgemanager.service.SmartFridgeManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/query/v1/")
public class QueryController {


    private SmartFridgeManagerService smartFridgeManagerService;

    @Autowired
    public QueryController(SmartFridgeManagerService smartFridgeManagerService) {
        Assert.notNull(smartFridgeManagerService, "smartFridgeManagerService cannot be null");
        this.smartFridgeManagerService = smartFridgeManagerService;
    }


    @GetMapping("/items")
    public ResponseEntity<Object> getItems(@Param("fillFactor") Double fillFactor) {

        return ResponseEntity.ok(smartFridgeManagerService.getItems(fillFactor));
    }

    @GetMapping("/types/{id}/fill-factor")
    public ResponseEntity<Object> getTypeFillFactor(@PathVariable Long id) {

        Double fillFactor = smartFridgeManagerService.getFillFactor(id);

        if (Objects.isNull(fillFactor)) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(fillFactor);
    }

    @PutMapping("/types/{id}/forget")
    public ResponseEntity<Object> removeType(@PathVariable Long id) {

        smartFridgeManagerService.forgetItem(id);
        return ResponseEntity.ok().build();
    }
}

