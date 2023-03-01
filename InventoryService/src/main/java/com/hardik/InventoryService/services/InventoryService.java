package com.hardik.InventoryService.services;

import com.hardik.InventoryService.Repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    @Autowired
    InventoryRepository inventoryRepository;

    public boolean checkAvailability(String skuCode) {
    return inventoryRepository.findBySkuCode(skuCode).isPresent();
    }
}
