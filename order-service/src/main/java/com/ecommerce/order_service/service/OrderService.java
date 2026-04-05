package com.ecommerce.order_service.service;

import com.ecommerce.order_service.events.OrderPlacedEvent;
import com.ecommerce.order_service.model.Order;
import com.ecommerce.order_service.model.dtos.CartDTO;
import com.ecommerce.order_service.model.dtos.PaymentRequestDTO;
import com.ecommerce.order_service.model.dtos.PaymentResponseDTO;
import com.ecommerce.order_service.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    private static final Logger LOG = LoggerFactory.getLogger(OrderService.class);

    private final WebClient webClient1;
    private final WebClient webClient2;

    public OrderService(WebClient.Builder builder) {
        this.webClient1 = builder.baseUrl("http://CART-SERVICE").build();
        this.webClient2 = builder.baseUrl("http://PAYMENT-SERVICE").build();
    }

    @Autowired
    private KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    @Autowired
    private OrderRepository orderRepository;


    public Order checkout(String userId) {

        LOG.debug("GET CART FROM CART SERVICE");
        CartDTO cart = webClient1.get()
                .uri("/cart/" + userId)
                .retrieve()
                .bodyToMono(CartDTO.class)
                .block();

        if (cart == null || cart.getItems().isEmpty()) {
            LOG.debug("Empty Cart!");
            throw new RuntimeException("Cart is empty");
        }

        LOG.debug("CALCULATE TOTAL");
        double total = cart.getItems().stream()
                .mapToDouble(item -> item.getQuantity() * 1000.0)
                .sum();

        LOG.debug("CALL PAYMENT SERVICE");
        PaymentResponseDTO payment = webClient2.post()
                .uri("/payment")
                .bodyValue(new PaymentRequestDTO(userId, total))
                .retrieve()
                .bodyToMono(PaymentResponseDTO.class)
                .block();

        if (payment == null || !"SUCCESS".equals(payment.getStatus())) {
            throw new RuntimeException("Payment Failed");
        }

        LOG.debug("CREATE ORDER");
        Order order = new Order(
                UUID.randomUUID().toString(),
                userId,
                cart.getItems(),
                total,
                "PLACED"
        );

        orderRepository.save(order);

        LOG.debug("CLEAR CART");
        webClient1.delete()
                .uri("/cart/" + userId)
                .retrieve()
                .bodyToMono(Void.class)
                .block();

        LOG.debug("PUBLISH EVENT");
        kafkaTemplate.send("order-topic",
                new OrderPlacedEvent(
                        order.getId(),
                        userId,
                        total,
                        "Order placed successfully"
                ));

        return order;
    }

    public List<Order> getOrders(String userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order getOrderById(String orderId) {
        return orderRepository.findById(orderId).orElseThrow();
    }

    public Order updateStatus(String orderId, String status) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus(status);
        return orderRepository.save(order);
    }

    public void cancelOrder(String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus("CANCELLED");
        orderRepository.save(order);
    }
}
