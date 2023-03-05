package com.hardik.InventoryService.controllers;

import com.hardik.InventoryService.dto.InventoryResponse;
import com.hardik.InventoryService.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    @Autowired
    InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> checkAvailability(@RequestParam List<String> skuCode){
        return inventoryService.checkAvailability(skuCode);
    }

   /* @GetMapping //Check Full Inventory
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> checkAvailabilityOfAllProducts(@RequestParam String cfi){
        return inventoryService.checkAvailabilityOfAllProducts();
    }*/
}
