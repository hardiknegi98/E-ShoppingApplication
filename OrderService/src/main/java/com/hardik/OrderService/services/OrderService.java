package com.hardik.OrderService.services;

import com.hardik.OrderService.dto.OrderLineItemsDto;
import com.hardik.OrderService.dto.OrderRequest;
import com.hardik.OrderService.models.Order;
import com.hardik.OrderService.models.OrderLineItems;
import com.hardik.OrderService.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderLineItems(mapOrderLineItemsDto(orderRequest));
        orderRepository.save(order);
    }

    private List<OrderLineItems> mapOrderLineItemsDto(OrderRequest orderRequest) {
        List<OrderLineItemsDto> orderLineItemsDtoList = orderRequest.getOrderLineItemsDtoList();
        return orderLineItemsDtoList.stream().
                map(orderLineItemsDto -> {
                    OrderLineItems orderLineItems = new OrderLineItems();
                    orderLineItems.setPrice(orderLineItemsDto.getPrice());
                    orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
                    orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
                    return orderLineItems;
                }).
                collect(Collectors.toList());
    }
}