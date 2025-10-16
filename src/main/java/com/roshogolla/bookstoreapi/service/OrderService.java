package com.roshogolla.bookstoreapi.service;

import com.roshogolla.bookstoreapi.entity.Order;
import com.roshogolla.bookstoreapi.repository.OrderRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    //Create
    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }

    //Read order by id
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    //Search order by date
    public List<Order> searchOrderByDate(LocalDate date) {
        return orderRepository.findByDate(date);
    }

    //Read all order
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    //Update
    public Order updateOrder(Long id, Order updatedOrder) {
        return orderRepository.findById(id)
                .map(order -> {
                    order.setDate(updatedOrder.getDate());
                    order.setBooks(updatedOrder.getBooks());
                    order.setTotalAmount(updatedOrder.getTotalAmount());
                    return orderRepository.save(order);
                })
                .orElseThrow(() -> new RuntimeException("Order not found with id " + id));
    }

    //Delete
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
