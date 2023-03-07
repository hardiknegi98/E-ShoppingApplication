package com.hardik.OrderService.services;

import com.hardik.OrderService.dto.InventoryResponse;
import com.hardik.OrderService.dto.OrderLineItemsDto;
import com.hardik.OrderService.dto.OrderRequest;
import com.hardik.OrderService.event.OrderPlacedEvent;
import com.hardik.OrderService.exceptions.OrderException;
import com.hardik.OrderService.models.Order;
import com.hardik.OrderService.models.OrderLineItems;
import com.hardik.OrderService.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class OrderService {
    Logger logger = Logger.getLogger("OrderService.class");
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    WebClient.Builder webClientBuilder;
    @Autowired
    KafkaTemplate<String,OrderPlacedEvent> kafkaTemplate;


    public void placeOrder(OrderRequest orderRequest) throws OrderException {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderLineItems(mapOrderLineItemsDto(orderRequest));

        List<String> skuCodeList = order.getOrderLineItems().stream()
                .map(OrderLineItems::getSkuCode)
                .collect(Collectors.toList());
        //order service communicates with inventory service to check if products in order are available in stock
        InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodeList).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        for (InventoryResponse inventoryResponse : inventoryResponseArray) {
            if (!inventoryResponse.isInStock())
                throw new OrderException("Order NOT placed sucessfully! "+inventoryResponse.getSkuCode()+" is not in stock");
        }
        logger.info("Order placed successfully");
        orderRepository.save(order);
        kafkaTemplate.send("notificationTopic",new OrderPlacedEvent(order.getOrderNumber())); //send order number as event to notification topic, event will be consumed by notification service
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
