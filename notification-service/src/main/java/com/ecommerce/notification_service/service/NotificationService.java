package com.ecommerce.notification_service.service;

import com.ecommerce.notification_service.model.OrderPlacedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @KafkaListener(topics = "order-topic", groupId = "notification-group")
    public void consume(OrderPlacedEvent event) {
        System.out.println("📩 Notification:");
        System.out.println("User: " + event.getUserId());
        System.out.println("OrderId: " + event.getOrderId());
        System.out.println("Amount: " + event.getAmount());
        System.out.println("Message: " + event.getMessage());
    }
}
