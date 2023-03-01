package com.hardik.OrderService.controllers;

import com.hardik.OrderService.dto.OrderLineItemsDto;
import com.hardik.OrderService.dto.OrderRequest;
import com.hardik.OrderService.exceptions.OrderException;
import com.hardik.OrderService.models.Order;
import com.hardik.OrderService.models.OrderLineItems;
import com.hardik.OrderService.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    Logger logger = Logger.getLogger("OrderController.class");
    @Autowired
    OrderService orderService;

    @PostMapping
    public ResponseEntity<Object> placeOrder(@RequestBody OrderRequest orderRequest) {
        try {
            orderService.placeOrder(orderRequest);
            return new ResponseEntity<>("order placed successfully", HttpStatus.OK);
        }catch (OrderException orderException){
            logger.info(orderException.getMessage());
            return new ResponseEntity<>(orderException.getMessage(),HttpStatus.CONFLICT);
        }
    }
}
