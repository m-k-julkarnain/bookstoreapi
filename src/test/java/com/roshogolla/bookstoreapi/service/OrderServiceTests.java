package com.roshogolla.bookstoreapi.service;

import com.roshogolla.bookstoreapi.entity.Order;
import com.roshogolla.bookstoreapi.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class OrderServiceTests {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void testAddOrder() {
        Order order = Order.builder().id(1L).date(LocalDate.now()).totalAmount(1500F).build();

        when(orderRepository.save(order)).thenReturn(order);

        Order savedOrder = orderService.addOrder(order);

        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.getTotalAmount()).isEqualTo(1500F);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void testGetOrderById() {
        Order order = Order.builder().id(1L).date(LocalDate.now()).totalAmount(1500F).build();

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Optional<Order> savedOrder = orderService.getOrderById(1L);

        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.get().getTotalAmount()).isEqualTo(1500F);
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    public void testSearchOrderByDate() {
        Order order = Order.builder().id(1L).date(LocalDate.now()).totalAmount(1500F).build();

        when(orderRepository.findByDate(LocalDate.now())).thenReturn(List.of(order));

        List<Order> savedOrder = orderService.searchOrderByDate(LocalDate.now());

        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.get(0).getTotalAmount()).isEqualTo(1500F);
        verify(orderRepository, times(1)).findByDate(LocalDate.now());
    }

    @Test
    public void testGetAllOrder() {
        Order order = Order.builder().id(1L).date(LocalDate.now()).totalAmount(1500F).build();

        when(orderRepository.findAll()).thenReturn(List.of(order));

        List<Order> savedOrder = orderService.getAllOrders();

        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.get(0).getTotalAmount()).isEqualTo(1500F);
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateOrder() {
        Order order = Order.builder().id(1L).date(LocalDate.now()).totalAmount(1500F).build();
        Order updatedOrder = Order.builder().id(1L).date(LocalDate.now()).totalAmount(1850F).build();

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(updatedOrder);

        Order savedOrder = orderService.updateOrder(1L, updatedOrder);

        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.getTotalAmount()).isEqualTo(1850F);
        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void testDeleteOrder() {
        Order order = Order.builder().id(1L).date(LocalDate.now()).totalAmount(1500F).build();

        orderService.deleteOrder(1L);

        verify(orderRepository, times(1)).deleteById(1L);
    }

}





























