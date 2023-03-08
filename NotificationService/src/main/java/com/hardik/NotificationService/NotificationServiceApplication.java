package com.hardik.NotificationService;

import com.hardik.NotificationService.event.OrderPlacedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@EnableEurekaClient
@Slf4j
public class NotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    // order number will be consumed by sendNotification listener from notification topic
    // which was produced by placeOrder producer in order service
    @KafkaListener(topics = "notificationTopic")
    public void sendNotification(OrderPlacedEvent orderPlaceEvent) {
        log.info("sent notification for order : " + orderPlaceEvent);
    }
}
