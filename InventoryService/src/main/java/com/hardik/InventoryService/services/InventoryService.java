package com.hardik.InventoryService.services;

import com.hardik.InventoryService.Repository.InventoryRepository;
import com.hardik.InventoryService.dto.InventoryResponse;
import com.hardik.InventoryService.models.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {
    @Autowired
    InventoryRepository inventoryRepository;

    public List<InventoryResponse> checkAvailability(List<String> skuCode) {
        List<Inventory> inventoryList = inventoryRepository.findBySkuCodeIn(skuCode);
        return inventoryList.stream().
                map(inventory ->
                        InventoryResponse.builder().
                                skuCode(inventory.getSkuCode()).
                                isInStock(inventory.getQuantity() > 0).
                                build()
                ).collect(Collectors.toList());
    }
}
