package pl.jagiela.microservices_ecommerce.order_service.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jagiela.microservices_ecommerce.order_service.dto.request.OrderItemDto;
import pl.jagiela.microservices_ecommerce.order_service.dto.request.OrderRequestDto;
import pl.jagiela.microservices_ecommerce.order_service.exception.OrderNotFoundException;
import pl.jagiela.microservices_ecommerce.order_service.exception.OrderProcessingException;
import pl.jagiela.microservices_ecommerce.order_service.model.Order;
import pl.jagiela.microservices_ecommerce.order_service.model.OrderItem;
import pl.jagiela.microservices_ecommerce.order_service.model.OrderStatus;
import pl.jagiela.microservices_ecommerce.order_service.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Order createOrder(OrderRequestDto orderRequest) {

        Order order = new Order();
        order.setUserId(orderRequest.getUserId());
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());

        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0.0;

        for (OrderItemDto itemDto : orderRequest.getItems()) {
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProductId(itemDto.getProductId());
            item.setQuantity(itemDto.getQuantity());

//                TODO: po utworzeniu gateway i Eureka
//                // Pobieramy cenę produktu z product-service
//                item.setPrice(getProductPrice(itemDto.getProductId()));
            double productPrice = 50.0; // Tymczasowo hardcoded dla testów

            item.setPrice(productPrice);
            totalAmount += productPrice * itemDto.getQuantity();

            orderItems.add(item);
        }

        order.setTotalAmount(totalAmount);
        order.setItems(orderItems);

        return orderRepository.save(order);
    }

    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order with ID " + id + " not found"));
    }

    @Transactional
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException("Order with ID " + id + " not found");
        }
        orderRepository.deleteById(id);
    }

    @Transactional
    public Order cancelOrder(Long id) {
        return updateOrderStatus(id, OrderStatus.CANCELED);
    }

    @Transactional
    public Order updateOrderStatus(Long id, OrderStatus newStatus) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order with ID " + id + " not found"));

        // Jeśli zamówienie jest już anulowane lub zakończone, nie pozwalamy na zmianę statusu
        if (order.getStatus() == OrderStatus.CANCELED) {
            throw new OrderProcessingException("Cannot update status of a canceled order.");
        }

        order.setStatus(newStatus);
        return orderRepository.save(order);
    }
}