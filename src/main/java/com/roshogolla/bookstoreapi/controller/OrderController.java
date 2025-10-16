package com.roshogolla.bookstoreapi.controller;

import com.roshogolla.bookstoreapi.dto.category.CategoryResponseDTO;
import com.roshogolla.bookstoreapi.dto.order.OrderCreateDTO;
import com.roshogolla.bookstoreapi.dto.order.OrderResponseDTO;
import com.roshogolla.bookstoreapi.dto.order.OrderUpdateDTO;
import com.roshogolla.bookstoreapi.entity.Order;
import com.roshogolla.bookstoreapi.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    //Create order
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderCreateDTO orderCreateDTO) {
        Order order = new Order();
        order.setDate(orderCreateDTO.getDate());
        order.setTotalAmount(orderCreateDTO.getTotalAmount());

        Order createdOrder = orderService.addOrder(order);

        OrderResponseDTO orderResponseDTO = new OrderResponseDTO(
                createdOrder.getId(),
                createdOrder.getDate(),
                createdOrder.getTotalAmount()
        );

        return ResponseEntity.ok(orderResponseDTO);
    }

    //Get order by id
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + id));

        OrderResponseDTO orderResponseDTO = new OrderResponseDTO(
                order.getId(),
                order.getDate(),
                order.getTotalAmount()
        );
        return ResponseEntity.ok(orderResponseDTO);
    }

    //Search order by date
    @GetMapping("/search/by-date")
    public ResponseEntity<List<OrderResponseDTO>> getOrderByDate(@RequestParam LocalDate date) {
        List<OrderResponseDTO> orders = orderService.searchOrderByDate(date)
                .stream()
                .map(order -> new OrderResponseDTO(
                        order.getId(),
                        order.getDate(),
                        order.getTotalAmount()
                )).collect(Collectors.toList());
        return ResponseEntity.ok(orders);
    }

    //Get all order
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrder() {
        List<OrderResponseDTO> orders = orderService.getAllOrders()
                .stream()
                .map(order -> new OrderResponseDTO(
                        order.getId(),
                        order.getDate(),
                        order.getTotalAmount()
                )).collect(Collectors.toList());
        return ResponseEntity.ok(orders);
    }

    //Update Order
    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> updateOrder(@PathVariable Long id, @Valid @RequestBody OrderUpdateDTO orderUpdateDTO) {
        Order order = new Order();
        order.setId(id);
        order.setDate(orderUpdateDTO.getDate());
        order.setTotalAmount(orderUpdateDTO.getTotalAmount());

        Order updatedOrder = orderService.updateOrder(id, order);

        OrderResponseDTO orderResponseDTO = new OrderResponseDTO(
                updatedOrder.getId(),
                updatedOrder.getDate(),
                updatedOrder.getTotalAmount()
        );
        return ResponseEntity.ok(orderResponseDTO);
    }

    //Delete Order
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Order deleted successfully");
    }
}














