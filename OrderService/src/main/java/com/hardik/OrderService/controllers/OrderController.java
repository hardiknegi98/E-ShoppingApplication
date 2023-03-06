package com.hardik.OrderService.controllers;

import com.hardik.OrderService.dto.OrderRequest;
import com.hardik.OrderService.exceptions.OrderException;
import com.hardik.OrderService.services.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
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
    @CircuitBreaker(name="inventory",fallbackMethod="fallBackMethod")
    public ResponseEntity<Object> placeOrder(@RequestBody OrderRequest orderRequest) {
        try {
            orderService.placeOrder(orderRequest);
            return new ResponseEntity<>("order placed successfully", HttpStatus.CREATED);
        }catch (OrderException orderException){
            logger.info(orderException.getMessage());
            return new ResponseEntity<>(orderException.getMessage(),HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<Object> fallBackMethod(OrderRequest orderRequest, RuntimeException runtimeException){
        return new ResponseEntity<>("Order not placed! please try after some time. Error in communicating with inventory service"
                ,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
