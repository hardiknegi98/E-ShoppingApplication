package com.hardik.InventoryService;

import com.hardik.InventoryService.Repository.InventoryRepository;
import com.hardik.InventoryService.models.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InventoryServiceApplication implements CommandLineRunner {
	@Autowired
	InventoryRepository inventoryRepository;

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*Inventory inventory = new Inventory();
		inventory.setQuantity(100);
		inventory.setSkuCode("iphone 14 pro max");
		inventoryRepository.save(inventory);
		*/
	}
}
