package com.hardik.InventoryService.controllers;

import com.hardik.InventoryService.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    @Autowired
    InventoryService inventoryService;

    @GetMapping("/{skuCode}")
    public ResponseEntity<Object> checkAvailability(@PathVariable("skuCode") String skuCode){
        return new ResponseEntity<>(inventoryService.checkAvailability(skuCode), HttpStatus.OK);
    }
}
